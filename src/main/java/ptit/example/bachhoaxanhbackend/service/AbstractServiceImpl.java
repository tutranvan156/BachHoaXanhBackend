//package ptit.example.bachhoaxanhbackend.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ptit.example.bachhoaxanhbackend.repository.AbstractRepository;
//
//import java.util.List;
//
///**
// * Project: BachHoaXanhBackend
// * Author: Tran Van Tu
// * Date: 4/24/2022 3:40 PM
// * Desc:
// */
//@Service
//public class AbstractServiceImpl<T> implements AbstractService<T>{
//
//
//    @Override
//    public List<T> findAll() {
//        return this.abstractRepository.findAll();
//    }
//
//    @Override
//    public T findByID(String id) {
//        return (T) this.abstractRepository.findById(id);
//    }
//
//    @Override
//    public T add(T t) {
//        return this.abstractRepository.save(t);
//    }
//
//    @Override
//    public void removeByID(String id) {
//        this.abstractRepository.deleteById(id);
//    }
//
//}
