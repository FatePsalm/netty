package club.pinea.socket.custom;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import club.pinea.message.coder.ByteStringDecoder;
import club.pinea.message.coder.ByteStringEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public class NettySocketServer implements Runnable {

	public static Logger logger = LogManager.getLogger(NettySocketServer.class);
	private int PORT = 8007;
	
	public NettySocketServer(int port) {
		this.PORT = port;
	}
	
	@Override
	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 100)
			.handler(new LoggingHandler(LogLevel.DEBUG))
			.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							//心跳
//							p.addLast("idle", new IdleStateHandler(5, 0, 0));
							// 需要解码的目标类
							p.addLast("encoder", new ByteStringEncoder());
							p.addLast("decoder", new ByteStringDecoder());
							//消息处理的handler
							p.addLast("handler", new NettyServerMessageHandler());
						}
					});
			logger.info("TCP协议地址:TCP " + "://" + InetAddress.getLocalHost().getHostAddress() + ":" + PORT + '/'
					+ ",tcp协议成功启动.........");

			ChannelFuture f = b.bind(PORT).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException | UnknownHostException e) {
			logger.error("InterruptedException:" + e.toString());
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
