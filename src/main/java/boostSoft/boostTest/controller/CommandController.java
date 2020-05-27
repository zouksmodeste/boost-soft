package boostSoft.boostTest.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boostSoft.boostTest.data.Command;
import boostSoft.boostTest.service.api.CommandServiceApi;

@RestController
@RequestMapping(path = "command")
public class CommandController {

	@Autowired CommandServiceApi commandServiceApi;
	
	// end-point d'enregistrement d'une commande
	@PostMapping("/register")
	public HttpEntity<? extends Object> createCommand(@RequestBody Command command, Principal principal){
		return commandServiceApi.createCommand(command, principal);
	}
	
	// end-point de la liste de toutes les commandes d'un client
	@GetMapping("/commandCustomer")
	public HttpEntity<? extends Object> allUserCommand(@RequestParam Long customerPhone, Principal principal){
		return commandServiceApi.allUserCommand(customerPhone, principal);
	}
	
	// end-point de recherche des commandes par date de creation decroissante
	@GetMapping("/dateDesc")
	public HttpEntity<? extends Object> findByOrderByDateCreationDesc(){
		return commandServiceApi.findByOrderByDateCreationDesc();
	}
	
	// end-point de recherche des commandes par date de creation croissante
	@GetMapping("/dateAsc")
	public HttpEntity<? extends Object> findByOrderByDateCreationAsc(){
		return commandServiceApi.findByOrderByDateCreationAsc();
	}
	
	// end-point de la liste de toutes les commandes d'un caissier
	@GetMapping("/validatorCommand")
	public HttpEntity<? extends Object> findByValidator(@RequestParam String validator, Principal principal){
		return commandServiceApi.findByValidator(validator, principal);
	}
	
	@PutMapping("/update")
	public HttpEntity<? extends Object> updateCommand(@RequestBody Command command, Principal principal){
		return commandServiceApi.updateCommand(command, principal);
	}
	
	@GetMapping("/commands")
	public HttpEntity<? extends Object> findAll(Principal principal){
		return commandServiceApi.findAll(principal);
	}
}
