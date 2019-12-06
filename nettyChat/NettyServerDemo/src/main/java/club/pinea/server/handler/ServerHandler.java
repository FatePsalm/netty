package club.pinea.server.handler;

import club.pinea.common.IOSession;
import club.pinea.message.MessageCTX;
import club.pinea.server.queue.ServerQueue;

/**
 * 处理消息接收请求
 * @author pineapple
 *
 */
public class ServerHandler {

	private ServerQueue queue = ServerQueue.getServerQueue();
	
	private ServerHandler() {}
	
	private static class ServerHandlerCreater {
		private static ServerHandler handler = new ServerHandler();
	}
	
	public static ServerHandler createServerHandler() {
		return ServerHandlerCreater.handler;
	}
	
	public void receiveMessage(Object message, IOSession session) {
		queue.receiveMessage(new MessageCTX(session, message));
	}
	
}
