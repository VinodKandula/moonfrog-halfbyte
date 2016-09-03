package com.aws.sns.mongo.dao;

import org.bson.Document;

import com.aws.sns.mongo.dto.SNSUsers;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class SNSUserDAO {
	
	private MongoClient mongo = new MongoClient();

	
	public void insertRecord(SNSUsers user) {
		try {

			MongoDatabase database = mongo.getDatabase("moonfrog");
			database.getCollection("snsUser").insertOne(
					new Document()
							.append("snsRegId", user.getSnsRegId())
							.append("deviceUUID", user.getDeviceUUID())
							.append("snsPlatform", user.getSnsPlatform())
							.append("status",user.getStatus())
							.append("created", user.getCreated())
							.append("updated", user.getUpdated()));

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
