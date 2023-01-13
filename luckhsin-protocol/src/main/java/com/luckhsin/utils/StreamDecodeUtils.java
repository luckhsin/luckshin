package com.luckhsin.utils;

import com.luckhsin.common.domain.RpcRequest;
import com.luckhsin.common.domain.RpcResponse;
import com.luckhsin.service.Serialization;
import com.luckhsin.service.impl.JsonSerialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description TODO
 * @Author dinghaodong
 * @Date 2023/1/13 10:54
 **/
public class StreamDecodeUtils {
    private static Serialization serialization = null;
    static {
        serialization = new JsonSerialization();
    }

    public static <T> T decode(InputStream inputStream,Class<T> c){
        T t = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            t = serialization.deSerialize(byteArrayOutputStream.toByteArray(), c);
        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }
}
