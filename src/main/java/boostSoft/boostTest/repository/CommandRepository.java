package boostSoft.boostTest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import boostSoft.boostTest.data.Command;

public interface CommandRepository extends JpaRepository<Command, Long> {

	Optional<Command> findByCommandId(Long commandId);

	List<Command> findByStatus(String statut);

	List<Command> findByCustomerPhone(Long customerPhone);

	List<Command> findByOrderByDateCreationDesc();

	List<Command> findByOrderByDateCreationAsc();

	List<Command> findByValidator(String validator);

}
