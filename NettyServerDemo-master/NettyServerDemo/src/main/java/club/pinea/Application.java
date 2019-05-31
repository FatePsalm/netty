package club.pinea;

import java.util.concurrent.atomic.AtomicInteger;

import club.pinea.server.queue.ServerQueue;
import club.pinea.socket.custom.NettySocketServer;
import club.pinea.socket.protobuf.ProtoBufServer;

public class Application {
	
	public static AtomicInteger flag = new AtomicInteger(0);
	
	public static void main(String[] args) {
//		NettySocketServer server = new NettySocketServer(8112);
		ProtoBufServer server = new ProtoBufServer(8112);
		new Thread(server).start();
		ServerQueue.getServerQueue().begin();
	}
}
