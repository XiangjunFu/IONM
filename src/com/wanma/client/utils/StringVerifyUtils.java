package com.wanma.client.utils;

public class StringVerifyUtils {
	
	/**
	 * 
	 * @param text
	 * @return true 不为空
	 */
	public static boolean verifyEmpty(String text) {
		return !(text == null || "".equals(text));
	}
	
	public static void main(String[] args) {
		byte[] bb = {(byte)0xef,(byte)0xef,(byte)0xef};
		System.out.println(new String(bb));
	}
}
