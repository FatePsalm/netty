package com.solace.springbootnetty.util;

import com.alibaba.fastjson.JSON;
import com.solace.springbootnetty.entity.MsgEntity;
import com.solace.springbootnetty.enums.MsgEnum;
import com.solace.springbootnetty.enums.ParamEnum;
import com.solace.springbootnetty.handler.FullHttpRequestHandler;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentMap;

/**
 * 作者 CG
 * 时间 2019/12/10 15:14
 * 注释 创建用户管理池
 */
public class ChannelUtl {

    /**
     * 作者 CG
     * 时间 2019/12/10 15:15
     * 注释 添加
     */
   public static void add(ConcurrentMap<String, Channel> map,String uuid, Channel channel) {
        map.put(uuid, channel);
    }

    /**
     * 作者 CG
     * 时间 2019/12/10 15:15
     * 注释 删除
     */
    public static void remove(ConcurrentMap<String, Channel> map,String uuid) {
        map.remove(uuid);
    }
     /**
       * 作者 CG
       * 时间 2019/12/11 15:00
       * 注释 群发
       */
    public static void sendList(ConcurrentMap<String, Channel> map, List<String> uuid, String text) {
         uuid.forEach(x->{
             Channel channel = map.get(x);
             if (channel!=null&&channel.isOpen()){
                 channel.writeAndFlush(new TextWebSocketFrame(text));
             }
         });
     }
    /**
     * 作者 CG
     * 时间 2019/12/11 14:56
     * 注释 群发排除自己
     */
    public static void sendAll(ConcurrentMap<String, Channel> map,String uuid, String text) {
        map.forEach((k, v) -> {
            if (!k.equals(uuid)&&v.isOpen()) {
                v.writeAndFlush(new TextWebSocketFrame(text));
            }
        });
    }
     /**
       * 作者 CG
       * 时间 2019/12/11 19:52
       * 注释 用户下线
       */
     public static void removeChatGroup(ChannelHandlerContext ctx){
         Map<String, String> stringMap = ctx.channel().attr(AttributeMap.PARAMMAP).get();
         ConcurrentMap<String, Channel> type = ChannelPoolMap.getType(ParamEnum.ChatType.findType(stringMap));
         String uid = stringMap.get(ParamEnum.Param.UID.getValue());
         ChannelUtl.remove(type,uid);
         if (ctx.channel().isOpen()) {
             ctx.channel().close();
         }
         //通知全部用户
         MsgEntity entity = new MsgEntity();
         entity.setType(MsgEnum.Type.下线消息.getType());
         entity.setMsg(uid);
         ChannelUtl.sendAll(type,uid,JSON.toJSONString(entity));
     }
     /**
       * 作者 CG
       * 时间 2019/12/11 19:31
       * 注释 添加用户
       */
     public static void addChatGroup(ParamEnum.ChatType chatType,String uid,Channel channel){
         //全局推送
         ConcurrentMap<String, Channel> type = ChannelPoolMap.getType(chatType);
         //添加
         ChannelUtl.add(type,uid,channel);
         //推送消息
         MsgEntity entity = new MsgEntity();
         entity.setType(MsgEnum.Type.上线消息.getType());
         entity.setMsg(uid);
         ChannelUtl.sendAll(type,uid, JSON.toJSONString(entity));
     }
     /**
       * 作者 CG
       * 时间 2019/12/11 17:54
       * 注释 异常回写
       */
    public  static  void exceptionCaughtTimeOut(ChannelHandlerContext ctx, DefaultByteBufHolder msg) {
        ctx.channel().writeAndFlush(msg);
        ctx.close();
    }

    /**
     * 作者 CG
     * 时间 2019/12/11 15:13
     * 注释 参数验证
     */
   public static void checkParam(ChannelHandlerContext ctx, Map<String, String> paramMap)  {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //获取url中的聊天类型
                String uid = paramMap.get(ParamEnum.Param.UID.getValue());
                if (StringUtils.isEmpty(uid))
                    exceptionCaughtTimeOut(ctx, new TextWebSocketFrame(ParamEnum.Param.UID.getValue() + "参数错误!"));
                ParamEnum.ChatType chatType = ParamEnum.ChatType.findType(paramMap);
                //如果参数NULL直接关闭通道
                if (chatType == null)
                    exceptionCaughtTimeOut(ctx, new TextWebSocketFrame("chatType参数错误!"));
                ctx.pipeline().remove(FullHttpRequestHandler.class);
                boolean open = ctx.channel().isOpen();
                if (open){
                    addChatGroup(chatType,uid,ctx.channel());
                }
            }
        }, 50);
    }
}
