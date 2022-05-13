package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptit.example.bachhoaxanhbackend.model.User;
import ptit.example.bachhoaxanhbackend.model.Voucher;
import ptit.example.bachhoaxanhbackend.repository.UserRepository;
import ptit.example.bachhoaxanhbackend.repository.VoucherRepository;
import ptit.example.bachhoaxanhbackend.service.VoucherService;
import ptit.example.bachhoaxanhbackend.utils.RespondCode;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/30/2022 4:21 PM
 * Desc:
 */
@RestController
@RequestMapping("/vouchers/")
public class VoucherController {


    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoucherService voucherService;

    @GetMapping("all")
    public ResponseEntity<?> all() {
        return new ResponseEntity<>(this.voucherRepository.findAllByStatus(Voucher.VoucherStatus.ENABLE.name()), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody Voucher voucher) {
        this.voucherRepository.save(voucher);
        return new ResponseEntity<>(this.voucherRepository.findAllByStatus(Voucher.VoucherStatus.ENABLE.name()), HttpStatus.OK);
    }

    /**
     * This method will return was not expire
     *
     * @param date
     * @return
     */
    @GetMapping("search/{date}")
    public ResponseEntity<?> searchDTO(@PathVariable("date") long date) {
        return new ResponseEntity<>(this.voucherRepository.findVouchersBetweenDate(date, Voucher.VoucherStatus.ENABLE.name()), HttpStatus.OK);
    }

    /**
     * Is that voucher is in use allow user delete this one
     *
     * @param id
     * @return
     * @// TODO: 4/30/2022 check again this case, can not allow delete voucher when have product using this voucher.
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            Optional<Voucher> voucher = this.voucherRepository.findById(id);
            voucher.get().setStatus(Voucher.VoucherStatus.DISABLE.name());
            this.voucherRepository.save(voucher.get());
            this.voucherService.deleteVoucher(id);
            return new ResponseEntity<>(this.voucherRepository.findAllByStatus(Voucher.VoucherStatus.ENABLE.name()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is using to add voucher to each user
     *
     * @param userID  is require
     * @param voucher is require
     * @return HttpStatus
     */
    @PutMapping("add-to-voucher/{userID}")
    private ResponseEntity<?> addVoucherForUser(@PathVariable("userID") String userID, @Valid @RequestBody Voucher voucher) {
        Optional<User> tempUser = this.userRepository.findById(userID);
        Optional<Voucher> tempVoucher = this.voucherRepository.findById(voucher.getVoucherID());
        if (tempVoucher.isPresent() && tempUser.isPresent()) {
            /**
             * This block of code using to desc value of Voucher quantity base on voucherID
             */
            Voucher voucherInStorage = tempVoucher.get();
            int voucherQuantityRemain = voucherInStorage.getQuantity();
            voucherInStorage.setQuantity(voucherQuantityRemain - 1);
            this.voucherRepository.save(voucherInStorage);
            /**
             * This block of code using to add this voucher into listVoucher of each user base on userID
             */
            User user = tempUser.get();
            List<Voucher> tempList = user.getUserListVoucher();
            tempList.add(voucher);
            return new ResponseEntity<>(this.userRepository.save(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(RespondCode.NOT_EXISTS, HttpStatus.NOT_FOUND);
        }
    }


}
