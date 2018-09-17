package com.example.demo.api.oauth2;

import java.io.IOException;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Embedded
public class AccessToken {

	private String tokenId;
	@Embedded
	private String oAuth2AccessToken; 
	private String authenticationId;
	private String userName;
	private String clientId;
	@Embedded
	private String authentication; 
	private String refreshToken;
	
	@NotSaved
	private static ObjectMapper mapper = new ObjectMapper();
	private static Gson gson=new GsonBuilder().registerTypeAdapter(OAuth2Authentication.class, new AuthenticationDeserializer()).create();		

	public AccessToken() {
	}

	public AccessToken(final OAuth2AccessToken oAuth2AccessToken, final OAuth2Authentication authentication,
			final String authenticationId) throws JsonProcessingException {
		this.tokenId = oAuth2AccessToken.getValue();
		this.authenticationId = authenticationId;
		this.userName = authentication.getName();
		this.clientId = authentication.getOAuth2Request().getClientId();
		this.refreshToken = oAuth2AccessToken.getRefreshToken().getValue();

		this.oAuth2AccessToken = mapper.writeValueAsString(oAuth2AccessToken);
		this.authentication = gson.toJson(authentication);
	}

	public String getTokenId() {
		return tokenId;
	}

	public OAuth2AccessToken convertToAuth2AccessToken() throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(oAuth2AccessToken, OAuth2AccessToken.class);
	}

	public String getAuthenticationId() {
		return authenticationId;
	}

	public String getUserName() {
		return userName;
	}

	public String getClientId() {
		return clientId;
	}

	
	public OAuth2Authentication getAuthentication(){
		return gson.fromJson(authentication, OAuth2Authentication.class);
	}

	public String getRefreshToken() {
		return refreshToken;
	}
}
