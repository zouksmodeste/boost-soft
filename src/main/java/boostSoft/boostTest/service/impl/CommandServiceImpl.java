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
import boostSoft.boostTest.data.Product;
import boostSoft.boostTest.data.ProductCommand;
import boostSoft.boostTest.data.ProductStatus;
import boostSoft.boostTest.repository.CommandRepository;
import boostSoft.boostTest.repository.ProductCommandrepository;
import boostSoft.boostTest.repository.ProductRepository;
import boostSoft.boostTest.service.api.CommandServiceApi;

@Service
public class CommandServiceImpl implements CommandServiceApi {

	@Autowired CommandRepository commandRepository;
	@Autowired ProductRepository productRepository;
	@Autowired ProductCommandrepository productCommandrepository;

	@Override
	public HttpEntity<? extends Object> createCommand(Command command, Principal principal) {
		try {
			
			if (command.getCommandType()==null || command.getCustomerPhone()==null || command.getProductCommand()==null) {
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
				command.setDateCreation(new Date());
				command.setValidator(principal.getName());
				command.setStatus(CommandStatus.EN_ATTENTE.getStatut());		// enregistrement de la commande
				command.setProductCommand(new ArrayList<ProductCommand>());
				command = commandRepository.save(command);
				
				for (ProductCommand commandproduct : realState) {				// referencement de la commande dans le pojo productCommand
					commandproduct.setCommand(command);
					command.getProductCommand().add(productCommandrepository.save(commandproduct));
					commandRepository.saveAndFlush(command);
				}
				return new ResponseEntity<Command>(commandRepository.findById(command.getCommandId()).get(), HttpStatus.CREATED); // renvoie de la command enregistrer avec une reponse http
			}
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // renvoie d'un message d'erreur avec une reponse http
		}
		
		
	}
	@Override
	public HttpEntity<? extends Object> confirmCommand(Long commandId) {
		try {
			Command currentCommand= commandRepository.getOne(commandId);
			currentCommand.setStatus(CommandStatus.VALIDE.getStatut());							// mise a jour du status de la commande
			currentCommand=commandRepository.saveAndFlush(currentCommand);
			return new ResponseEntity<Command>(currentCommand, HttpStatus.ACCEPTED);			// renvoie de la commande mise a jour avec une reponse http
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec une reponse http
		}
		
	}
	@Override
	public HttpEntity<? extends Object> cancelCommand(Long commandId) {
		try {
			Command currentCommand= commandRepository.findByCommandId(commandId).get();
			currentCommand.setStatus(CommandStatus.CANCELED.getStatut());						// mise a jour du status de la commande
			currentCommand=commandRepository.saveAndFlush(currentCommand);
			return new ResponseEntity<Command>(currentCommand, HttpStatus.ACCEPTED);            // renvoie de la commande mise a jour avec une reponse http

		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec une reponse http
		}
			}
	@Override
	public HttpEntity<? extends Object> deleverCommand(Long commandId) {
		try {
			Command currentCommand= commandRepository.findByCommandId(commandId).get();
			currentCommand.setStatus(CommandStatus.TERMINE.getStatut());						// mise a jour du status de la commande 
			currentCommand=commandRepository.saveAndFlush(currentCommand);
			return new ResponseEntity<Command>(currentCommand, HttpStatus.ACCEPTED);			// renvoie de la commande mise a jour avec une reponse http

		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec une reponse http
		}
	}
	@Override
	public HttpEntity<? extends Object> allCancelCommand() {
		try {
			List<Command> currentCommands = commandRepository.findByStatus(CommandStatus.CANCELED.getStatut());
			return new ResponseEntity<List<Command>>(currentCommands, HttpStatus.FOUND);		// renvoie la liste des commandes annulees et une reponse http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec une reponse http
		}
	}
	@Override
	public HttpEntity<? extends Object> allConfirmCommand() {
		try {
			List<Command> currentCommands = commandRepository.findByStatus(CommandStatus.VALIDE.getStatut());
			return new ResponseEntity<List<Command>>(currentCommands, HttpStatus.FOUND);		// renvoie la liste des commandes confirmees et une reponse http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec une reponse http
		}
	}
	@Override
	public HttpEntity<? extends Object> alldeleverCommand() {
		try {
			List<Command> currentCommands = commandRepository.findByStatus(CommandStatus.TERMINE.getStatut());
			return new ResponseEntity<List<Command>>(currentCommands, HttpStatus.FOUND);		// renvoie la liste des commandes terminees et une reponse http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec une reponse http
		}
	}
	@Override
	public HttpEntity<? extends Object> allUserCommand(Long customerPhone) {
		try {
			List<Command> currentCommands = commandRepository.findByCustomerPhone(customerPhone);
			return new ResponseEntity<List<Command>>(currentCommands, HttpStatus.FOUND);		// renvoie la liste des commandes d'un client et une reponse http
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
	public HttpEntity<? extends Object> findByValidator(String validator) {
		try {
			List<Command> currentCommands = commandRepository.findByValidator(validator);
			return new ResponseEntity<List<Command>>(currentCommands, HttpStatus.FOUND);	// renvoie la liste des commandes creer par un caissier et une reponse http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	// renvoie d'un message d'erreur avec une reponse http
		}
	}
	@Override
	public HttpEntity<? extends Object> updateCommand(Command command) {
		try {
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
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	// renvoie d'un message d'erreur avec une reponse http
		}
	}
}
