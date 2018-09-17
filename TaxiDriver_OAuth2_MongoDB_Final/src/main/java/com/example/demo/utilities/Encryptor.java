package com.example.demo.utilities;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class Encryptor {
	
	private static final StandardPasswordEncoder encoder = new StandardPasswordEncoder("lkdfjasld329821fs");
	
	public static String encryptPassword(String password){		
		return encoder.encode(password);
	}
	
	public static void checkIfPasswordMatches(String passwordText, String passwordEncrypt) throws Exception{
		if(!encoder.matches(passwordText, passwordEncrypt)){
			throw new Exception();
		}
	}
	
	public static StandardPasswordEncoder getPasswordEncoder(){
		return encoder;
	}
}

