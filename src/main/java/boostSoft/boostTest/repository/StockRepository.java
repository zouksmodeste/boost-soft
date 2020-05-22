package boostSoft.boostTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boostSoft.boostTest.data.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

}
