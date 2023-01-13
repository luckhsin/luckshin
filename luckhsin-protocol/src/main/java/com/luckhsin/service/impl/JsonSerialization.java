package com.luckhsin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luckhsin.service.Serialization;

import java.io.IOException;

/**
 * @Description TODO
 * @Author dinghaodong
 * @Date 2023/1/12 16:09
 **/
public class JsonSerialization implements Serialization {
    private ObjectMapper objectMapper;

    public JsonSerialization(){
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public <T> T deSerialize(byte[] data, Class<T> clazz) {
        try {
            return objectMapper.readValue(data,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> byte[] serialize(T tClass) {
        try {
            return objectMapper.writeValueAsBytes(tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
