package ch.bbzw.m151.swshop.repo;

import ch.bbzw.m151.swshop.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User checkPassword(String username, String user_password);
}
