package boostSoft.boostTest.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boostSoft.boostTest.data.Product;
import boostSoft.boostTest.service.api.ProductServiceApi;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

	@Autowired ProductServiceApi productServiceApi;
	
	// end-point d'enregistrement d'un produit
	@PostMapping("/register")
	public HttpEntity<? extends Object> createProduct(@RequestBody Product product, Principal principal){
		return productServiceApi.createProduct(product, principal);
	}
	
	// end-point de mis a jour d'un produit
	@PutMapping("/update")
	public HttpEntity<? extends Object> updateProduct(@RequestBody Product product, Principal principal){
		return productServiceApi.updateProduct(product,principal);
	}
	
	// end-point de recherche d'un produit
	@GetMapping("/select")
	public HttpEntity<? extends Object> findByTitle(@RequestParam String title, Principal principal){
		return productServiceApi.findByTitle(title,principal);
	}
	
	// end-point de suppression d'un produit
	@DeleteMapping("/delete")
	public HttpEntity<? extends Object> deleteProduct(@RequestParam String title, Principal principal){
		return productServiceApi.deleteProduct(title, principal);
	}
	
	// end-point pour la liste de tous les produits
	@GetMapping("/products")
	public HttpEntity<? extends Object> findAll(Principal principal){
		return productServiceApi.findAll(principal);
	}
	
	// end-point de recherche de tous les produits crees par un utilisateur
	@GetMapping("/owner")
	public HttpEntity<? extends Object> findByOwner(@RequestParam String owner, Principal principal){
		return productServiceApi.findByOwner(owner,principal);
	}
	
	// end-point de recherche des produits par ordre croissant de creation
	@GetMapping("/productAsc")
	public HttpEntity<? extends Object> findByOrderByDateCreationAsc(){
		return productServiceApi.findByOrderByDateCreationAsc();
	}
	
	// end-point de recherche des produits par ordre decroissant de creation
	@GetMapping("/productDesc")
	public HttpEntity<? extends Object> findByOrderByDateCreationDesc(){
		return productServiceApi.findByOrderByDateCreationDesc();
	}
	
	// end-point de recherche des produits ayant le meme prix
	@GetMapping("/price")
	public HttpEntity<? extends Object> findByPrice(@RequestParam float price, Principal principal){
		return productServiceApi.findByPrice(price, principal);
	}
	
	@PostMapping("/increment")
	public HttpEntity<? extends Object> incrementQuantityProduct(@RequestParam String title,@RequestParam int quantity, Principal principal){
		return productServiceApi.incrementQuantityProduct(title, quantity, principal);
	}
}
