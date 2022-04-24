package ptit.example.bachhoaxanhbackend.mongodb;

import org.bson.Document;

import java.lang.reflect.Field;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/23/2022 10:19 PM
 * Desc:
 */
public class CollectionControl<T> {

    public static <T> Document convertToJSON(T t) throws IllegalAccessException {
        Field[] fields = t.getClass().getDeclaredFields();
        Document tempDocument = new Document();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            tempDocument.append(fields[i].getName(), fields[i].get(t));
        }
        System.out.println(tempDocument);
        return tempDocument;
    }
}
