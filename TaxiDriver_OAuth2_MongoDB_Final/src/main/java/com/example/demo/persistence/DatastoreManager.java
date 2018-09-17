package com.example.demo.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class DatastoreManager {


    private static DatastoreManager instance=new DatastoreManager();
    private Datastore datastore;
    
    private DatastoreManager() {
        try {
            MongoClient mongo = new MongoClient("localhost");    
            this.datastore=new Morphia().createDatastore(mongo, "oauth2");            
            datastore.ensureIndexes();            
        } catch (Exception ex) {
            Logger.getLogger(DatastoreManager.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public static DatastoreManager getInstance(){        
        return instance;
    }
    
    public Datastore getDatastore(){        
        return datastore;
    }



}
