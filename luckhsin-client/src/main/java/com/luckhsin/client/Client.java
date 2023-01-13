package com.luckhsin.client;

import com.luckhsin.common.domain.MsgDo;
import com.luckhsin.common.domain.RpcRequest;
import com.luckhsin.common.domain.RpcResponse;
import com.luckhsin.service.MsgService;
import com.luckhsin.service.impl.JsonSerialization;
import com.luckhsin.utils.StreamDecodeUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @Description TODO
 * @Author dinghaodong
 * @Date 2022/11/9 10:37
 **/
@Slf4j
public class Client {
    public static void main(String[] args) {
        JsonSerialization jsonSerialization = new JsonSerialization();
        try {
            Socket socket = new Socket("192.168.52.7",8080);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            //request
            MsgService msgService = (MsgService)Proxy.newProxyInstance(MsgService.class.getClassLoader(), new Class[]{MsgService.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    RpcRequest request = new RpcRequest();
                    request.setInterfaceName(method.getDeclaringClass().getName());
                    request.setMethodName(method.getName());
                    request.setParams(args);
                    request.setParamsTypes(method.getParameterTypes());
                    outputStream.write(jsonSerialization.serialize(request));
                    outputStream.flush();
                    socket.shutdownOutput();
                    RpcResponse rpcResponse = StreamDecodeUtils.decode(inputStream, RpcResponse.class);
                    Object data = rpcResponse.getData();
                    return data;
                }
            });
            MsgDo demo = msgService.getMsgById("demo");
            log.info("接收到服务端返回值:"+demo);
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
