package com.pite.r.netty;

import java.net.InetSocketAddress;

import android.util.Log;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 3915服务端
 */
public class ConnectServer {
	private int port;
	private EventLoopGroup boss = new NioEventLoopGroup();
	private EventLoopGroup work = new NioEventLoopGroup();

	public ConnectServer(int port) {
		this.port = port;
		setConnect();
	}

	/**
	 * 建立服务器
	 */
	public void setConnect() {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boss, work).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
				.option(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE, true)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel channel) throws Exception {
						channel.pipeline().addLast(new ServerReceHandler());
					}
				});
		try {
			ChannelFuture channelFuture = bootstrap.bind(8888).sync();
			if (channelFuture.isSuccess()) {
				Log.e("2", "connect server success");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
	}
}
