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
	public HttpEntity<? extends Object> findByUserName(@RequestParam String username){
		return userServiceApi.findByUserName(username);
	}
	
	// end-point de suppression d'un utilisateur
	@DeleteMapping("/delete")
	public HttpEntity<? extends Object> delete(@RequestParam String name){
		return userServiceApi.delete(name);
	}
	
	// end-point de mis a jour d'un utilisateur
	@PutMapping("/update")
	public HttpEntity<? extends Object> update(@RequestBody User user){
		return userServiceApi.update(user);
	}
	
	// end-point de mis a jour du nom d'un utilisateur
	@PutMapping("/name")
	public HttpEntity<? extends Object> updateName(@RequestParam int userId,@RequestParam String name){
		return userServiceApi.updateName(userId, name);
	}
	
	// end-point de mis a jour du prenom d'un utilisateur
	@PutMapping("/subname")
	public HttpEntity<? extends Object> updateSubName(@RequestParam int userId,@RequestParam String subname){
		return userServiceApi.updateSubName(userId, subname);
	}
	
	// end-point de mis a jour du nom d'utilisateur
	@PutMapping("/username")
	public HttpEntity<? extends Object> updateUserName(@RequestParam int userId,@RequestParam String username){
		return userServiceApi.updateUserName(userId, username);
	}
	
	// end-point de mis a jour du mot de passe d'un utilisateur
	@PutMapping("/password")
	public HttpEntity<? extends Object> updatePassWord(@RequestParam int userId,@RequestParam String password){
		return userServiceApi.updatePassWord(userId, password);
	}
	
	// end-point de mis a jour du nom d'un utilisateur
	@PutMapping("/mail")
	public HttpEntity<? extends Object> updateMail(@RequestParam int userId,@RequestParam String mail){
		return userServiceApi.updateMail(userId, mail);
	}
	
	// end-point de mis a jour du numero de telephone d'un utilisateur
	@PutMapping("/phonenumber")
	public HttpEntity<? extends Object> updatePhoneNumber(@RequestParam int userId,@RequestParam String phonenumber){
		return userServiceApi.updatePhoneNumber(userId, phonenumber);
	}
	
	// end-point de validation d'un compte utilisateur
	@PostMapping("/validate")
	public HttpEntity<? extends Object> validateUser(@RequestParam int validateMessage,@RequestParam String username){
		return userServiceApi.validateUser(validateMessage, username);
	}
	
	// end-point de recherche de tous les utilisateurs
	@GetMapping("/users")
	public HttpEntity<? extends Object> findAll(){
		return userServiceApi.findAll();
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
	public HttpEntity<? extends Object> findByName(@RequestParam String name){
		return userServiceApi.findByName(name);
	}
	
	// end-point de verification d'un utilisateur
	@PostMapping("/verifyaccount")
	public HttpEntity<? extends Object> verifyAccount(@RequestParam String username){
		return userServiceApi.verifyAccount(username);
	}
	
	// end-point de reinitialisation du mot de passe d'un utilisateur
	@PostMapping("/resetpassword")
	public HttpEntity<? extends Object> resetPasswordUser(@RequestParam String password,@RequestParam int validateMessage,@RequestParam String username){
		return userServiceApi.resetPasswordUser(password, validateMessage, username);
	}
	
	// end-point de blocage d'un utilisateur
	@PostMapping("/blocked")
	public HttpEntity<? extends Object> blockedUser(@RequestParam String username){
		return userServiceApi.blockedUser(username);
	}
	
	// end-point de suspition d'un utilisateur
	@PostMapping("/suspend")
	public HttpEntity<? extends Object> suspendedUser(@RequestParam String username){
		return userServiceApi.suspendedUser(username);
	}
}
