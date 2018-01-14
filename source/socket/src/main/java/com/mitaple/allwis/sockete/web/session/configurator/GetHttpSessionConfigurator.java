/**
 * socket
 * GetHttpSessionConfigurator.java
 * create date : 2018年1月14日
 * Copyright © 2014-2016 MITAPLE Corporation.All rights reserved.
 * update date: 2018年1月14日
 */
package com.mitaple.allwis.sockete.web.session.configurator;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端。注解的值将被用于监听用户连接的终端访问URL地址。
 *  configurator：通过GetHttpSessionConfigurator可以在onOpen方法中取得HttpSession，然后通过HttpSession的ServletContext容器可以取得spring的service，实现在websocket中变相注入spring bean。
 * @author liuwb
 * @since 1.0.0
 *
 */
public class GetHttpSessionConfigurator extends Configurator {
	/* (non-Javadoc)
	 * @see javax.websocket.server.ServerEndpointConfig.Configurator#modifyHandshake(javax.websocket.server.ServerEndpointConfig, javax.websocket.server.HandshakeRequest, javax.websocket.HandshakeResponse)
	 */
	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		HttpSession httpSession = (HttpSession) request.getHttpSession();
		sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
	}
}
