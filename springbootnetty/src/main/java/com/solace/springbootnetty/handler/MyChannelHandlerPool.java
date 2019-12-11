package com.solace.springbootnetty.handler;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

 /**
   * 作者 CG
   * 时间 2019/12/11 20:35
   * 注释 自带管理池
   */
public class MyChannelHandlerPool {

    public MyChannelHandlerPool(){}

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}