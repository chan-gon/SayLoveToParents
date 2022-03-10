package com.board.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.log4j.Log4j;

/**
 * socket 통신은 서버와 클라이언트가 1:N의 관계를 맺는다. 따라서 여러 클라이언트로부터 서버에 발송된 메시지를 처리하는
 * Handler가 필요하다.
 *
 */
@Log4j
@Component
public class WebSockChatHandler extends TextWebSocketHandler implements WebSocketConfigurer {

	// 1:N(서버:클라이언트) 채팅 구현을 위해 여러 사용자 session을 받아야 하므로 List로 구현
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("#ChattingHandler, afterConnectionEstablished");
		sessionList.add(session);
		log.info(session.getPrincipal().getName() + "님이 입장하셨습니다.");
	}

	/**
	 *	웹소켓 서버로 메시지를 전송했을 때 해당 메소드가 실행된다.
	 *	횬재 웹소켓 서버에 접속한 모든 session에게 메시지를 전달하기 위해 loop를 통해 메시지를 전송한다.
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info("#ChattingHandler, handleMessage");
		log.info(session.getId() + ": " + message);
		for (WebSocketSession s : sessionList) {
			s.sendMessage(new TextMessage(session.getPrincipal().getName() + " : " + message.getPayload()));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("#ChattingHandler, afterConnectionClosed");
		sessionList.remove(session);
		log.info(session.getPrincipal().getName() + "님이 퇴장하셨습니다.");
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(this, "chatting").setAllowedOrigins("*");
	}

}
