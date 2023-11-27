package com.x.work.service;

import com.x.common.response.BaseResult;
import com.x.work.domain.FileSimpleDo;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2023/2/23 17:28
 */
public interface MarkDownService {

    /**
     * 获取md文件字内容
     *
     * @param id 文档id
     * @return 文件内容
     */
    BaseResult<String> getMdFileStr(Long id);

    /**
     * 获取文件索引列表
     *
     * @param menuId 菜单id
     * @return 文件索引列表
     */
    BaseResult<List<FileSimpleDo>> getMdList(Long menuId);
}
