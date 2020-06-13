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

import boostSoft.boostTest.data.User;
import boostSoft.boostTest.service.api.UserServiceApi;


@RestController
@RequestMapping(path = "/auth")
public class UserController {

	@Autowired UserServiceApi userServiceApi;
	
	// end-point d'enregistrement d'un utilisateur
	@PostMapping("/register")
	public HttpEntity<? extends Object> createUser(@RequestBody User user) {
		return userServiceApi.create(user);
	}
	
	// end-point de recherche d'un utilisateur par son nom d'utilisateur
	@GetMapping("/select")
	public HttpEntity<? extends Object> findByUserName(@RequestParam String username, Principal principal){
		return userServiceApi.findByUserName(username,principal);
	}
	
	// end-point de suppression d'un utilisateur
	@DeleteMapping("/delete")
	public HttpEntity<? extends Object> delete(@RequestParam String username, Principal principal){
		return userServiceApi.delete(username,principal);
	}
	
	// end-point de mis a jour d'un utilisateur
	@PutMapping("/update")
	public HttpEntity<? extends Object> update(@RequestBody User user, Principal principal){
		return userServiceApi.update(user,principal);
	}
	
	// end-point de mis a jour du nom d'un utilisateur
	@PutMapping("/name")
	public HttpEntity<? extends Object> updateName(@RequestParam int userId,@RequestParam String name,Principal principal){
		return userServiceApi.updateName(userId, name, principal);
	}
	
	// end-point de mis a jour du nom d'utilisateur
	@PutMapping("/username")
	public HttpEntity<? extends Object> updateUserName(@RequestParam int userId,@RequestParam String username,Principal principal){
		return userServiceApi.updateUserName(userId, username, principal);
	}
	
	// end-point de mis a jour du mot de passe d'un utilisateur
	@PutMapping("/password")
	public HttpEntity<? extends Object> updatePassWord(@RequestParam int userId,@RequestParam String password,Principal principal){
		return userServiceApi.updatePassWord(userId, password,principal);
	}
	
	// end-point de mis a jour du nom d'un utilisateur
	@PutMapping("/mail")
	public HttpEntity<? extends Object> updateMail(@RequestParam int userId,@RequestParam String mail,Principal principal){
		return userServiceApi.updateMail(userId, mail, principal);
	}
	
	// end-point de mis a jour du numero de telephone d'un utilisateur
	@PutMapping("/phonenumber")
	public HttpEntity<? extends Object> updatePhoneNumber(@RequestParam int userId,@RequestParam String phonenumber,Principal principal){
		return userServiceApi.updatePhoneNumber(userId, phonenumber,principal);
	}
	
	@PutMapping("/store")
	public HttpEntity<? extends Object> updateStore(@RequestParam int userId,@RequestParam String store,Principal principal){
		return userServiceApi.updateStore(userId, store, principal);
	}
	
	// end-point de recherche de tous les utilisateurs
	@GetMapping("/users")
	public HttpEntity<? extends Object> findAll(Principal principal){
		return userServiceApi.findAll(principal);
	}
	
	// end-point de login d'un utilisateur
	@PostMapping("/login")
	public HttpEntity<? extends Object> login(Principal principal){
		return userServiceApi.login(principal);
	}
	
	// end-point de logout d'un utilisateur
	@PostMapping("/logout")
	public HttpEntity<? extends Object> logout(Principal principal){
		return userServiceApi.logout(principal);
	}
	
	// end-point de recherche d'un utilisateur par son nom
	@GetMapping("/name")
	public HttpEntity<? extends Object> findByName(@RequestParam String name,Principal principal){
		return userServiceApi.findByName(name,principal);
	}
	
	// end-point de blocage d'un utilisateur
	@PostMapping("/blocked")
	public HttpEntity<? extends Object> blockedUser(@RequestParam String username,Principal principal){
		return userServiceApi.blockedUser(username,principal);
	}
	
	@PutMapping("/resetPassword")
	public HttpEntity<? extends Object> resetPassword(@RequestParam String username,@RequestParam String newPassword,
			Principal principal){
		return userServiceApi.resetPassword(username, newPassword, principal);
	}

}
