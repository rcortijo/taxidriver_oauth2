package com.example.demo.api.oauth2.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.example.demo.api.oauth2.AccessToken;
import com.example.demo.api.oauth2.OAuthTokenStore;
import com.example.demo.api.oauth2.RefreshToken;
import com.example.demo.persistence.OAuthRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

public class MongoTokenStoreController implements TokenStore {

	private final OAuthTokenStore tokenStore;

	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

	public MongoTokenStoreController() {
		tokenStore = OAuthRepository.getTokenStore();
	}

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return readAuthentication(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String tokenId) {
		return tokenStore.findAccessTokenById(tokenId).getAuthentication();
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		AccessToken accessToken;
		try {
			accessToken = new AccessToken(token, authentication, authenticationKeyGenerator.extractKey(authentication));
			tokenStore.storeAccessToken(accessToken);
			OAuthRepository.storeTokenStore(tokenStore);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		AccessToken token = tokenStore.findAccessTokenById(tokenValue);
		if (token != null) {
			OAuth2AccessToken accessToken;
			try {
				accessToken = token.convertToAuth2AccessToken();
				return accessToken;
			} catch (IOException e) {
				return null;
			}

		}
		return null;

	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		tokenStore.removeAccessTokenById(token.getValue());
		OAuthRepository.storeTokenStore(tokenStore);
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		RefreshToken token;
		try {
			token = new RefreshToken(refreshToken, authentication);
			tokenStore.storeRefreshToken(token);
			OAuthRepository.storeTokenStore(tokenStore);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		RefreshToken token = tokenStore.findRefreshTokenById(tokenValue);
		if (token != null) {
			try {
				return token.convertToAuth2RefreshToken();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		RefreshToken refreshToken = tokenStore.findRefreshTokenById(token.getValue());
		if (refreshToken != null)
			return refreshToken.getAuthentication();

		return null;

	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		tokenStore.removeRefreshTokenById(token.getValue());
		OAuthRepository.storeTokenStore(tokenStore);
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		try {
			tokenStore.removeAccessTokenByRefreshToken(tokenStore.findRefreshTokenById(refreshToken.getValue()));
			OAuthRepository.storeTokenStore(tokenStore);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		AccessToken token = tokenStore
				.findAccessTokenByAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
		try {
			return token == null ? null : token.convertToAuth2AccessToken();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		List<AccessToken> tokens = tokenStore.findAccessTokenByClientId(clientId);
		return convertToAccessTokens(tokens);
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		List<AccessToken> tokens = tokenStore.findAccessTokenByClientIdAndUserName(clientId, userName);
		return convertToAccessTokens(tokens);
	}

	private Collection<OAuth2AccessToken> convertToAccessTokens(List<AccessToken> tokens) {
		List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();
		for (AccessToken token : tokens) {
			try {
				accessTokens.add(token.convertToAuth2AccessToken());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return accessTokens;
	}
}
