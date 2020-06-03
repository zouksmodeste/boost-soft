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

import boostSoft.boostTest.data.Command;
import boostSoft.boostTest.data.CommandStatus;
import boostSoft.boostTest.data.CommandType;
import boostSoft.boostTest.data.Product;
import boostSoft.boostTest.data.ProductCommand;
import boostSoft.boostTest.data.ProductStatus;
import boostSoft.boostTest.data.User;
import boostSoft.boostTest.data.UserStatus;
import boostSoft.boostTest.repository.CommandRepository;
import boostSoft.boostTest.repository.ProductCommandrepository;
import boostSoft.boostTest.repository.ProductRepository;
import boostSoft.boostTest.repository.UserRepository;
import boostSoft.boostTest.service.api.CommandServiceApi;

@Service
public class CommandServiceImpl implements CommandServiceApi {

	@Autowired CommandRepository commandRepository;
	@Autowired ProductRepository productRepository;
	@Autowired ProductCommandrepository productCommandrepository;
	@Autowired UserRepository userRepository;

	@Override
	public HttpEntity<? extends Object> createCommand(Command command, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			}else {
				if (command.getProductCommand()==null) {
					return new ResponseEntity<String>("Please enter informations in blank places", HttpStatus.BAD_REQUEST); // renvoie un message d'erreur avec une reponse http
				}else {
					float totalprice=0;
					float price = 0;
					List<ProductCommand> realState = new ArrayList<ProductCommand>();		// nouvelle liste
					List<ProductCommand> orders = command.getProductCommand();				// liste des produits contenus dans la commande
					
					for (ProductCommand productCommand : orders) {	
						
						Product currentProduct = productRepository.findById(productCommand.getProduct().getProductId()).get(); // recuperation du produit en base de donnees
						
						if (productCommand.getQuantityOrdered() <= currentProduct.getStock().getQuantity()) { // comparaison entre la quantite de produits commande et celle en stock
							productCommand.setAvailableQuantity(productCommand.getQuantityOrdered());   // mise a jour de la quantite valide de produit commande par la quantite commande
							realState.add(productCommand);												// ajout du produit a la nouvelle liste
							price = productCommand.getAvailableQuantity()*productCommand.getProduct().getPrice(); // calcule du prix d'un produit en fonction de sa quantite commandee
							
						} else {
							productCommand.setAvailableQuantity(
									productRepository.findByTitle(productCommand.getProduct().getTitle()).getStock().getQuantity());  // mise a jour de la quantite valide de produit commande par la quantite en stock
							realState.add(productCommand);												// ajout du produit a la nouvelle liste	
							price = productCommand.getAvailableQuantity()*productCommand.getProduct().getPrice(); // calcule du prix d'un produit en fonction de sa quantite commandee
							
							currentProduct.setStatus(ProductStatus.INSUFFISANT.getStatut());
							productRepository.saveAndFlush(currentProduct);
							
							System.err.println("one or more products of this order are not available in stock");
							
						}
						
						currentProduct.getStock().setQuantity(currentProduct.getStock().getQuantity() - productCommand.getAvailableQuantity()); // reduit la quantite de produit commande en base de donnees
						productRepository.saveAndFlush(currentProduct);
						
						totalprice = totalprice + price; // calcule du prix total de la commande
						
					}
					
					command.setTotalPrice(totalprice);
					command.setCommandType(CommandType.VENTE);
					command.setDateCreation(new Date());
					command.setValidator(principal.getName());
					command.setStatus(CommandStatus.TERMINE.getStatut());		// enregistrement de la commande
					command.setProductCommand(new ArrayList<ProductCommand>());
					command = commandRepository.save(command);
					
					for (ProductCommand commandproduct : realState) {				// referencement de la commande dans le pojo productCommand
						commandproduct.setCommand(command);
						command.getProductCommand().add(productCommandrepository.save(commandproduct));
						commandRepository.saveAndFlush(command);
					}
					return new ResponseEntity<Command>(commandRepository.findById(command.getCommandId()).get(), HttpStatus.CREATED); // renvoie de la command enregistrer avec une reponse http
				}
			}
			
			
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // renvoie d'un message d'erreur avec une reponse http
		}
		
		
	}
	
	@Override
	public HttpEntity<? extends Object> allUserCommand(Long customerPhone, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			}else {
				List<Command> currentCommands = commandRepository.findByCustomerPhone(customerPhone);
				return new ResponseEntity<List<Command>>(currentCommands, HttpStatus.FOUND);		// renvoie la liste des commandes d'un client et une reponse http
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec une reponse http
		}
	}
	@Override
	public HttpEntity<? extends Object> findByOrderByDateCreationDesc() {
		try {
			return new ResponseEntity<List<Command>>(commandRepository.findByOrderByDateCreationDesc(), HttpStatus.FOUND);	// lites des commandes par ordre decroissant et une reponse http
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	// renvoie d'un message d'erreur avec une reponse http
		}
	}
	@Override
	public HttpEntity<? extends Object> findByOrderByDateCreationAsc() {
		try {
			return new ResponseEntity<List<Command>>(commandRepository.findByOrderByDateCreationAsc(), HttpStatus.FOUND);	// lites des commandes par ordre croissant et une reponse http
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	// renvoie d'un message d'erreur avec une reponse http
		}
	}
	@Override
	public HttpEntity<? extends Object> findByValidator(String validator, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			}else {
				List<Command> currentCommands = commandRepository.findByValidator(validator);
				return new ResponseEntity<List<Command>>(currentCommands, HttpStatus.FOUND);	// renvoie la liste des commandes creer par un caissier et une reponse http
			}
			
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	// renvoie d'un message d'erreur avec une reponse http
		}
	}
	@Override
	public HttpEntity<? extends Object> updateCommand(Command command, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			}else {
				Command currentCommand = commandRepository.findById(command.getCommandId()).get();
				if (currentCommand.getCustomerPhone().equals(command.getCustomerPhone()) && currentCommand.getProductCommand().equals(command.getProductCommand())) {
					return new ResponseEntity<Command>(currentCommand, HttpStatus.ACCEPTED);
				}else {
					currentCommand.setCustomerPhone(command.getCustomerPhone());
					currentCommand.setCommandType(command.getCommandType());
					currentCommand.setProductCommand(command.getProductCommand());
					commandRepository.saveAndFlush(currentCommand);
					
					List<ProductCommand> orders = command.getProductCommand();
					for (ProductCommand productCommand : orders) {
						productCommand.setCommand(currentCommand);
						productCommandrepository.saveAndFlush(productCommand);
					}

					return new ResponseEntity<Command>(currentCommand, HttpStatus.ACCEPTED);
				}
			}
			
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	// renvoie d'un message d'erreur avec une reponse http
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
				return new ResponseEntity<List<Command>>(commandRepository.findAll(), HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public HttpEntity<? extends Object> lastCommand(Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<List<Command>>(commandRepository.findAll(), HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
