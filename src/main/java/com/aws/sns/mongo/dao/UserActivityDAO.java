package com.aws.sns.mongo.dao;

import org.bson.Document;

import com.aws.sns.mongo.dto.UserActivity;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class UserActivityDAO {
	MongoClient mongo = new MongoClient();

	public void insertRecord(UserActivity user) {
		try {
			MongoDatabase database = mongo.getDatabase("moonfrog");
			database.getCollection("userActivity").insertOne(
					new Document()
							.append("gameId", user.getGameId())
							.append("gameName", user.getGameName())
							.append("score", user.getScore())
							.append("subEvents", user.getSubEvents())
							.append("userUUID", user.getUserUUID())
							.append("event", user.getEvent())
							.append("timestamp", user.getTimestamp()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
