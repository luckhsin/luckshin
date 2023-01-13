package com.luckhsin.common.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author dinghaodong
 * @Date 2023/1/13 9:57
 **/
@Data
public class RpcRequest implements Serializable {
    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] params;
    private Class<?>[] paramsTypes;
}
