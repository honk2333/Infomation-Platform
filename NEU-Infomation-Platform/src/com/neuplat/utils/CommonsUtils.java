package com.neuplat.utils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class CommonsUtils {

	//����uuid����
	public static String getUUID() throws UnsupportedEncodingException{
		String tmp= UUID.randomUUID().toString().replace("-", "");
		return tmp;
	}
	
}
