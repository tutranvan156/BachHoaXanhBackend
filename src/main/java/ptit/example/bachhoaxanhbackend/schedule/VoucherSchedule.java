package ptit.example.bachhoaxanhbackend.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ptit.example.bachhoaxanhbackend.model.Voucher;
import ptit.example.bachhoaxanhbackend.repository.VoucherRepository;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 5/9/2022 11:15 PM
 * Desc:
 */
@Configuration
@EnableScheduling
public class VoucherSchedule {

    private static final Logger logger = LoggerFactory.getLogger(VoucherSchedule.class);
    @Autowired
    private VoucherRepository voucherRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    private void filterExpireVoucher() {
        long currentTime = System.currentTimeMillis();
        logger.info("Start filter expire voucher at : " + currentTime);
        List<Voucher> voucherList = this.voucherRepository.findAllByStatus(Voucher.VoucherStatus.ENABLE.name());
        for (Voucher item : voucherList) {
            if (item.getDateEnd() < currentTime) {
                item.setStatus(Voucher.VoucherStatus.DISABLE.name());
                logger.info(Voucher.VoucherStatus.DISABLE.name() + ": " + item.getVoucherID() + " due to expire value : " + item.getDateEnd());
                this.voucherRepository.save(item);
            }
        }
        logger.info("Finish filter expire voucher");
    }
}
