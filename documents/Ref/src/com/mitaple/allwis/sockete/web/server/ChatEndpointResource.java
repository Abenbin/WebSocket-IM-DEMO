/**
 * maple1-socket
 * ChatEndpointResource.java
 * create date : 2017年12月24日
 * Copyright © 2014-2016 MITAPLE Corporation.All rights reserved.
 * update date: 2017年12月24日
 */
package com.mitaple.allwis.sockete.web.server;

import java.io.IOException;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @version 1.0.0
 * @author liuwb
 * @since 1.0.0
 *
 */
@ServerEndpoint("/chat")
public class ChatEndpointResource {
	
	@OnOpen
	public void open(Session session) {
		System.out.println("Chat On Open ----------------");
		System.out.println(session.getQueryString());
		String msg = "Welcome!";
		broadcast(session, msg);
	}

	@OnMessage
	public void message(Session session, String msg) {
		String sendMessage = "";
	}
	
	/**
	 * 离开聊天室
	 * @param session
	 */
	@OnClose
	public void close(Session session) {
		System.out.println("Chat On Close ----------------");
	}

	/**
	 * 发送广播
	 * 
	 * @param msg
	 */
	private static void broadcast(Session session, String msg) {
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
