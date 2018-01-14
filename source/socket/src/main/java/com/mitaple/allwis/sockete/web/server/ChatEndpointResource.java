/**
 * maple1-socket
 * ChatEndpointResource.java
 * create date : 2017年12月24日
 * Copyright © 2014-2016 MITAPLE Corporation.All rights reserved.
 * update date: 2017年12月24日
 */
package com.mitaple.allwis.sockete.web.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import com.mitaple.allwis.sockete.message.ChatMessage;
import com.mitaple.allwis.sockete.web.session.configurator.GetHttpSessionConfigurator;

/**
 * @version 1.0.0
 * @author liuwb
 * @since 1.0.0
 *
 */
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatEndpointResource {
	private static ConcurrentMap<String, List<Session>> helloMap = new ConcurrentHashMap<>();
	private static ConcurrentMap<String, String> helloKeyMap = new ConcurrentHashMap<>();
	private static CopyOnWriteArraySet<Session> helloList = new CopyOnWriteArraySet<>();

	@OnOpen
	public void open(Session session) throws IOException {
		// 通过GetHttpSessionConfigurator 获取 httpsession
		HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
		String loginUserStr = (String) httpSession.getAttribute("loginUser");
		if (loginUserStr == null) {
			session.close();
		} else {
			List<Session> sessionList = helloMap.get(loginUserStr);
			if (sessionList == null) {
				sessionList = new ArrayList<>();
				helloMap.put(loginUserStr, sessionList);
			}
			sessionList.add(session);
			helloKeyMap.put(session.getId(), loginUserStr);
			helloList.add(session);
			ChatMessage welcomeMsg = ChatMessage.buildWelComeMessage(helloKeyMap.values(), "欢迎" + loginUserStr + "进入聊天室！");
			// 群聊
			List<Session> sessions = new ArrayList<>();
			sessions.addAll(helloList);
			broadcast(sessions, welcomeMsg);
		}
	}

	/**
	 * 接受客户端的消息，并把消息发送给所有连接的会话
	 * 
	 * @param message
	 *            客户端发来的消息
	 * @param session
	 *            客户端的会话
	 */
	@OnMessage
	public void message(Session session, String msg) {
		String fromUser = helloKeyMap.get(session.getId());
		if (fromUser == null) {
			HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
			String loginUserStr = (String) httpSession.getAttribute("loginUser");
			helloKeyMap.put(session.getId(), loginUserStr);
			List<Session> sessionList = helloMap.get(loginUserStr);
			if (sessionList == null) {
				sessionList = new ArrayList<>();
				helloMap.put(loginUserStr, sessionList);

			}
			helloList.add(session);
			sessionList.add(session);
			fromUser = loginUserStr;
		}
		ChatMessage chatMessage = JSONObject.toJavaObject(JSONObject.parseObject(msg), ChatMessage.class);
		chatMessage.setFromUserId(fromUser);
		List<Session> sessions = null;
		if (null == chatMessage.getToUserId() || "".equals(chatMessage.getToUserId())
				|| "*".equals(chatMessage.getToUserId())) {
			// 群聊
			sessions = new ArrayList<>();
			sessions.addAll(helloList);
			sessions.remove(session);
			broadcast(sessions, ChatMessage.build(helloKeyMap.values(), chatMessage.getToUserId(), chatMessage.getFromUserId(),
					chatMessage.getContent()));
		} else {
			sessions = helloMap.get(chatMessage.getToUserId());
			if (sessions != null) {
				broadcast(sessions, ChatMessage.build(helloKeyMap.values(), chatMessage.getToUserId(), chatMessage.getFromUserId(),
						chatMessage.getContent()));
			}
		}
	}

	/**
	 * 离开聊天室
	 * 
	 * @param session
	 */
	@OnClose
	public void close(Session session) {
		String keyHello = helloKeyMap.get(session.getId());
		if (keyHello != null) {
			List<Session> sessionList = helloMap.get(keyHello);
			if (sessionList != null) {
				sessionList.remove(session);
			}
		}
		helloKeyMap.remove(session.getId());
		helloList.remove(session);
		// 群聊
		List<Session> sessions = new ArrayList<>();
		sessions.addAll(helloList);
		broadcast(sessions, ChatMessage.buildWelComeMessage(helloKeyMap.values(), "用户" + keyHello + "离开了聊天室！"));
	}

	/**
	 * 发送广播
	 * 
	 * @param msg
	 */
	private void broadcast(List<Session> sessions, ChatMessage msg) {
		try {
			for (Session sessionIdx : sessions) {
				sessionIdx.getBasicRemote().sendText(JSONObject.toJSONString(msg));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
