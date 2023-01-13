package com.luckhsin.server;


import com.luckhsin.common.domain.MsgDo;
import com.luckhsin.common.domain.RpcRequest;
import com.luckhsin.common.domain.RpcResponse;
import com.luckhsin.service.MsgService;
import com.luckhsin.service.impl.JsonSerialization;
import com.luckhsin.service.impl.MsgServiceImpl;
import com.luckhsin.utils.StreamDecodeUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author dinghaodong
 * @Date 2022/11/9 10:28
 **/
@Slf4j
public class Server {

    static final int PORT = 8080;
    public static void main(String[] args) throws Exception {
        JsonSerialization jsonSerialization = new JsonSerialization();
        ServerSocket socket = new ServerSocket(PORT);
        while (true){
            Socket server = socket.accept();
            new Thread(()->{
                try {
                    //接收客户端信息流
                    InputStream inputStream = server.getInputStream();
                    OutputStream outputStream = server.getOutputStream();
                    RpcRequest request = StreamDecodeUtils.decode(inputStream, RpcRequest.class);
                    log.info("接收到客户端发送消息："+request);
                    MsgService msgService = new MsgServiceImpl();
                    Class<? extends MsgService> aClass1 = msgService.getClass();
                    Method method = aClass1.getMethod(request.getMethodName(), request.getParamsTypes());
                    Object invoke = method.invoke(msgService, request.getParams());
                    RpcResponse response = RpcResponse.success(invoke);
                    log.info("返回给客户端消息:"+response);
                    outputStream.write(jsonSerialization.serialize(response));
                    outputStream.flush();
                    inputStream.close();
                    outputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
