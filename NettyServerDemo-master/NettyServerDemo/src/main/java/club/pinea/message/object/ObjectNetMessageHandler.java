package club.pinea.message.object;

import club.pinea.message.MessageCTX;
import club.pinea.message.NetMessageHandler;
import io.netty.channel.ChannelFutureListener;

public class ObjectNetMessageHandler implements NetMessageHandler<MessageCTX> {

	@Override
	public void doMessage(MessageCTX msg) {
		Object str = msg.getMessasge();
		//返回消息并关闭通道
		msg.getSession().getContext().writeAndFlush(str).addListener(ChannelFutureListener.CLOSE);
	}

}
