package club.pinea.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import java.util.Scanner;

import club.pinea.message.proto.ProtoBufMessage.Message;
import club.pinea.message.proto.ProtoBufMessage.Message.Builder;

public class Client implements Runnable {
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 8112;
	
	private long id;
	
	public Client() {}
	public Client(long id) {
		this.id = id;
	}
	
	
	@Override
	public void run() {
//		stringSender();
		try {
			Socket socket = new Socket(HOST, PORT);
			Builder builder = Message.newBuilder();
			builder.setId(id);
			builder.setMsg("Hello World!");
			
			socket.getOutputStream().write(builder.build().toByteArray());
			socket.getOutputStream().flush();
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			byte [] bs = new byte[bis.available()];
			bis.read(bs);
			Message msg = Message.parseFrom(bs);
			System.out.println("id : " + msg.getId() + "message : " + msg.getMsg());
			if (!socket.isClosed())
				socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void stringSender() {
		try {
			Socket socket = new Socket(HOST, PORT);
			socket.getOutputStream().write(("Hello World!" + new String(new byte[] { 6, 3, 7 })).getBytes());
//			socket.getOutputStream().flush();
			Scanner sc = new Scanner(socket.getInputStream());
			String res = sc.nextLine();
			System.out.println(res);
			sc.close();
			if (!socket.isClosed())
				socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(new Client(i)).start();
		}
	}
}
