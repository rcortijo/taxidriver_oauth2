package com.example.demo.persistence;

import org.mongodb.morphia.Datastore;

import com.example.demo.api.oauth2.OAuthTokenStore;

public class OAuthRepository {
	
	private static Datastore datastore = DatastoreManager.getInstance().getDatastore();


	public static OAuthTokenStore getTokenStore() {
		OAuthTokenStore tokenStore= datastore.find(OAuthTokenStore.class).get();
		if(tokenStore==null){
			tokenStore=new OAuthTokenStore();
			storeTokenStore(tokenStore);
		}
		return tokenStore;
	}

	public static void storeTokenStore(OAuthTokenStore tokenStore){
		if(tokenStore!=null){
			datastore.save(tokenStore);
		}
	}

}
