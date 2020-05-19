package boostSoft.boostTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boostSoft.boostTest.data.ProductCommand;

public interface ProductCommandrepository extends JpaRepository<ProductCommand, Long>{

}
