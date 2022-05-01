package ptit.example.bachhoaxanhbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ptit.example.bachhoaxanhbackend.model.Voucher;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/30/2022 4:23 PM
 * Desc:
 */
@Repository
public interface VoucherRepository extends MongoRepository<Voucher, String> {
    @Query("{$and : [{'dateStart' : {$lte : ?0}}, {'dateEnd' : {$gte : ?0}}, {'voucherStatus' : ?1}]}")
    List<Voucher> findVouchersBetweenDate(long date, String status);
}

