package com.example.demo.persistence;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("sequences")
public class Sequence {

	@Id
	private String key;
	private long value=0;
	
	public Sequence(){
				
	}
	
	public Sequence(String key){
		this.key=key;		
	}

	public String getCounter() {
		return String.valueOf(value);
	}
		
	public void increment(){
		value++;
	}
}
