package com.x.work.service;

import com.x.common.response.BaseResult;
import com.x.work.domain.NginxLogDo;
import com.x.work.dto.ServerMessageDto;

import java.util.List;

/**
 * @author: xuemingqi
 * @since: 2023/2/10 17:21
 */
public interface NginxService {

    /**
     * 获取nginx访问日志
     */
    BaseResult<List<NginxLogDo>> getNginxLog(ServerMessageDto param);
}
