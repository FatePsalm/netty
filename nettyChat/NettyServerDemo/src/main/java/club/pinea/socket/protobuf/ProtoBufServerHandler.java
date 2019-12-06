package club.pinea.socket.protobuf;

import club.pinea.common.BaseServerHandler;
import club.pinea.common.IOSession;
import club.pinea.container.GlobalSessionContainer;
import club.pinea.message.proto.ProtoBufMessage.Message;
import club.pinea.server.handler.ServerHandler;
import io.netty.channel.ChannelHandlerContext;

public class ProtoBufServerHandler extends BaseServerHandler<Message> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
		logger.info("接收到客户端消息" + msg);
		IOSession session  = GlobalSessionContainer.getIOSession(ctx.hashCode());
		//处理心跳消息
//		if (msg.getCommand() == CmdType.C_S_HEART_VALUE) {
		//log.info("收到IP:" + session.getIp() +"的心跳消息");
		//TODO 返回用户心跳	
		//return;
//		}
		ServerHandler.createServerHandler().receiveMessage(msg, session);
	}


}
