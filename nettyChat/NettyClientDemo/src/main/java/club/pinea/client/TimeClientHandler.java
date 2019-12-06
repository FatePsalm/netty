package club.pinea.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TimeClientHandler extends SimpleChannelInboundHandler<String> {

	private String message = "Hello World!";
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		ctx.writeAndFlush(message);
		System.out.println("激活连接并且发送消息");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println(msg);
		ctx.close();
	}
}
