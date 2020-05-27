package boostSoft.boostTest.service.api;

import java.security.Principal;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import boostSoft.boostTest.data.Product;

@Service
public interface ProductServiceApi {

	public abstract HttpEntity<? extends Object> createProduct(Product product, Principal principal);
	public abstract HttpEntity<? extends Object> updateProduct(Product product, Principal principal);
	public abstract HttpEntity<? extends Object> findByTitle(String title, Principal principal);
	public abstract HttpEntity<? extends Object> deleteProduct(String title,Principal principal);
	public abstract HttpEntity<? extends Object> findAll(Principal principal);
	public abstract HttpEntity<? extends Object> findByOwner(String owner, Principal principal);
	public abstract HttpEntity<? extends Object> findByOrderByDateCreationDesc();
	public abstract HttpEntity<? extends Object> findByOrderByDateCreationAsc();
	public abstract HttpEntity<? extends Object> findByPrice(float price,Principal principal);
	public abstract HttpEntity<? extends Object> incrementQuantityProduct(String title, int quantity, Principal principal);
}
