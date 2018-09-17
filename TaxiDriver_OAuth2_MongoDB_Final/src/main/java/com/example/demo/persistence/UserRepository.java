package com.example.demo.persistence;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.domain.AbstractUser;

public class UserRepository {

	private static Datastore datastore = DatastoreManager.getInstance().getDatastore();

	public static void saveUser(AbstractUser user) throws Exception {
		if (user == null)
			throw new Exception();
		datastore.save(user);
	}

	public static AbstractUser findUserById(String userId) throws Exception {
		AbstractUser user=datastore.find(AbstractUser.class).field("id").equal(userId).get();
		if(user==null) throw new Exception();
		return user;		
	}

	public static AbstractUser findUserByEmail(String email) throws Exception {
		//AbstractUser user=datastore.find(AbstractUser.class).field("email").equal(email).get();
		AbstractUser user=datastore.find(AbstractUser.class).field("username").equal(email).get();
		if(user==null) throw new Exception();
		return user;		
	}
	
	public UserDetails findOneByUsername(String username) throws UsernameNotFoundException {
		UserDetails user=datastore.find(AbstractUser.class).field("username").equal(username).get();
		if(user==null) throw new UsernameNotFoundException("Not found");
		return user;		
	}

	public static List<AbstractUser> findAllUsers() {
		return datastore.find(AbstractUser.class).asList();
	}
	
	public static String getNextUserId() {
		Sequence seq = datastore.find(Sequence.class).field("key").equal("users").get();
		if (seq == null) {
			seq = new Sequence("users");
		}

		String id = seq.getCounter();
		seq.increment();

		datastore.save(seq);
		return id;
	}
}
