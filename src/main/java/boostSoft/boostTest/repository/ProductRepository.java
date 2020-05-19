package boostSoft.boostTest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import boostSoft.boostTest.data.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByTitle(String title);

	List<Product> findByOwner(String owner);

	List<Product> findByOrderByDateCreationDesc();

	List<Product> findByOrderByDateCreationAsc();

	List<Product> findByPrice(float price);

}
