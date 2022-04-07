package com.board.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtils {
	private static MessageSourceAccessor msAcc = null;

	public void setMessageSourceAccessor(MessageSourceAccessor msAcc) {
		MessageUtils.msAcc = msAcc;
	}

	public static String getMessage(String code) {
		return msAcc.getMessage(code, LocaleContextHolder.getLocale());
	}

	public static String getMessage(String code, Object[] objs) {
		return msAcc.getMessage(code, objs, LocaleContextHolder.getLocale());
	}
}
