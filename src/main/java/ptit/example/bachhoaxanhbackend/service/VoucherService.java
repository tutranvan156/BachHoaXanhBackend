package ptit.example.bachhoaxanhbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.example.bachhoaxanhbackend.model.User;
import ptit.example.bachhoaxanhbackend.model.Voucher;
import ptit.example.bachhoaxanhbackend.repository.UserRepository;
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
    private UserRepository userRepository;

    /**
     * This method remove all voucher in voucherList of specific user;
     * @param voucherID
     */
    public void  deleteVoucher(String voucherID) {
        List<User> userList = this.userRepository.findAllByStatus(User.UserStatus.ENABLE.name());
        for (User itemUser : userList) {
            List<Voucher> voucherList = itemUser.getUserListVoucher();
            for (int i = 0; i < voucherList.size(); i++) {
                if (voucherList.get(i).getVoucherID().equals(voucherID)) {
                    voucherList.remove(i);
                }
            }
            itemUser.setUserListVoucher(voucherList);
            this.userRepository.save(itemUser);
        }
    }
}
