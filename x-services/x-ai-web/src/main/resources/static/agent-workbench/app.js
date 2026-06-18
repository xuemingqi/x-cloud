const DEFAULT_API_BASE = 'http://127.0.0.1:19084/ai/agent';
const STORAGE_KEY = 'x-ai-agent-workbench';
const AGENT_API_PREFIX = '/ai/agent';
const AGENT_ENDPOINT_SUFFIXES = [
  '/capabilities',
  '/run',
  '/stream/run',
  '/plan/enter',
  '/plan/exit',
  '/permission',
];

const state = {
  apiBase: DEFAULT_API_BASE,
  events: [],
  eventKeys: new Set(),
  streamText: '',
  streamPendingText: '',
  streamRenderTimer: null,
  busy: false,
};

const els = {
  apiBaseInput: document.querySelector('#apiBaseInput'),
  saveEndpointBtn: document.querySelector('#saveEndpointBtn'),
  connectionBadge: document.querySelector('#connectionBadge'),
  refreshCapabilitiesBtn: document.querySelector('#refreshCapabilitiesBtn'),
  capabilityList: document.querySelector('#capabilityList'),
  capabilitySelect: document.querySelector('#capabilitySelect'),
  userIdInput: document.querySelector('#userIdInput'),
  sessionIdInput: document.querySelector('#sessionIdInput'),
  promptInput: document.querySelector('#promptInput'),
  contextInput: document.querySelector('#contextInput'),
  resultOutput: document.querySelector('#resultOutput'),
  lastTaskMeta: document.querySelector('#lastTaskMeta'),
  eventTimeline: document.querySelector('#eventTimeline'),
  eventTemplate: document.querySelector('#eventTemplate'),
  runBtn: document.querySelector('#runBtn'),
  streamBtn: document.querySelector('#streamBtn'),
  clearEventsBtn: document.querySelector('#clearEventsBtn'),
  clearResultBtn: document.querySelector('#clearResultBtn'),
  enterPlanBtn: document.querySelector('#enterPlanBtn'),
  exitPlanBtn: document.querySelector('#exitPlanBtn'),
  planPromptInput: document.querySelector('#planPromptInput'),
  permissionModeSelect: document.querySelector('#permissionModeSelect'),
  applyPermissionBtn: document.querySelector('#applyPermissionBtn'),
};

function init() {
  restoreSettings();
  bindEvents();
  loadCapabilities();
}

function restoreSettings() {
  const saved = readStorage();
  state.apiBase = normalizeBase(saved.apiBase || DEFAULT_API_BASE);
  els.apiBaseInput.value = state.apiBase;
  els.userIdInput.value = saved.userId || els.userIdInput.value;
  els.sessionIdInput.value = saved.sessionId || els.sessionIdInput.value;
}

function bindEvents() {
  document.querySelectorAll('.nav-item').forEach((item) => {
    item.addEventListener('click', () => activatePanel(item));
  });

  document.querySelectorAll('.quick-action').forEach((item) => {
    item.addEventListener('click', () => {
      els.capabilitySelect.value = item.dataset.capability;
      els.promptInput.value = item.dataset.prompt;
      activatePanel(document.querySelector('[data-panel="runPanel"]'));
    });
  });

  els.saveEndpointBtn.addEventListener('click', saveEndpoint);
  els.refreshCapabilitiesBtn.addEventListener('click', loadCapabilities);
  els.runBtn.addEventListener('click', () => runAgent(false));
  els.streamBtn.addEventListener('click', () => runAgent(true));
  els.clearEventsBtn.addEventListener('click', clearEvents);
  els.clearResultBtn.addEventListener('click', () => setResult('等待任务...'));
  els.enterPlanBtn.addEventListener('click', () => callPlan('/plan/enter'));
  els.exitPlanBtn.addEventListener('click', () => callPlan('/plan/exit'));
  els.applyPermissionBtn.addEventListener('click', applyPermission);
}

function activatePanel(item) {
  document.querySelectorAll('.nav-item').forEach((nav) => nav.classList.remove('active'));
  document.querySelectorAll('.panel').forEach((panel) => panel.classList.remove('active'));
  item.classList.add('active');
  document.querySelector(`#${item.dataset.panel}`).classList.add('active');
}

function saveEndpoint() {
  state.apiBase = normalizeBase(els.apiBaseInput.value);
  els.apiBaseInput.value = state.apiBase;
  writeStorage();
  setBadge('已保存', 'ok');
}

async function loadCapabilities() {
  setBadge('连接中', 'muted');
  try {
    const payload = await request('/capabilities', { method: 'GET' });
    const capabilities = unwrap(payload) || [];
    renderCapabilities(Array.isArray(capabilities) ? capabilities : []);
    setBadge('已连接', 'ok');
  } catch (error) {
    renderCapabilities([]);
    setBadge('连接失败', 'error');
    appendEvent('error', 'capabilities', error.message);
  }
}

function renderCapabilities(capabilities) {
  els.capabilityList.innerHTML = '';
  if (capabilities.length === 0) {
    els.capabilityList.innerHTML = '<div class="empty">暂无能力</div>';
    return;
  }

  const known = new Set(Array.from(els.capabilitySelect.options).map((item) => item.value));
  capabilities.forEach((item) => {
    const code = item.capability || item.name || String(item);
    if (code && !known.has(code)) {
      els.capabilitySelect.add(new Option(code, code));
      known.add(code);
    }

    const card = document.createElement('div');
    card.className = 'capability';
    card.innerHTML = `
      <strong>${escapeHtml(code)}</strong>
      <span>${escapeHtml(item.desc || '')}</span>
    `;
    card.addEventListener('click', () => {
      if (code) {
        els.capabilitySelect.value = code;
      }
    });
    els.capabilityList.appendChild(card);
  });
}

async function runAgent(stream) {
  if (state.busy) {
    return;
  }
  setBusy(true);
  resetStreamState();
  setResult(stream ? '开始接收事件流...' : '运行中...');
  appendEvent('request', stream ? 'stream' : 'run', els.promptInput.value);

  try {
    if (stream) {
      await streamRequest('/stream/run', buildAgentRequest(), handleStreamChunk);
    } else {
      const payload = await request('/run', {
        method: 'POST',
        body: JSON.stringify(buildAgentRequest()),
      });
      handleAgentResponse(unwrap(payload));
    }
    setBadge('运行完成', 'ok');
  } catch (error) {
    setBadge('运行失败', 'error');
    setResult(`请求失败：${error.message}`);
    appendEvent('error', 'agent', error.message);
  } finally {
    setBusy(false);
    writeStorage();
  }
}

async function callPlan(path) {
  setBusy(true);
  try {
    const payload = await request(path, {
      method: 'POST',
      body: JSON.stringify({
        ...buildAgentRequest(),
        request: els.planPromptInput.value.trim(),
        capability: 'PLANNING',
      }),
    });
    handleAgentResponse(unwrap(payload));
    setBadge(path.includes('enter') ? '计划中' : '已退出计划', 'ok');
  } catch (error) {
    setBadge('计划失败', 'error');
    appendEvent('error', 'plan', error.message);
  } finally {
    setBusy(false);
  }
}

async function applyPermission() {
  setBusy(true);
  try {
    const payload = await request('/permission', {
      method: 'POST',
      body: JSON.stringify({
        userId: els.userIdInput.value.trim(),
        sessionId: els.sessionIdInput.value.trim(),
        mode: els.permissionModeSelect.value,
      }),
    });
    handleAgentResponse(unwrap(payload));
    setBadge(`权限 ${els.permissionModeSelect.value}`, 'ok');
  } catch (error) {
    setBadge('权限失败', 'error');
    appendEvent('error', 'permission', error.message);
  } finally {
    setBusy(false);
  }
}

function buildAgentRequest() {
  return {
    request: els.promptInput.value.trim(),
    userId: els.userIdInput.value.trim(),
    sessionId: els.sessionIdInput.value.trim(),
    capability: els.capabilitySelect.value,
    context: readContext(),
  };
}

function readContext() {
  const raw = els.contextInput.value.trim();
  if (!raw) {
    return {};
  }
  try {
    return JSON.parse(raw);
  } catch (error) {
    throw new Error(`上下文 JSON 格式错误：${error.message}`);
  }
}

async function request(path, options = {}) {
  const response = await fetch(`${state.apiBase}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
    ...options,
  });
  if (!response.ok) {
    throw new Error(`${response.status} ${response.statusText}`);
  }
  return response.json();
}

async function streamRequest(path, body, onChunk) {
  const response = await fetch(`${state.apiBase}${path}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json, text/event-stream, application/x-ndjson',
    },
    body: JSON.stringify(body),
  });
  if (!response.ok || !response.body) {
    throw new Error(`${response.status} ${response.statusText}`);
  }

  const reader = response.body.getReader();
  const decoder = new TextDecoder();
  let buffer = '';
  while (true) {
    const { value, done } = await reader.read();
    if (done) {
      break;
    }
    buffer += decoder.decode(value, { stream: true });
    buffer = consumeJsonBuffer(buffer, onChunk);
  }
  buffer += decoder.decode();
  consumeJsonBuffer(buffer, onChunk, true);
}

function consumeJsonBuffer(buffer, onChunk, flush = false) {
  let cursor = 0;
  let depth = 0;
  let start = -1;
  let inString = false;
  let escaped = false;

  for (let i = 0; i < buffer.length; i += 1) {
    const char = buffer[i];
    if (inString) {
      if (escaped) {
        escaped = false;
      } else if (char === '\\') {
        escaped = true;
      } else if (char === '"') {
        inString = false;
      }
      continue;
    }
    if (char === '"') {
      inString = true;
      continue;
    }
    if (char === '{') {
      if (depth === 0) {
        start = i;
      }
      depth += 1;
    }
    if (char === '}') {
      depth -= 1;
      if (depth === 0 && start >= 0) {
        const json = buffer.slice(start, i + 1);
        cursor = i + 1;
        try {
          onChunk(JSON.parse(json));
        } catch (error) {
          appendEvent('warn', 'stream', json);
        }
        start = -1;
      }
    }
  }

  const rest = buffer.slice(cursor);
  if (flush && rest.trim()) {
    appendEvent('chunk', 'stream', rest.trim());
    return '';
  }
  return rest;
}

function handleStreamChunk(payload) {
  const data = unwrap(payload);
  if (!data) {
    appendEvent('chunk', 'stream', toPreviewText(payload));
    return;
  }
  handleAgentResponse(data, true);
}

function handleAgentResponse(data, append = false) {
  if (!data) {
    return;
  }
  const text = typeof data.text === 'string' ? data.text : '';
  if (append) {
    if (text) {
      queueStreamText(text);
    }
  } else {
    setResult(text || toPreviewText(data));
  }
  const meta = [data.taskId, data.userId, data.sessionId, data.capability].filter(Boolean).join(' · ');
  els.lastTaskMeta.textContent = meta || new Date().toLocaleString();

  if (Array.isArray(data.events)) {
    data.events.forEach((event) => {
      appendAgentEvent(event);
    });
  } else if (!append && text) {
    appendEvent('response', data.capability || 'agent', text);
  }
}

function unwrap(payload) {
  if (!payload || typeof payload !== 'object') {
    return payload;
  }
  if (Object.prototype.hasOwnProperty.call(payload, 'data')) {
    return payload.data;
  }
  if (Object.prototype.hasOwnProperty.call(payload, 'result')) {
    return payload.result;
  }
  return payload;
}

function appendEvent(type, source, content) {
  const normalizedContent = content == null ? '' : String(content);
  state.events.unshift({
    type,
    source,
    content: normalizedContent,
    time: new Date().toLocaleTimeString(),
  });
  renderEvents();
}

function appendAgentEvent(event) {
  const type = event.type || 'event';
  const source = event.source || 'agent';
  const content = event.content == null ? '' : String(event.content);
  const key = `${type}\u0000${source}\u0000${content}`;
  if (state.eventKeys.has(key)) {
    return;
  }
  state.eventKeys.add(key);
  appendEvent(type, source, content);
}

function renderEvents() {
  els.eventTimeline.innerHTML = '';
  if (state.events.length === 0) {
    els.eventTimeline.innerHTML = '<div class="empty">暂无事件</div>';
    return;
  }
  state.events.slice(0, 80).forEach((event) => {
    const node = els.eventTemplate.content.cloneNode(true);
    node.querySelector('.event-meta').textContent = `${event.time} · ${event.type} · ${event.source}`;
    node.querySelector('.event-content').textContent = event.content;
    els.eventTimeline.appendChild(node);
  });
}

function clearEvents() {
  state.events = [];
  state.eventKeys.clear();
  renderEvents();
}

function setResult(text) {
  els.resultOutput.textContent = text;
}

function appendToResult(text) {
  els.resultOutput.textContent = `${els.resultOutput.textContent}\n${text}`.trim();
  els.resultOutput.scrollTop = els.resultOutput.scrollHeight;
}

function queueStreamText(text) {
  state.streamPendingText += text;
  if (state.streamRenderTimer) {
    return;
  }
  state.streamRenderTimer = window.setTimeout(flushStreamText, 80);
}

function flushStreamText() {
  state.streamRenderTimer = null;
  if (!state.streamPendingText) {
    return;
  }
  state.streamText += state.streamPendingText;
  state.streamPendingText = '';
  setResult(state.streamText || '等待文本输出...');
  els.resultOutput.scrollTop = els.resultOutput.scrollHeight;
}

function resetStreamState() {
  state.streamText = '';
  state.streamPendingText = '';
  if (state.streamRenderTimer) {
    window.clearTimeout(state.streamRenderTimer);
    state.streamRenderTimer = null;
  }
}

function toPreviewText(value) {
  if (typeof value === 'string') {
    return value;
  }
  try {
    return JSON.stringify(value, null, 2);
  } catch (error) {
    return String(value);
  }
}

function setBadge(text, type) {
  els.connectionBadge.textContent = text;
  els.connectionBadge.className = `badge ${type === 'error' ? 'error' : type === 'muted' ? 'muted' : ''}`.trim();
}

function setBusy(busy) {
  state.busy = busy;
  [els.runBtn, els.streamBtn, els.enterPlanBtn, els.exitPlanBtn, els.applyPermissionBtn].forEach((button) => {
    button.disabled = busy;
  });
}

function normalizeBase(value) {
  let base = (value || DEFAULT_API_BASE).trim().replace(/\/+$/, '');
  for (const suffix of AGENT_ENDPOINT_SUFFIXES) {
    if (base.endsWith(`${AGENT_API_PREFIX}${suffix}`)) {
      return base.slice(0, -suffix.length);
    }
  }
  if (base.endsWith(AGENT_API_PREFIX)) {
    return base;
  }
  return base;
}

function readStorage() {
  try {
    return JSON.parse(localStorage.getItem(STORAGE_KEY)) || {};
  } catch (error) {
    return {};
  }
}

function writeStorage() {
  localStorage.setItem(STORAGE_KEY, JSON.stringify({
    apiBase: state.apiBase,
    userId: els.userIdInput.value.trim(),
    sessionId: els.sessionIdInput.value.trim(),
  }));
}

function escapeHtml(value) {
  return String(value)
    .replaceAll('&', '&amp;')
    .replaceAll('<', '&lt;')
    .replaceAll('>', '&gt;')
    .replaceAll('"', '&quot;')
    .replaceAll("'", '&#39;');
}

init();
