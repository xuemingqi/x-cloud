package com.x.framework.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xuemingqi
 */
@Slf4j
public class JsonUtil {

    public final static ObjectMapper OBJECT_MAPPER = createDefaultMapper();


    private static ObjectMapper createDefaultMapper() {
        ObjectMapper mapper = new ObjectMapper();
        DateTimeFormatter LocalDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter LocalDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter LocalTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(LocalDateTimeFormatter));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(LocalDateTimeFormatter));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(LocalDateFormatter));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(LocalDateFormatter));
        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(LocalTimeFormatter));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(LocalTimeFormatter));

        mapper.registerModule(timeModule);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
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

    public static <T> T strToObject(String object, TypeReference<T> type) {
        T t = null;
        try {
            t = OBJECT_MAPPER.readValue(object, type);
        } catch (JsonProcessingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return t;
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
