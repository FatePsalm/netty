/**
 * niuniu-server_com.decibel.game.niuniu.bootstrap_IOSession.java
 *
 */
package club.pinea.common;

import java.net.InetSocketAddress;

import io.netty.channel.ChannelHandlerContext;

/**
 * 功能描述:
 *
 * @createTime: 2017年7月28日 下午12:24:34
 * @version: 6.0
 * @updateTime: 2017年7月28日 下午12:24:34
 * @changesSum:
 */
public class IOSession {
    private int sessionId;

    private SessionType type = SessionType.SOCKET;

    ChannelHandlerContext context;

    private String ip;

    public int getSessionId() {
        return sessionId;
    }

    @Override
    public int hashCode() {
        return getSessionId();
    }

    public ChannelHandlerContext getContext() {
        return context;
    }

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
        sessionId = context.hashCode();
        ip = ((InetSocketAddress) context.channel().remoteAddress()).getAddress().getHostAddress();
    }

    public void setType(SessionType type) {
        this.type = type;
    }

    public SessionType getType() {
        return type;
    }

    public String getIp() {
        return ip;
    }

    public void send(String msg) {
        this.context.writeAndFlush(msg);
    }

    public void disConnect() {
        if (this.context != null) {
            try {
                this.context.close();
            } catch (Throwable t) {

            }
        }
    }

    public enum SessionType {
        SOCKET, WEBSOCKET
    }
}
