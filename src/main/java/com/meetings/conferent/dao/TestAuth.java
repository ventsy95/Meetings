package com.meetings.conferent.dao;

import org.apache.commons.codec.binary.Base64;

public class TestAuth {

	public static void main(String[] args) {
		String plainCreds = "joro2:password";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		System.out.println(base64Creds);
	}

}
