package com.litc.common.util.comet4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.comet4j.core.CometConnection;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

import com.litc.common.util.Constant;

public class CometUtil extends ConnectListener implements
		ServletContextListener {
	/**
	 * 初始化上下文
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		// CometContext ： Comet4J上下文，负责初始化配置、引擎对象、连接器对象、消息缓存等。
		CometContext cc = CometContext.getInstance();
		// 注册频道，即标识哪些字段可用当成频道，用来作为向前台传送数据的“通道”
		cc.registChannel(Constant.CHANNEL_BOOK_ONIX_CHECK);
		// 添加监听器
		CometEngine engine = CometContext.getInstance().getEngine();
		engine.addConnectListener(this);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean handleEvent(ConnectEvent connEvent) {
		// TODO Auto-generated method stub
		final CometConnection conn = connEvent.getConn();
		// Object userId =
		// conn.getRequest().getSession().getAttribute("currentUserId");
		// CacheManager.putContent(userId.toString(), connEvent);
		return true;
	}

	private void doCache(final CometConnection conn, String userId) {
		if (userId != null) {
			CacheManager.putContent(conn.getId(), String.valueOf(userId),
					Constant.EXPIRE_AFTER_ONE_HOUR);
		}
	}

	/**
	 * 推送给所有的客户端
	 * 
	 * @param comet
	 */
	public void pushToAll(String message) {
		try {
			CometEngine engine = CometContext.getInstance().getEngine();
			// 推送到所有客户端
			engine.sendToAll(Constant.CHANNEL_BOOK_ONIX_CHECK,message);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

	}

	/**
	 * 推送给指定客户端
	 * 
	 * @param comet
	 */
	public void pushTo(String userId,String message) {
		try {
			ConnectEvent connEvent = (ConnectEvent) CacheManager.getContent(userId).getValue();
			final CometConnection conn = connEvent.getConn();
			// 建立连接和用户的关系
			doCache(conn, userId);
			final String connId = conn.getId();
			CometEngine engine = CometContext.getInstance().getEngine();
			if (CacheManager.getContent(connId).isExpired()) {
				doCache(conn, userId);
			}
			// 推送到指定的客户端
			engine.sendTo(Constant.CHANNEL_BOOK_ONIX_CHECK,
					engine.getConnection(connId), message);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
}