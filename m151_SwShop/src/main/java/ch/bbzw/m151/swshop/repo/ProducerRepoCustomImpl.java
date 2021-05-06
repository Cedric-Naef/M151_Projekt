package ch.bbzw.m151.swshop.repo;

import ch.bbzw.m151.swshop.model.Merch;
import ch.bbzw.m151.swshop.model.MerchType;
import ch.bbzw.m151.swshop.model.Price;
import ch.bbzw.m151.swshop.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Set;

@Repository
public class ProducerRepoCustomImpl implements ProducerRepoCustom {

    private final EntityManager entityManager;

    @Autowired
    public ProducerRepoCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Producer customQuery() {
        final Price price = new Price(1.0f, 0);
        final Merch merch = new Merch("custom", MerchType.WAFFE, price);
        final Producer producer = new Producer("custom", Set.of(merch));

        // For save
        entityManager.persist(producer);
        // For update
        //entityManager.merge(producer);

        return producer;
    }
}
