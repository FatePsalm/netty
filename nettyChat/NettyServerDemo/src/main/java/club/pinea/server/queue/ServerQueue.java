package club.pinea.server.queue;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import club.pinea.message.MessageCTX;
import club.pinea.message.NetMessageHandler;
import club.pinea.message.bytes.BytesNetMessageHandler;
import club.pinea.message.object.ObjectNetMessageHandler;
import club.pinea.message.proto.ProtoBufMessage;
import club.pinea.message.proto.ProtoBufNetMessageHandler;
import club.pinea.message.string.StringNetMessageHandler;
import club.pinea.util.ThreadPoolUtil;


/**
 * 服务器消息队列
 * @author pineapple
 *
 */
public class ServerQueue{

	private ServerQueue() {}
	
	private static class ServerQueueCreater{
		private static ServerQueue queue = new ServerQueue();
	}
	
	public static ServerQueue getServerQueue() {
		return ServerQueueCreater.queue;
	}
	
	Logger log = LogManager.getLogger(ServerQueue.class);
	private volatile boolean flag = true;
	
	LinkedBlockingQueue<MessageCTX> linkedQueue = new LinkedBlockingQueue<>();
	
	public void begin() {
		int process = Runtime.getRuntime().availableProcessors();
		ThreadPoolUtil threadPool = ThreadPoolUtil.getInstance();
		for (int i = 0; i < process; i++) {
			threadPool.execute(new ServerQueueTaker(i));
		}
	}
	
	public void receiveMessage(MessageCTX message) {
		try {
			linkedQueue.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private class ServerQueueTaker implements Runnable{
		private int index;
		public ServerQueueTaker(int index) {
			this.index = index;
		}
		
		@Override
		public void run() {
			Thread.currentThread().setName(Thread.currentThread().getName()+"消息处理线程 socket" + this.index);
			while (flag) {
				MessageCTX message = null;
				try {
					message = linkedQueue.take();
				} catch (Exception e) {
					log.error("{}", e);
				}
				final MessageCTX msg = message;
				ThreadPoolUtil.getInstance().execute(() -> {
					//TODO 这里可以根据message来做出不同的netMessageHandler处理，我这里就直接使用StringNetMessageHandler处理
					try {
						Object obj = msg.getMessasge();
						if(obj instanceof String) {
							NetMessageHandler<MessageCTX> msgHandler = new StringNetMessageHandler();
							msgHandler.doMessage(msg);
						} else if(obj instanceof ProtoBufMessage.Message) {
							NetMessageHandler<MessageCTX> msgHandler = new ProtoBufNetMessageHandler();
							msgHandler.doMessage(msg);
						} else if(obj instanceof byte[]) {
							NetMessageHandler<MessageCTX> msgHandler = new BytesNetMessageHandler();
							msgHandler.doMessage(msg);
						} else {
							NetMessageHandler<MessageCTX> msgHandler = new ObjectNetMessageHandler();
							msgHandler.doMessage(msg);
						}
					} catch (Exception e) {
						log.error("{}", e);
					}
				});
			}
		}
	}
	
}
