package com.luckhsin.service;

public interface Serialization {
    public <T> T deSerialize(byte[] data,Class<T> clazz);
    public <T> byte[] serialize(T tClass);
}
