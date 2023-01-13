package com.luckhsin.service.impl;

import com.luckhsin.common.domain.MsgDo;
import com.luckhsin.service.MsgService;

/**
 * @Description TODO
 * @Author dinghaodong
 * @Date 2023/1/13 10:01
 **/
public class MsgServiceImpl implements MsgService {
    @Override
    public MsgDo getMsgById(String id) {
        MsgDo msgDo = new MsgDo();
        msgDo.setName("服务端返回ID为"+id+"的msgDo");
        return msgDo;
    }
}
