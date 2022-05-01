package ptit.example.bachhoaxanhbackend.service;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ptit.example.bachhoaxanhbackend.model.Voucher;
import ptit.example.bachhoaxanhbackend.mongodb.MongoUtils;
import ptit.example.bachhoaxanhbackend.repository.VoucherRepository;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/30/2022 9:14 PM
 * Desc:
 */
@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

//    public List<Voucher> disableVoucher(String id) {
////        MongoCollection<Document> voucherMongoCollection = MongoUtils.getInstance().getCollection("voucher");
////
////        Query query = new Query(Criteria.where(new ObjectId().toString()).is(id));
////        Update update = new Update();
////        update.set("status", Voucher.VoucherStatus.DISABLE.name());
////        voucherMongoCollection.updateOne(query, update);
//
//
//
//
//
//    }

}
