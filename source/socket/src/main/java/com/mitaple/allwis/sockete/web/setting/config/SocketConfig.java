/**
 * maple1-socket
 * SocketConfig.java
 * create date : 2017年12月24日
 * Copyright © 2014-2016 MITAPLE Corporation.All rights reserved.
 * update date: 2017年12月24日
 */
package com.mitaple.allwis.sockete.web.setting.config;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

import com.mitaple.allwis.sockete.web.server.ChatEndpointResource;

/**
 * @version 1.0.0
 * @author liuwb
 * @since 1.0.0
 *
 */
public class SocketConfig implements ServerApplicationConfig {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.websocket.server.ServerApplicationConfig#getEndpointConfigs(java.
	 * util.Set)
	 */
	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> scanned) {
		Set<ServerEndpointConfig> result = new HashSet<>();
		if (scanned.contains(ChatEndpointResource.class)) {
			result.add(ServerEndpointConfig.Builder.create(ChatEndpointResource.class, "/chat").build());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.websocket.server.ServerApplicationConfig#
	 * getAnnotatedEndpointClasses(java.util.Set)
	 */
	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
		Set<Class<?>> results = new HashSet<>();
		for (Class<?> clazz : scanned) {
			if (clazz.getPackage().getName().startsWith("com.mitaple.allwis.sockete.web.server")) {
				results.add(clazz);
			}
		}
		return results;
	}

}
