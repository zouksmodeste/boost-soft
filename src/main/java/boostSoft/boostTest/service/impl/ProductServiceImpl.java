package boostSoft.boostTest.service.impl;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import boostSoft.boostTest.data.Product;
import boostSoft.boostTest.data.ProductStatus;
import boostSoft.boostTest.data.Stock;
import boostSoft.boostTest.data.User;
import boostSoft.boostTest.data.UserStatus;
import boostSoft.boostTest.repository.ProductRepository;
import boostSoft.boostTest.repository.StockRepository;
import boostSoft.boostTest.repository.UserRepository;
import boostSoft.boostTest.service.api.ProductServiceApi;

@Service
public class ProductServiceImpl implements ProductServiceApi {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	StockRepository stockRepository;

	@Override
	public HttpEntity<? extends Object> createProduct(Product product, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				if (product.getTitle() == null || product.getPrice() == 0) { 
					return new ResponseEntity<String>("Please enter informations in blank places", HttpStatus.OK);
				} else {
					Product currentProduct = productRepository.save(product); 
					Stock currentStock = new Stock();
					Date date = new Date();
					product.setOwner(principal.getName());
					product.setDateCreation(date);
					product.setStatus(ProductStatus.DISPONIBLE.getStatut()); 
					product.setStock(currentStock);
					productRepository.saveAndFlush(currentProduct); 

					currentStock.setQuantity(0);
					currentStock.setDateCreation(date);
					currentStock.setProduct(currentProduct);
					stockRepository.saveAndFlush(currentStock);

					return new ResponseEntity<Product>(currentProduct, HttpStatus.OK); 
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> updateProduct(Product product, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			}else {
				Date date = new Date();
				Product currentProduct = productRepository.findByTitle(product.getTitle());
				if (product.getTitle() != null || product.getPrice() != 0) { 
															
					currentProduct.setTitle(product.getTitle());
					currentProduct.setDateCreation(date);
					currentProduct.setPrice(product.getPrice()); 
					currentProduct.setStatus(product.getStatus());

					productRepository.saveAndFlush(currentProduct);
					return new ResponseEntity<Product>(currentProduct, HttpStatus.OK); 
				} else {
					return new ResponseEntity<Product>(currentProduct, HttpStatus.OK); 
				}
			}
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> findByTitle(String title, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			}else {
				Product currentProduct = productRepository.findByTitle(title); 
				return new ResponseEntity<Product>(currentProduct, HttpStatus.OK); 
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> deleteProduct(String title,Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			}else {
				Product currentProduct = productRepository.findByTitle(title); 
				currentProduct.setStatus(ProductStatus.DELETED.getStatut()); 
				productRepository.saveAndFlush(currentProduct); 
				return new ResponseEntity<Product>(currentProduct, HttpStatus.OK); 
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> findAll(Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<List<Product>>(productRepository.findAll(), HttpStatus.OK);
			} 
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> findByOwner(String owner, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<List<Product>>(productRepository.findByOwner(owner), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> findByOrderByDateCreationDesc() {
		try {
			return new ResponseEntity<List<Product>>(productRepository.findByOrderByDateCreationDesc(), HttpStatus.OK); 
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> findByOrderByDateCreationAsc() {
		try {
			return new ResponseEntity<List<Product>>(productRepository.findByOrderByDateCreationAsc(), HttpStatus.OK); 
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> findByPrice(float price, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<List<Product>>(productRepository.findByPrice(price), HttpStatus.OK);
			} 
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> incrementQuantityProduct(String title, int quantity, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			}else {
				Product currentProduct = productRepository.findByTitle(title);
				Stock currentStock = stockRepository.findById(currentProduct.getStock().getStockId()).get();

				currentProduct.getStock().setQuantity(quantity);
				currentProduct.setStock(currentStock);
				productRepository.saveAndFlush(currentProduct);
				stockRepository.saveAndFlush(currentStock);

				return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

}
