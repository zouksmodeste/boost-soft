package boostSoft.boostTest.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import boostSoft.boostTest.data.User;
import boostSoft.boostTest.data.UserStatus;
import boostSoft.boostTest.repository.UserRepository;
import boostSoft.boostTest.service.api.UserServiceApi;

@Service
public class UserServiceImpl implements UserServiceApi {

	@Autowired
	UserRepository userRepository;

	@Override
	public HttpEntity<? extends Object> create(User user) {
		try {
			// enregistrement d'un utilisateur
			user.setUserType(user.getUserType());
			user.setUserStatus(UserStatus.VALID.getStatut());
			user.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassWord()));
			User currentUser = userRepository.save(user);
			return new ResponseEntity<User>(currentUser, HttpStatus.OK); // renvoie l'utilisateur enregistre avec un
																			// code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> delete(String username, Principal principal) {
		try {

			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				User currentUser1 = userRepository.findByUserName(username); // recherche de l'utilisateur
				currentUser1.setUserStatus(UserStatus.DELETED.getStatut()); // mise a jour du statut de l'utilisateur
				userRepository.saveAndFlush(currentUser1); // mise a jour de l'utilisateur en base de donnees
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> findByUserName(String username, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<User>(userRepository.findByUserName(username), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> update(User user, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				User currentUser1 = userRepository.findById(user.getUserId()).get(); // recherche de l'utilisateur
				if (currentUser1.getName().equalsIgnoreCase(user.getName())
						&& currentUser1.getStore().equalsIgnoreCase(user.getStore())
						&& currentUser1.getMail().equalsIgnoreCase(user.getMail())
						&& currentUser1.getPhoneNumber().equalsIgnoreCase(user.getPhoneNumber())
						&& currentUser1.getUserName().equalsIgnoreCase(user.getUserName())
						&& currentUser1.getUserType().equals(user.getUserType())
						&& currentUser1.getPassWord().equalsIgnoreCase(user.getPassWord())) {
					return new ResponseEntity<User>(currentUser1, HttpStatus.OK);
				} else {
					currentUser1.setName(user.getName());
					currentUser1.setStore(user.getStore());
					currentUser1.setMail(user.getMail());
					currentUser1.setPhoneNumber(user.getPhoneNumber());
					currentUser1.setUserName(user.getUserName());
					currentUser1.setUserType(user.getUserType());
					currentUser1.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassWord()));
					userRepository.saveAndFlush(currentUser1);
					return new ResponseEntity<User>(currentUser1, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> updateName(int userId, String name, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				User currentUser1 = userRepository.findById(userId).get(); // recherche de l'utilisateur
				currentUser1.setName(name); // mis a jour du nouveau nom de l'utilisateur
				userRepository.saveAndFlush(currentUser1); // mis a jour de l'utilisateur en base de donnees
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK); // renvoie de l'utilisateur avec un code
																				// http
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> updateUserName(int userId, String username, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				User currentUser1 = userRepository.findById(userId).get(); // recherche de l'utilisateur
				currentUser1.setUserName(username); // mis a jour du nouveau nom d'utilisateur
				userRepository.saveAndFlush(currentUser1); // mis a jour de l'utilisateur en base de donnees
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK); // renvoie de l'utilisateur avec un code
																				// http
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> updatePassWord(int userId, String password, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				User currentUser1 = userRepository.findById(userId).get(); // recherche de l'utilisateur
				currentUser1.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password)); // mis
																														// a
																														// jour
																														// du
																														// nouveau
																														// mot
																														// de
																														// passe
																														// de
																														// l'utilisateur
				userRepository.saveAndFlush(currentUser1); // mis a jour de l'utilisateur en base de donnees
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK); // renvoie de l'utilisateur avec un code
																				// http
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> updateMail(int userId, String mail, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				User currentUser1 = userRepository.findById(userId).get(); // recherche de l'utilisateur
				currentUser1.setMail(mail); // mis a jour du mail utilisateur
				userRepository.saveAndFlush(currentUser1); // mis a jour de l'utilisateur en base de donnees
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK); // renvoie de l'utilisateur avec un code
																				// http
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> updatePhoneNumber(int userId, String phonenumber, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				User currentUser1 = userRepository.findById(userId).get(); // recherche de l'utilisateur
				currentUser1.setPhoneNumber(phonenumber); // mis a jour du nouveau contact
				userRepository.saveAndFlush(currentUser1); // mis a jour de l'utilisateur en base de donnees
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK); // renvoie de l'utilisateur avec un code
																				// http
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> login(Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName()); // recherche de l'utilisateur
			if (!(currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut()))
					&& !(currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut()))) { // verification
																											// si
																											// l'utilisateur
																											// est
																											// valide et
																											// non
																											// bloque

				currentUser.setUserStatus(UserStatus.SIGNEDIN.getStatut()); // mis a jour du status de l'utilisateur
				userRepository.saveAndFlush(currentUser); // mis a jour de l'utilisateur en base de donnees
				return new ResponseEntity<User>(currentUser, HttpStatus.OK); // renvoie de l'utilisateur avec un code
																				// http
			} else {
				// l'utilisateur est bloque OU SUPPRIMER donc acces interdit

				return new ResponseEntity<String>(
						"you are not allowed to acces this system contact Boost Soft for more details",
						HttpStatus.FORBIDDEN); // renvoie une chaine de caractere couple avec une reponse http
			}

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> findAll(Principal principal) {
		try {
			List<User> currentUsers = userRepository.findAll(); // recherche de tous les utilisateurs en base de donnees
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<List<User>>(currentUsers, HttpStatus.OK); // renvoie une liste d'utilisateurs
																					// avec un code http
			}

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> logout(Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName()); // recherche de l'utilisateur
			currentUser.setUserStatus(UserStatus.SIGNEDOUT.getStatut()); // changement du status de l'utilisateur
			userRepository.saveAndFlush(currentUser); // mis a jour de l'utilisateur dans la base de donnees
			return new ResponseEntity<User>(currentUser, HttpStatus.OK); // renvoie de l'utilisateur avec un code http
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> findByName(String name, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				List<User> currentUsers = userRepository.findByName(name); // recherche des utilisateurs ayant le meme
																			// nom
				return new ResponseEntity<List<User>>(currentUsers, HttpStatus.OK); // renvoie une liste d'utilisateurs
																					// avec un code http
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> blockedUser(String username, Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				User currentUser1 = userRepository.findByUserName(username); // rechercher de l'utilisateur
				currentUser1.setUserStatus(UserStatus.BLOCKED.getStatut()); // mis a jour du status de l'utilisateur
				userRepository.saveAndFlush(currentUser1); // mis a jour de l'utilisateur dans la base de donnees
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK); // renvoie de l'utilisateur avec un code
																				// http
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // renvoie d'un message
																									// d'erreur avec un
																									// code http
		}
	}

	@Override
	public HttpEntity<? extends Object> resetPassword(String username, String newPassword,Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				User currentUser1 = userRepository.findByUserName(username);
				currentUser1.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(newPassword));
				userRepository.saveAndFlush(currentUser1);
				return new ResponseEntity<User>(currentUser1, HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
