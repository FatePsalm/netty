package com.solace.springbootnetty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.solace.springbootnetty.entity.MsgEntity;
import com.solace.springbootnetty.enums.MsgEnum;
import com.solace.springbootnetty.util.ChannelPoolMap;
import com.solace.springbootnetty.enums.ParamEnum;
import com.solace.springbootnetty.util.AttributeMap;
import com.solace.springbootnetty.util.ChannelUtl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * 作者 CG
 * 时间 2019/12/11 10:17
 * 注释 业务处理
 */
public class BusinessWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        Map<String, String> stringMap = channelHandlerContext.channel().attr(AttributeMap.PARAMMAP).get();
        ParamEnum.ChatType type = ParamEnum.ChatType.findType(stringMap);
        ConcurrentMap<String, Channel> concurrentMap = ChannelPoolMap.getType(type);
        String text = textWebSocketFrame.text();
        JSONObject jsonObject = JSON.parseObject(text);
        String uid = stringMap.get(ParamEnum.Param.UID.getValue());
        String acceptId = jsonObject.getString("acceptId");
        String msg = jsonObject.getString("msg");
        MsgEntity msgEntity = new MsgEntity();
        msgEntity.setType(MsgEnum.Type.普通消息.getType());
        msgEntity.setSendId(uid);
        msgEntity.setAcceptId(acceptId);
        msgEntity.setMsg(msg);
        /*给接受者发送消息*/
        ChannelUtl.sendList(concurrentMap, Arrays.asList(acceptId,uid),JSON.toJSONString(msgEntity));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //删除连接池中的用户连接
        ChannelUtl.removeChatGroup(ctx);
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelUtl.removeChatGroup(ctx);
    }
}