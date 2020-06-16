package boostSoft.boostTest.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import boostSoft.boostTest.data.Product;
import boostSoft.boostTest.data.ProductStatus;
import boostSoft.boostTest.data.User;
import boostSoft.boostTest.data.UserStatus;
import boostSoft.boostTest.repository.ProductRepository;
import boostSoft.boostTest.repository.UserRepository;
import boostSoft.boostTest.service.api.ProductServiceApi;

@Service
public class ProductServiceImpl implements ProductServiceApi {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	public HttpEntity<? extends Object> createProduct(Product product, Principal principal) {
		try {
			boolean isExist = false;
			List<Product> currentProducts = productRepository.findAll();
			for (Product product2 : currentProducts) {
				if (product2.getTitle().equalsIgnoreCase(product.getTitle())) {
					isExist = true;
					break;
				}
			}

			if (isExist) {
				return new ResponseEntity<String>("couldn't create another product with same title",
						HttpStatus.ACCEPTED);
			} else {
				User currentUser = userRepository.findByUserName(principal.getName());
				if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
						|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
					return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
				} else {
					if (product.getTitle() == null) {
						return new ResponseEntity<String>("Please enter informations in blank places (title)",
								HttpStatus.OK);
					} else {
						Product currentProduct = productRepository.save(product);
						Date date = new Date();
						product.setOwner(principal.getName());
						product.setDateCreation(date);
						product.setStatus(ProductStatus.DISPONIBLE.getStatut());
						product.setQuantity(product.getQuantity());
						productRepository.saveAndFlush(currentProduct);

						return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
					}
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
			} else {
				Date date = new Date();
				Product currentProduct = productRepository.findById(product.getProductId()).get();
				if (!product.getTitle().isEmpty()) currentProduct.setTitle(product.getTitle()); 
				if (!product.getOwner().isEmpty()) currentProduct.setOwner(product.getOwner());
				if (!product.getStatus().isEmpty()) currentProduct.setStatus(product.getStatus());
				if (product.getPrice()!=0) currentProduct.setPrice(product.getPrice());
				if (!(product.getQuantity()+"").isEmpty()) currentProduct.setQuantity(product.getQuantity()+currentProduct.getQuantity());
				currentProduct.setDateCreation(date);
				
				productRepository.saveAndFlush(currentProduct);
				return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
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
			} else {
				Product currentProduct = productRepository.findByTitle(title);
				return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public HttpEntity<? extends Object> deleteProduct(String title, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
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
			Boolean IsDelete = false;
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				List<Product> listProducts = new ArrayList<>();
				List<Product> products = productRepository.findAll();
				for (Product product : products) {
					if (product.getStatus().equalsIgnoreCase(ProductStatus.DELETED.getStatut())) {
						IsDelete = true;
					} else {
						IsDelete = false;
						listProducts.add(product);
					}
				}
				return new ResponseEntity<List<Product>>(listProducts, HttpStatus.OK);
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
			} else {
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
			} else {
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
			} else {
				Product currentProduct = productRepository.findByTitle(title);
				currentProduct.setQuantity(quantity + currentProduct.getQuantity());
				productRepository.saveAndFlush(currentProduct);

				return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
