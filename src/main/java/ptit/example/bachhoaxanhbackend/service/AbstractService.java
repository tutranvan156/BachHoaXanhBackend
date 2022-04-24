package ptit.example.bachhoaxanhbackend.service;

import java.util.List;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/24/2022 3:41 PM
 * Desc:
 */
public interface AbstractService<T> {
    public List<T> findAll();
    public T findByID(String id);
    public T add(T t);
    public void removeByID(String id);
}
