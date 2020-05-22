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
			
			System.err.println("attaque");
			if (product.getTitle() == null || product.getPrice() == 0) { // verificie si le produit qu'on veut
																			// enregistrer est vide
				return new ResponseEntity<String>("Please enter informations in blank places", HttpStatus.BAD_REQUEST); // renvoie
																														// un
																														// message
																														// d'erreur
																														// avec
																														// une
																														// reponse
																														// http
			} else {
				Product currentProduct = productRepository.save(product); // enregistremenr d'un produit
				Stock currentStock = new Stock();
				Date date = new Date();
				product.setOwner(principal.getName());
				product.setDateCreation(date);
				product.setStatus(ProductStatus.DISPONIBLE.getStatut()); // mise a jour du status d'un produit
				product.setStock(currentStock);
				productRepository.saveAndFlush(currentProduct); // mis a jour du produit dans la base de donnees

				currentStock.setQuantity(0);
				currentStock.setDateCreation(date);
				currentStock.setProduct(currentProduct);
				stockRepository.saveAndFlush(currentStock);

				return new ResponseEntity<Product>(currentProduct, HttpStatus.CREATED); // renvoie du produit avec une
																						// reponse http
			}

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> updateProduct(Product product) {
		try {
			Date date = new Date();
			Product currentProduct = productRepository.findByTitle(product.getTitle());
			if (product.getTitle() != null || product.getDateCreation() != null || product.getPrice() != 0
					|| product.getStatus() != null) { // verification des attributs en bases de donnees et ceux fournis
														// par l'utilisateur dans le formulaire
				currentProduct.setTitle(product.getTitle());
				currentProduct.setDateCreation(date);
				currentProduct.setPrice(product.getPrice()); // mise a jour des differents attributs du produit
				currentProduct.setStatus(product.getStatus());

				productRepository.saveAndFlush(currentProduct);
				return new ResponseEntity<Product>(currentProduct, HttpStatus.OK); // renvoie du produit avec une
																					// reponse http
			} else {
				return new ResponseEntity<Product>(currentProduct, HttpStatus.OK); // renvoie du produit avec une
																					// reponse http
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> findByTitle(String title) {
		try {
			Product currentProduct = productRepository.findByTitle(title); // recherche le produit
			return new ResponseEntity<Product>(currentProduct, HttpStatus.OK); // renvoie du produit avec une reponse
																				// http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> deleteProduct(String title) {
		try {
			Product currentProduct = productRepository.findByTitle(title); // recherche du produit
			currentProduct.setStatus(ProductStatus.DELETED.getStatut()); // mise a jour du status du produit
			productRepository.saveAndFlush(currentProduct); // mise a jour du produit en base de donnees
			return new ResponseEntity<Product>(currentProduct, HttpStatus.OK); // renvoie du produit avec une reponse
																				// http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> findAll() {
		try {
			return new ResponseEntity<List<Product>>(productRepository.findAll(), HttpStatus.FOUND); // renvoie la liste
																										// de tous les
																										// produits et
																										// une reponse
																										// http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> findByOwner(String owner) {
		try {
			return new ResponseEntity<List<Product>>(productRepository.findByOwner(owner), HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> findByOrderByDateCreationDesc() {
		try {
			return new ResponseEntity<List<Product>>(productRepository.findByOrderByDateCreationDesc(),
					HttpStatus.FOUND); // renvoie la liste de tous les produits par ordre decroissant de creation et
										// une reponse http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> findByOrderByDateCreationAsc() {
		try {
			return new ResponseEntity<List<Product>>(productRepository.findByOrderByDateCreationAsc(),
					HttpStatus.FOUND); // renvoie la liste de tous les produits par ordre croissant de creation et une
										// reponse http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> findByPrice(float price) {
		try {
			return new ResponseEntity<List<Product>>(productRepository.findByPrice(price), HttpStatus.FOUND); // renvoie
																												// une
																												// liste
																												// de
																												// produits
																												// ayant
																												// le
																												// meme
																												// prix
																												// avec
																												// une
																												// reponse
																												// hhtp
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> incrementQuantityProduct(String title, int quantity) {
		try {
			Product currentProduct = productRepository.findByTitle(title);
			Stock currentStock = stockRepository.findById(currentProduct.getStock().getStockId()).get();

			currentProduct.getStock().setQuantity(quantity);
			currentProduct.setStock(currentStock);
			productRepository.saveAndFlush(currentProduct);
			stockRepository.saveAndFlush(currentStock);

			return new ResponseEntity<Product>(currentProduct, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

}
