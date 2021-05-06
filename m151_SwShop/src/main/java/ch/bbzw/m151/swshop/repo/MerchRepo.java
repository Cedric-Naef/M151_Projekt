package ch.bbzw.m151.swshop.repo;

import ch.bbzw.m151.swshop.model.Merch;
import ch.bbzw.m151.swshop.model.MerchType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchRepo extends CrudRepository<Merch, Long> {
    Merch findById(long id);

    Merch findByMerchName(String merchName);

    List<Merch> findByMerchType(MerchType merchType);
}
