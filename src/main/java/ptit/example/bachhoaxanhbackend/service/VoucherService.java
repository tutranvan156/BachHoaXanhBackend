package ptit.example.bachhoaxanhbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.example.bachhoaxanhbackend.repository.VoucherRepository;

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
