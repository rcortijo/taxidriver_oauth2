package com.example.demo.api.oauth2;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.example.demo.persistence.CalendarConverter;

@Entity("tokenStore")
@Converters(CalendarConverter.class)
public class OAuthTokenStore {

	
	@Id
	public String id="tokenStore";
	@Embedded
	public List<AccessToken> accessTokens = new ArrayList<>();
	@Embedded
	public List<RefreshToken> refreshTokens = new ArrayList<>();

	public OAuthTokenStore() {

	}

	/**
	 * Returns null if not found
	 * @param tokenId
	 * @return
	 */
	public AccessToken findAccessTokenById(String tokenId) {
		for (AccessToken token : accessTokens) {
			if (token.getTokenId().equals(tokenId)) {
				return token;
			}
		}
		return null;
	}

	public AccessToken findAccessTokenByAuthenticationId(String extractKey) {
		for (AccessToken token : accessTokens) {
			if (token.getAuthenticationId().equals(extractKey)) {
				return token;
			}
		}
		return null;
	}

	public List<AccessToken> findAccessTokenByClientIdAndUserName(String clientId, String username) {
		List<AccessToken> result = new ArrayList<>();
		result.addAll(findAccessTokenByClientId(clientId));
		result.addAll(findAccessTokenByUsername(username));
		return result;
	}

	public List<AccessToken> findAccessTokenByClientId(String clientId) {
		List<AccessToken> result = new ArrayList<>();

		for (AccessToken token : accessTokens) {
			if (token.getClientId().equals(clientId)) {
				result.add(token);
			}
		}
		return result;
	}

	public List<AccessToken> findAccessTokenByUsername(String username) {
		List<AccessToken> result = new ArrayList<>();

		for (AccessToken token : accessTokens) {
			if (token.getUserName().equals(username)) {
				result.add(token);
			}
		}
		return result;
	}

	public void removeAccessTokenById(String tokenId) {
		for (AccessToken token : new ArrayList<>(accessTokens)) {
			if (token.getTokenId().equals(tokenId)) {
				accessTokens.remove(token);
			}
		}

	}

	public void removeAccessTokenByRefreshToken(RefreshToken refreshToken) {
		for (AccessToken token : new ArrayList<>(accessTokens)) {
			if (token.getRefreshToken().equals(refreshToken.getTokenId())) {
				accessTokens.remove(token);
			}
		}
	}

	public void storeAccessToken(AccessToken accessToken) {
		removeAccessTokenById(accessToken.getTokenId());
		this.accessTokens.add(accessToken);
	}

	public void storeRefreshToken(RefreshToken token) {
		removeRefreshTokenById(token.getTokenId());
		this.refreshTokens.add(token);
	}

	public RefreshToken findRefreshTokenById(String tokenId) {
		for (RefreshToken token : refreshTokens) {
			if (token.getTokenId().equals(tokenId)) {
				return token;
			}
		}
		return null;
	}

	public void removeRefreshTokenById(String tokenId) {
		for (RefreshToken token : new ArrayList<>(refreshTokens)) {
			if (token.getTokenId().equals(tokenId)) {
				refreshTokens.remove(token);
			}
		}
	}

}
