package com.x.framework.mq.property;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


/**
 * @author : xuemingqi
 * @since : 2024-08-30 17:18
 */
@Component
@ConfigurationPropertiesBinding
public class MqTypeConverter implements Converter<String, MqProperty.MqTypeEnum> {

    @Override
    public MqProperty.MqTypeEnum convert(String s) {
        return MqProperty.MqTypeEnum.fromType(s);
    }
}
