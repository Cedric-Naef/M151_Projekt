package ch.bbzw.m151.swshop.repo;

import ch.bbzw.m151.swshop.model.Producer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepo extends CrudRepository<Producer, Long>, ProducerRepoCustom {
}
