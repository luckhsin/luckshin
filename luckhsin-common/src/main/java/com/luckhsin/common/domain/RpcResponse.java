package com.luckhsin.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author dinghaodong
 * @Date 2023/1/13 9:58
 **/
@Data
@NoArgsConstructor
public class RpcResponse implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public RpcResponse(int code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static RpcResponse message(int code,String message){
        return new RpcResponse(code,message,null);
    }

    public static RpcResponse success(Object data){
        return new RpcResponse(Status.SUCCESS.getCode(), Status.SUCCESS.getStandardMessage(),data);
    }

    public static RpcResponse fail(String message){
        return new RpcResponse(Status.INTERNAL_SERVER_ERROR.getCode(),message,null);
    }

    public enum Status{
        //一些状态码的描述
        SUCCESS(200,"OK"),
        BAD_PARM(201,"Parameter is not standard"),
        BAD_REQUEST(400,"Bad Request"),
        INTERNAL_SERVER_ERROR(500,"Unknown Internal Error"),
        NOT_VALID_PARAM(40005,"Not valid Params"),
        NOT_SUPPORTED_OPERATION(40006,"Operation not supported"),
        NOT_LOGIN(50000,"Not Login");
        private int code;
        private String standardMessage;

        Status(int code, String standardMessage) {
            this.code = code;
            this.standardMessage = standardMessage;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStandardMessage() {
            return standardMessage;
        }

        public void setStandardMessage(String standardMessage) {
            this.standardMessage = standardMessage;
        }
    }
}
