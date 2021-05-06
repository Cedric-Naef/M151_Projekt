package ch.bbzw.m151.swshop.repo;

import ch.bbzw.m151.swshop.model.Producer;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepoCustom {
    Producer customQuery();
}
