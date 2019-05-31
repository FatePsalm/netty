package club.pinea.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import club.pinea.common.IOSession.SessionType;
import club.pinea.container.GlobalSessionContainer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class BaseServerHandler<T> extends SimpleChannelInboundHandler<T> {
	
	public static Logger logger = LogManager.getLogger(BaseServerHandler.class);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		logger.info("有一个客户端连接上来了");
		IOSession session  = GlobalSessionContainer.getIOSession(ctx.hashCode());
		if(session == null){
			session = this.createIOSession(ctx);
			GlobalSessionContainer.addIOSession(session);
		}
	}

	@Override
	protected abstract void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception;

	protected IOSession createIOSession(ChannelHandlerContext ctx){
		IOSession session = new IOSession();
		session.setContext(ctx);
		session.setType(SessionType.SOCKET);
		return session;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("{}", cause);
		super.exceptionCaught(ctx, cause);
	}
	
	@Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		IOSession session  = GlobalSessionContainer.getIOSession(ctx.hashCode());
		if(session == null){
			session = this.createIOSession(ctx);
			GlobalSessionContainer.addIOSession(session);
		}
		//TODO 处理用户掉线
    }
}
