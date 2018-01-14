/**
 * socket
 * ChatMessage.java
 * create date : 2018年1月7日
 * Copyright © 2014-2016 MITAPLE Corporation.All rights reserved.
 * update date: 2018年1月7日
 */
package com.mitaple.allwis.sockete.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @version 1.0.0
 * @author liuwb
 * @since 1.0.0
 *
 */
public class ChatMessage implements Serializable{
	private static final long serialVersionUID = 3546274737037986868L;

	public enum MsgType {
		Text, Img, Video
	}

	private List<String> users;

	private String toUserId;

	private String fromUserId;

	private MsgType type;

	private String content;
	
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	private Date msgDate;

	private String mediaAddress;
	
	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getType() {
		return type == null ? null : type.name();
	}

	public MsgType type() {
		return type;
	}

	public void setType(String type) {
		this.type = MsgType.valueOf(MsgType.class, type);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}

	public String getMediaAddress() {
		return mediaAddress;
	}

	public void setMediaAddress(String mediaAddress) {
		this.mediaAddress = mediaAddress;
	}
	
	public static ChatMessage buildWelComeMessage(Collection<String> sessions, String welcome) {
		ChatMessage message = new ChatMessage();
		message.setType(MsgType.Text.name());
		message.setMsgDate(new Date());
		message.setContent(welcome);
		List<String> sessionsString = new ArrayList<>();
		sessionsString.addAll(sessions);
		message.setUsers(sessionsString);
		return message;
	}
	
	public static ChatMessage build(Collection<String> sessions, String toUserId, String fromUser, String msg) {
		ChatMessage message = new ChatMessage();
		message.setType(MsgType.Text.name());
		message.setMsgDate(new Date());
		message.setFromUserId(fromUser);
		message.setToUserId(toUserId);
		message.setContent(msg);
		List<String> sessionsString = new ArrayList<>();
		sessionsString.addAll(sessions);
		message.setUsers(sessionsString);
		return message;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}
}
