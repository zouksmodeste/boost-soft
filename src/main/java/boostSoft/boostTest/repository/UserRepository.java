package boostSoft.boostTest.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import boostSoft.boostTest.data.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByName(String name);

	User findByUserName(String username);

}
