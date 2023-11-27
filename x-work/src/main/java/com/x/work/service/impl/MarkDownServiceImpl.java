package com.x.work.service.impl;

import com.x.common.enums.ResponseCodeEnum;
import com.x.common.response.BaseResult;
import com.x.common.response.ResultUtil;
import com.x.common.utils.ListUtil;
import com.x.config.redis.constants.RedisCommonConstant;
import com.x.work.db.entity.File;
import com.x.work.db.enums.FileTypeEnum;
import com.x.work.db.service.FileIService;
import com.x.work.domain.FileSimpleDo;
import com.x.work.service.MarkDownService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2023/2/23 17:28
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MarkDownServiceImpl implements MarkDownService {

    private final FileIService fileIService;


    @Override
    @Cacheable(cacheNames = "MD_FILE_CONTENT=" + 60, key = "#id", cacheManager = RedisCommonConstant.CACHE_MANAGER_NAME)
    public BaseResult<String> getMdFileStr(Long id) {
        File file = fileIService.lambdaQuery()
                .eq(File::getFileId, id)
                .one();
        if (null == file) {
            return ResultUtil.buildResultError(ResponseCodeEnum.FILE_NOT_EXIST);
        }

        return ResultUtil.buildResultSuccess(file.getFileContent());
    }

    @Override
    @Cacheable(cacheNames = "MD_FILE_INFO=" + 60, key = "#menuId", cacheManager = RedisCommonConstant.CACHE_MANAGER_NAME)
    public BaseResult<List<FileSimpleDo>> getMdList(Long menuId) {
        List<File> files = fileIService.lambdaQuery()
                .eq(File::getFileType, FileTypeEnum.MD)
                .eq(File::getMenuId, menuId)
                .orderBy(true, true, File::getCreateTime)
                .list();

        List<FileSimpleDo> simpleDos = ListUtil.copyList(files, FileSimpleDo.class);
        return ResultUtil.buildResultSuccess(simpleDos);
    }
}
