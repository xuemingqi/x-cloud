package com.github.xiaoymin.knife4j.spring.gateway.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Compatibility override for Knife4j 4.5.0 on Spring Framework 7.
 */
public final class PathUtils {

    private static final Logger log = LoggerFactory.getLogger(PathUtils.class);

    public static final String DEFAULT_CONTEXT_PATH = "/";

    static final String DOC_URL = "/doc.html";

    static final Pattern PATTERN = Pattern.compile("(.*?)/doc\\.html", Pattern.CASE_INSENSITIVE);

    private PathUtils() {
    }

    public static String getContextPath(String url) {
        if (StringUtils.hasLength(url)) {
            try {
                String path = URI.create(url).getPath();
                var matcher = PATTERN.matcher(path);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
        return DEFAULT_CONTEXT_PATH;
    }

    public static String append(String... paths) {
        if (Objects.isNull(paths) || paths.length == 0) {
            return DEFAULT_CONTEXT_PATH;
        }
        String path = Arrays.stream(paths)
                .filter(StrUtil::isNotBlank)
                .map(item -> "/" + item)
                .collect(Collectors.joining());
        return path.replaceAll("/+", "/");
    }

    public static String getDefaultContextPath(ServerHttpRequest request) {
        String contextPath = request.getPath().contextPath().value();
        if (!StringUtils.hasLength(contextPath)) {
            HttpHeaders headers = request.getHeaders();
            List<String> refererList = headers.get(HttpHeaders.REFERER);
            if (refererList != null && !refererList.isEmpty()) {
                String referer = refererList.get(0);
                log.debug("Referer:{}", referer);
                contextPath = getContextPath(referer);
            } else {
                contextPath = DEFAULT_CONTEXT_PATH;
            }
        }
        return contextPath;
    }

    public static String processContextPath(String contextPath) {
        String value = contextPath;
        if (DEFAULT_CONTEXT_PATH.equals(value)) {
            value = "";
        }
        if (value.endsWith(DEFAULT_CONTEXT_PATH)) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    public static boolean contextPathNull(String contextPath) {
        return StrUtil.isNotBlank(contextPath) && !DEFAULT_CONTEXT_PATH.equalsIgnoreCase(contextPath);
    }
}
