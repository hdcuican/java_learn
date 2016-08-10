package com.java.nioNetty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.java.nioNetty.pool.NioSelectorRunnablePool;
/**
 * 
 * <p>Decsription: 用NIO实现一个简单的netty框架</p>
 * @author  shadow
 * @date  2016年8月10日
 */
public class MianTest {

	public static void main(String[] args) {

		//初始化线程
		NioSelectorRunnablePool nioSelectorRunnablePool = new NioSelectorRunnablePool(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		
		//获取服务类
		ServerBootstrap bootstrap = new ServerBootstrap(nioSelectorRunnablePool);
		
		//绑定端口
		bootstrap.bind(new InetSocketAddress(10101));
		
		System.out.println("start");
	}

}
