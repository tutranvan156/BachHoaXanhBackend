package ptit.example.bachhoaxanhbackend.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import ptit.example.bachhoaxanhbackend.mongodb.MongoUtils;

import java.util.logging.Filter;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/24/2022 3:41 PM
 * Desc:
 */
public abstract class AbstractService<T> {

    private String collectionName;
    private MongoDatabase database;
    private MongoCollection<T> collection;

    protected AbstractService(String collectionName) {
        this.collectionName = collectionName;
        this.database = MongoUtils.getInstance();
        this.collection = (MongoCollection<T>) database.getCollection(collectionName);
    }

}
