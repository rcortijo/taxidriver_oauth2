package com.example.demo.api.oauth2;

import java.io.IOException;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class RefreshToken  {

    private String tokenId;
    @Embedded
    private String oAuth2RefreshToken;
    @Embedded
    private String authentication;

   
    @NotSaved
	private static Gson gson=new GsonBuilder().registerTypeAdapter(OAuth2Authentication.class, new AuthenticationDeserializer()).create();	
    
    public RefreshToken(){
    	
    }
    public RefreshToken(OAuth2RefreshToken oAuth2RefreshToken, OAuth2Authentication authentication) throws JsonProcessingException {        
        this.tokenId = oAuth2RefreshToken.getValue();
        
        this.oAuth2RefreshToken =  gson.toJson(oAuth2RefreshToken);
        this.authentication =  gson.toJson(authentication);
    }

    public String getTokenId() {
        return tokenId;
    }

    public OAuth2RefreshToken convertToAuth2RefreshToken() throws JsonParseException, JsonMappingException, IOException {
        return gson.fromJson(oAuth2RefreshToken,DefaultOAuth2RefreshToken.class);
    }

    public OAuth2Authentication getAuthentication() {		
		return gson.fromJson(authentication, OAuth2Authentication.class);
    }
 
}
