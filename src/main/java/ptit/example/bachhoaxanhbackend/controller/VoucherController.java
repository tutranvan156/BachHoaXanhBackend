package ptit.example.bachhoaxanhbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptit.example.bachhoaxanhbackend.model.Voucher;
import ptit.example.bachhoaxanhbackend.repository.VoucherRepository;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/30/2022 4:21 PM
 * Desc:
 */
@RestController
@RequestMapping("/vouchers")
public class VoucherController {


    @Autowired
    private VoucherRepository voucherRepository;

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        return new ResponseEntity<>(this.voucherRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody Voucher voucher) {
        return new ResponseEntity<>(this.voucherRepository.save(voucher), HttpStatus.OK);

    }

    @GetMapping("/search/{date}")
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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            Optional<Voucher> voucher = this.voucherRepository.findById(id);
            voucher.get().setStatus(Voucher.VoucherStatus.DISABLE.name());
            return new ResponseEntity<>(this.voucherRepository.save(voucher.get()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
