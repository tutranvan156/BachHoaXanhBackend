package ptit.example.bachhoaxanhbackend.mongodb;


import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import ptit.example.bachhoaxanhbackend.model.User;

import java.util.ArrayList;
import java.util.logging.Filter;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 5:09 PM
 * Desc:
 */
public class MongoUtils {
    public static MongoDatabase getDataBase() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://vantu:vantu@cluster0.ujfub.mongodb.net/BackHoaXanhAltas?retryWrites=true&w=majority");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("BachHoaXanhAltas");
        MongoCollection<User> users = database.getCollection("user", User.class);

        return database;
    }
    public static void createCollection(String collectionName) {
        MongoDatabase database = getDataBase();
        if (database.listCollectionNames().into(new ArrayList<>()).contains(collectionName)) {
            System.out.println("Database existed");
        } else {
            database.createCollection(collectionName);
        }
    }

}
