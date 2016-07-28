package com.pite.r.netty;

import com.pite.r.read.Read3915Data;
import com.pite.r.tool.TestRCMD;

import android.util.Log;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端接收消息处理
 */
public class ServerReceHandler extends ChannelInboundHandlerAdapter {
	private Read3915Data readData = null;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		byte[] bt = TestRCMD.set0x35Connect();
		ByteBuf buf = ctx.alloc().buffer().writeBytes(bt);
		ctx.writeAndFlush(buf);
		readData = new Read3915Data();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] bt = new byte[buf.readableBytes()];
		buf.readBytes(bt);
		// ctx.writeAndFlush(Unpooled.copiedBuffer(bt)); // 回显数据
		Log.e("2", "接收到3915数据 ：" + TestRCMD.bytesToHexString(bt));
		readData.addBytes(bt, ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}

}
