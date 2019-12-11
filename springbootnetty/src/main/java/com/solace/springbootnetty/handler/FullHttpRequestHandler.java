package com.solace.springbootnetty.handler;

import com.solace.springbootnetty.enums.ParamEnum;
import com.solace.springbootnetty.util.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * 作者 CG
 * 时间 2019/12/11 11:00
 * 注释 路由Handler
 * 主要负责分发和授权
 */
public class FullHttpRequestHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
//            LogUtil.logger(this.getClass()).info("连接处理FullHttpRequest");
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
//            LogUtil.logger(this.getClass()).info("连接处理uri[{}]", uri);
            Map<String, String> paramMap = RequestUtil.paramMap(uri);
            ctx.channel().attr(AttributeMap.PARAMMAP).setIfAbsent(paramMap);
//            LogUtil.logger(this.getClass()).info("uri参数[{}]", paramMap);
            String url = paramMap.get(ParamEnum.Param.URL.getValue());
            request.setUri(url);
            ChannelUtl.checkParam(ctx, paramMap);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        channelHandlerContext.writeAndFlush(new TextWebSocketFrame("连接正在初始化..."));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ChannelUtl.exceptionCaughtTimeOut(ctx,new TextWebSocketFrame(cause.getMessage()));
    }
}