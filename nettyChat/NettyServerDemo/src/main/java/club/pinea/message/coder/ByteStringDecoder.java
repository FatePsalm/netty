package club.pinea.message.coder;

import java.nio.charset.Charset;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * @author pineapple
 * @date 2017年12月27日 下午4:54:50
 * @description xxx
 */
public class ByteStringDecoder extends MessageToMessageDecoder<ByteBuf>{
	
	
    private final Charset charset;
 
    /**
     * Creates a new instance with the current system character set.
     */
    public ByteStringDecoder() {
        this(Charset.defaultCharset());
    }
 
    /**
     * Creates a new instance with the specified character set.
     */
    public ByteStringDecoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
		
		//跳过四个字节 把00 00 00 00 跳过
//		msg = msg.skipBytes(3);
		//剔除最后四个字节
		msg = msg.copy(0, msg.readableBytes() - 3);
		//把剩余字节加入集合
		out.add(msg.toString(charset));
	}
	
}
