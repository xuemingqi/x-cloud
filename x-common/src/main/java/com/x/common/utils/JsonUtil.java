package com.x.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xuemingqi
 */
@Slf4j
public class JsonUtil {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final static JavaTimeModule TIME_MODULE = new JavaTimeModule();

    static {
        OBJECT_MAPPER.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TIME_MODULE.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        TIME_MODULE.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        OBJECT_MAPPER.registerModule(TIME_MODULE);
    }

    public static String toJsonStr(Object data) {
        String str = null;
        try {
            OBJECT_MAPPER.setDefaultPropertyInclusion(Include.ALWAYS);
            str = OBJECT_MAPPER.writeValueAsString(data);
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return str;
    }

    public static String toJsonStrNonNull(Object data) {
        String str = null;
        try {
            OBJECT_MAPPER.setDefaultPropertyInclusion(Include.NON_NULL);
            str = OBJECT_MAPPER.writeValueAsString(data);
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return str;
    }

    public static <T> T jsonToBean(String json, Class<T> type) {
        T t = null;
        try {
            t = OBJECT_MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return t;
    }

    public static JsonNode readTree(String json) throws IOException {
        return OBJECT_MAPPER.readTree(json);
    }

    public static <T> T objectToObject(Object object, TypeReference<T> type) {
        return OBJECT_MAPPER.convertValue(object, type);
    }

    public static boolean isJsonString(String jsonString) {
        try {
            OBJECT_MAPPER.readTree(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String toJsonStrIfNotAlready(Object object) {
        if (object instanceof String && isJsonString((String) object)) {
            return (String) object;
        } else {
            return toJsonStr(object);
        }
    }
}
