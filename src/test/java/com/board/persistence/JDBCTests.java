package com.board.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTests {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://joonggo-rds-mysql.chagb5t1righ.ap-northeast-2.rds.amazonaws.com/joonggo_market";
	private static final String USER = "joonggo_market";
	private static final String PWD = "joonggo1234";

	@Test
	public void testConnection() {
		try {
			Class.forName(DRIVER);
			Connection con = DriverManager.getConnection(URL, USER, PWD);
			log.info(con);
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}
}
