package com.x.framework.db.logback;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.x.framework.common.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * @author ：xuemingqi
 * @since ：created in 2023/1/10 14:18
 */
@Slf4j
@Component
@AllArgsConstructor
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
@ConditionalOnClass(BaseMapper.class)
public class MybatisLogInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        //获取配置信息
        Configuration configuration = mappedStatement.getConfiguration();
        //通过配置信息和BoundSql对象来生成带值得sql语句
        String sql = geneSql(configuration, boundSql);
        //打印sql语句以及执行结果
        Object returnValue = invocation.proceed();
        log.info("SQL:[{}],result:[{}]", sql, StringUtils.left(JsonUtil.toJsonStrIfNotAlready(returnValue), 1000));
        return returnValue;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private static String geneSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("\\s+", " ");

        if (!parameterMappings.isEmpty() && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    Object value = metaObject.hasGetter(propertyName) ? metaObject.getValue(propertyName) : boundSql.getAdditionalParameter(propertyName);
                    if (value != null) {
                        sql = sql.replaceFirst("\\?", getParameterValue(value));
                    }
                }
            }
        }
        return sql;
    }

    private static String getParameterValue(Object obj) {
        if (obj instanceof String str) {
            return "'" + str + "'";
        } else if (obj instanceof Date date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            return "'" + formatter.format(date) + "'";
        } else {
            return obj != null ? obj.toString() : "";
        }
    }
}
