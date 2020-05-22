package boostSoft.boostTest.service.impl;

import java.security.Principal;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import boostSoft.boostTest.data.User;
import boostSoft.boostTest.data.UserStatus;
import boostSoft.boostTest.mail.EmailServiceImpl;
import boostSoft.boostTest.repository.UserRepository;
import boostSoft.boostTest.service.api.UserServiceApi;
import boostSoft.boostTest.sms.TwilioConfiguration;

@Service
public class UserServiceImpl implements UserServiceApi {

	@Autowired UserRepository userRepository;
	@Autowired SimpleMailMessage template;
	@Autowired EmailServiceImpl emailServiceImpl;
	@Autowired TwilioConfiguration twilioConfiguration;
	
	@Override
	public HttpEntity<? extends Object> create(User user) {
		try {
		    final String messages = "Your activation code is :"; // message personnalise pour envoie du code de validatiom
			
		    if (user.getName()==null || user.getSubName()==null || user.getUserName()==null || user.getPassWord()==null || user.getMail()==null || user.getPhoneNumber()==null || user.getUserType()==null) {
		    	return new ResponseEntity<String>("Please enter informations in blank places", HttpStatus.BAD_REQUEST); // renvoie un message d'erreur avec une reponse http
			}else {
				// enregistrement d'un utilisateur
				Random r = new Random();
				user.setValidate(false);
				user.setUserStatus(UserStatus.AWAITINGVALIDATION.getStatut());
				user.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassWord()));
				user.setValidateMessage(r.nextInt(10000 - 1000));
				User currentUser = userRepository.save(user);

				
				// envoie du code de validation du compte a l'utilisateur par message
				PhoneNumber to = new PhoneNumber(user.getPhoneNumber());
				PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
				String message = messages;
				MessageCreator creator = Message.creator(to, from, message + user.getValidateMessage());
				creator.create();
				
				
				// envoie du mail de bienvenue a l'utilisateur
				String text = String.format(template.getText());
				emailServiceImpl.sendSimpleMessage("boost.test.v1@gmail.com", user.getMail(), "Welcome", text);
				return new ResponseEntity<User>(currentUser, HttpStatus.CREATED); // renvoie l'utilisateur enregistre avec un code http
			}
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> delete(String username) {
		try {
			User currentUser = userRepository.findByUserName(username);		// recherche de l'utilisateur
			currentUser.setUserStatus(UserStatus.DELETED.getStatut());		// mise a jour du statut de l'utilisateur
			userRepository.saveAndFlush(currentUser);						// mise a jour de l'utilisateur en base de donnees
			return new ResponseEntity<User>(currentUser, HttpStatus.GONE);  // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> findByUserName(String username) {
		try {
			User currentUser = userRepository.findByUserName(username);		// recherche de l'utilisateur
			return new ResponseEntity<User>(currentUser, HttpStatus.FOUND); // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> update(User user) {
		try {
			User currentUser = userRepository.findById(user.getUserId()).get();		// recherche de l'utilisateur
			currentUser.setName(user.getName());									// mis a jour du nouveau nom utilisateur
			currentUser.setSubName(user.getSubName());								// mis a jour du nouveau prenom utilisateur
			currentUser.setUserName(user.getUserName());							// mis a jour du nouveau nom d'utilisateur
			currentUser
					.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassWord())); // mis a jour du nouveau mot de passe utilisateur
			currentUser.setMail(user.getMail());									// mis a jour du nouveau mail utilisateur
			currentUser.setPhoneNumber(user.getPhoneNumber());						// mis a jour du nouveau telephone utilisateur
			userRepository.saveAndFlush(currentUser);								// mis a jour de l'utilisateur en base de donnees
			return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED);      // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> updateName(int userId, String name) {
		try {
			User currentUser = userRepository.findById(userId).get();			// recherche de l'utilisateur
			currentUser.setName(name);											// mis a jour du nouveau nom de l'utilisateur
			userRepository.saveAndFlush(currentUser);							// mis a jour de l'utilisateur en base de donnees
			return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED);  // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> updateSubName(int userId, String subname) {
		try {
			User currentUser = userRepository.findById(userId).get();			// recherche de l'utilisateur
			currentUser.setSubName(subname);									// mis a jour du nouveau prenom de l'utilisateur
			userRepository.saveAndFlush(currentUser);							// mis a jour de l'utilisateur en base de donnees
			return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED);  // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> updateUserName(int userId, String username) {
		try {
			User currentUser = userRepository.findById(userId).get();			// recherche de l'utilisateur
			currentUser.setUserName(username);									// mis a jour du nouveau nom d'utilisateur
			userRepository.saveAndFlush(currentUser); 							// mis a jour de l'utilisateur en base de donnees
			return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED);  // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> updatePassWord(int userId, String password) {
		try {
			User currentUser = userRepository.findById(userId).get();           // recherche de l'utilisateur
			currentUser.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password)); // mis a jour du nouveau mot de passe de l'utilisateur
			userRepository.saveAndFlush(currentUser);							// mis a jour de l'utilisateur en base de donnees
			return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED);  // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> updateMail(int userId, String mail) {
		try {
			User currentUser = userRepository.findById(userId).get();			// recherche de l'utilisateur
			currentUser.setMail(mail);											// mis a jour du mail utilisateur
			userRepository.saveAndFlush(currentUser);							// mis a jour de l'utilisateur en base de donnees
			return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED); // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> updatePhoneNumber(int userId, String phonenumber) {
		try {
			User currentUser = userRepository.findById(userId).get();			// recherche de l'utilisateur
			currentUser.setPhoneNumber(phonenumber);							// mis a jour du nouveau contact
			userRepository.saveAndFlush(currentUser);							// mis a jour de l'utilisateur en base de donnees
			return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED); // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> validateUser(int validateMessage, String username) {
		try {
			User currentUser = userRepository.findByUserName(username);			// recherche de l'utilisateur	
			if (currentUser.getValidateMessage().equals(validateMessage)) {		// comparaison du code en base de donnee et celui fourni par l'utilisateur dans le formulaire
				currentUser.setValidate(true);									// mis a jour de l'attribut validate
				currentUser.setUserStatus(UserStatus.VALID.getStatut());		// mis a jour du status de l'utilisateur
				userRepository.saveAndFlush(currentUser);						// mis a jour de l'utilisateur en base de donnees
			}
			return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED); // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> login(Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());	// recherche de l'utilisateur				
			if (currentUser.getValidate()
					&& !(currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut()))) {  // verification si l'utilisateur est valide et non bloque
				
				currentUser.setUserStatus(UserStatus.SIGNEDIN.getStatut());			// mis a jour du status de l'utilisateur 
				userRepository.saveAndFlush(currentUser);							// mis a jour de l'utilisateur en base de donnees 
				return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED);  // renvoie de l'utilisateur avec un code http
			} else {
				// l'utilisateur n'est pas valide donc acces interdit
				if (!currentUser.getValidate()) {
					return new ResponseEntity<String>("please validate your account first ", HttpStatus.UNAUTHORIZED); // renvoie une chaine de caractere couple avec une reponse http
				}
				// l'utilisateur est bloque donc acces interdit
				else {
					return new ResponseEntity<String>(
							"you are not allowed to acces this system contact Boost Soft for more details",
							HttpStatus.FORBIDDEN);   // renvoie une chaine de caractere couple avec une reponse http
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> findAll() {
		try {
			List<User> currentUsers = userRepository.findAll();							// recherche de tous les utilisateurs en base de donnees	
			return new ResponseEntity<List<User>>(currentUsers, HttpStatus.ACCEPTED); // renvoie une liste d'utilisateurs avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> logout(Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());   // recherche de l'utilisateur 
			currentUser.setUserStatus(UserStatus.SIGNEDOUT.getStatut());			 // changement du status de l'utilisateur
			userRepository.saveAndFlush(currentUser);								 // mis a jour de l'utilisateur dans la base de donnees
			return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED); 		 // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> findByName(String name) {
		try {
			List<User> currentUsers = userRepository.findByName(name);				// recherche des utilisateurs ayant le meme nom
			return new ResponseEntity<List<User>>(currentUsers, HttpStatus.FOUND);  // renvoie une liste d'utilisateurs avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}
	
	@Override
	public HttpEntity<? extends Object> verifyAccount(String username) {
		try {
			final String messages = "Your verification code is :";
			
			User currentUser = userRepository.findByUserName(username);			// recherche de l'utilisateur
			Random r = new Random();											// fonction alleatoire
			currentUser.setValidateMessage(r.nextInt(10000 - 100));             // creation du nouveau code de validation du compte
			userRepository.saveAndFlush(currentUser);							// mis a jour de l'utilisateur en base de donnees
			
			// envoie du code de validation du compte a l'utilisateur par message
			PhoneNumber to = new PhoneNumber(currentUser.getPhoneNumber());
			PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
			String message = messages;
			MessageCreator creator = Message.creator(to, from, message + currentUser.getValidateMessage());
			creator.create();
			
			return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED);  // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}

	}

	@Override
	public HttpEntity<? extends Object> resetPasswordUser(String password, int validateMessage, String username) {
		try {
			User currentUser = userRepository.findByUserName(username);                // recherche de l'utilisateur
			if (currentUser.getValidateMessage() == validateMessage) {                 // comparaison du code de l'utilisateur en base de donne et celui fourni par celui-ci dans le formulaire
				currentUser
						.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password));
				userRepository.saveAndFlush(currentUser);								// mis a jour du mot de passe de l'utilisateur
				return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED);      // renvoie de l'utilisateur avec un code http
			} else {
				return new ResponseEntity<String>("Wrong code ", HttpStatus.NOT_FOUND); // renvoie une chaine de caractere avec un code http
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}

	}

	@Override
	public HttpEntity<? extends Object> blockedUser(String username) {
		try {
			User currentUser = userRepository.findByUserName(username);        // rechercher de l'utilisateur
			currentUser.setUserStatus(UserStatus.BLOCKED.getStatut());         // mis a jour du status de l'utilisateur
			userRepository.saveAndFlush(currentUser);                          // mis a jour de l'utilisateur dans la base de donnees													
			return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED); // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

	@Override
	public HttpEntity<? extends Object> suspendedUser(String username) {
		try {
			User currentUser = userRepository.findByUserName(username);        // recherche de l'utilisateur
			currentUser.setUserStatus(UserStatus.SUSPENDED.getStatut());       // changement du status de l'utilisateur
			userRepository.saveAndFlush(currentUser);                          // mis a jour de l'utilisateur dans la base de donnee
			return new ResponseEntity<User>(currentUser, HttpStatus.ACCEPTED); // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message d'erreur avec un code http
		}
	}

}
