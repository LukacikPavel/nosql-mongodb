package sk.upjs.nosql_mongodb;

import java.util.Collections;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;

@Configuration
@EnableMongoRepositories
@ComponentScan(basePackages = "sk.upjs.nosql_mongodb")
public class MongoConfig extends AbstractMongoClientConfiguration {

	static String HOST = "";
	static String USERNAME = "";
	static String PASS = "";
	static String DATABASE = "lukacik";

	@Override
	protected String getDatabaseName() {
		return DATABASE;
	}

//	@Override
//	protected void configureClientSettings(Builder builder) {
//		builder.credential(MongoCredential.createCredential(USERNAME, DATABASE, PASS.toCharArray()))
//				.applyToClusterSettings(settings -> {
//					settings.hosts(Collections.singletonList(new ServerAddress(HOST)));
//				});
//	}

//	@Override
//	public MongoClient mongoClient() {
//		ConnectionString connectionString = new ConnectionString("mongodb://" + HOST);
//		MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
//				.applyConnectionString(connectionString)
//				.credential(MongoCredential.createCredential(USERNAME, DATABASE, PASS.toCharArray()))
//				.build();
//		return MongoClients.create(mongoClientSettings);
//	}
	
//	@Override
//	public MongoDatabaseFactory mongoDbFactory() {
//		return new SimpleMongoClientDatabaseFactory(mongoClient(), DATABASE);
//	}
}
