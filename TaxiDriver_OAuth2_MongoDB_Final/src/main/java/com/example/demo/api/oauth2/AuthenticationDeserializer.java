package com.example.demo.api.oauth2;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import com.example.demo.domain.AbstractUser;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class AuthenticationDeserializer implements JsonDeserializer<OAuth2Authentication> {

	@Override
	public OAuth2Authentication deserialize(JsonElement json, Type type, JsonDeserializationContext arg2)
			throws JsonParseException {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json.toString());

			JSONObject storedRequest = jsonObject.getJSONObject("storedRequest");
			
			JSONObject requestParams=storedRequest.getJSONObject("requestParameters");
			Type typeMap = new TypeToken<Map<String, String>>(){}.getType();
			Map<String, String> params = new Gson().fromJson(requestParams.toString(), typeMap);
			
			List scopeList=Arrays.asList(new Gson().fromJson(storedRequest.getJSONArray("scope").toString(),String[].class));

		
			
			OAuth2Request oAuth2Request = new OAuth2Request(params,
					storedRequest.getString("clientId"), null, true, new HashSet(scopeList),
					null, null, null, null);
			
						
			JSONObject userAuthorization = jsonObject.getJSONObject("userAuthentication");
				

			AbstractUser principal = getPrincipalObject(userAuthorization.getJSONObject("principal"));

			List<Map<String, String>> authorieties=Arrays.asList(new Gson().fromJson(userAuthorization.getJSONArray("authorities").toString(),Map[].class));

			
			Authentication userAuthentication = new UsernamePasswordAuthenticationToken(principal,
					null,
					getAuthorities(authorieties));
			OAuth2Authentication authentication = new OAuth2Authentication(oAuth2Request, userAuthentication);
			return authentication;
		} catch (Exception e) {
			e.printStackTrace();
			throw new JsonParseException("");
		}
	}

	private AbstractUser getPrincipalObject(JSONObject principalJson) throws JSONException {
		AbstractUser user = new AbstractUser(principalJson);
		return user;		
	}

	private Collection<GrantedAuthority> getAuthorities(List<Map<String, String>> authorities) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>(authorities.size());
		for (Map<String, String> authority : authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.get("role")));
		}
		return grantedAuthorities;
	}
}
