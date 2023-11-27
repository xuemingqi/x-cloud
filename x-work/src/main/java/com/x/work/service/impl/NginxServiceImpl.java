package com.x.work.service.impl;

import com.x.common.response.BaseResult;
import com.x.common.response.ResultUtil;
import com.x.common.utils.CompletableUtil;
import com.x.common.utils.DateUtil;
import com.x.common.utils.FileUtil;
import com.x.work.domain.NginxLogDo;
import com.x.work.dto.ServerMessageDto;
import com.x.work.service.LocationService;
import com.x.work.service.NginxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author : xuemingqi
 * @since : 2023/2/10 17:21
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class NginxServiceImpl implements NginxService {

    private final LocationService locationService;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public BaseResult<List<NginxLogDo>> getNginxLog(ServerMessageDto param) {
        //ip->时间
        Map<String, String> ipList = getNginxLogIp(param);

        //查询地址信息
        List<CompletableFuture<NginxLogDo>> futures = ipList.entrySet().stream().map(entry -> CompletableFuture
                .supplyAsync(() -> locationService.getLocationByIp(entry.getKey()), threadPoolTaskExecutor)
                .thenApplyAsync(r -> NginxLogDo.builder()
                        .ip(entry.getKey())
                        .time(DateUtil.parseLinuxTime(entry.getValue()))
                        .city(r.getCity())
                        .addr(r.getAddr())
                        .build())).collect(Collectors.toList());

        //等待任务结束
        //futures.forEach(CompletableFuture::join);
        CompletableUtil.join(futures);

        //获取返回结果
        List<NginxLogDo> result = CompletableUtil.get(futures);

        return ResultUtil.buildResultSuccess(result);
    }

    private static Map<String, String> getNginxLogIp(ServerMessageDto param) {
        //读文件
        List<String> lines = FileUtil.getServerLogLines(param.getPath(), param.getSize());

        //ip->时间
        Map<String, String> ipList = new HashMap<>();
        lines.forEach(line -> ipList.put(line.substring(0, line.indexOf("-")),
                line.substring(line.indexOf("[") + 1, line.indexOf("]"))));

        return ipList;
    }
}
