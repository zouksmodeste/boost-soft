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
			boolean isExist = false;
			List<User> currentUsers = userRepository.findAll();
			for (User user2 : currentUsers) {
				if (user2.getUserName().equalsIgnoreCase(user.getUserName()) || user2.getPhoneNumber().equalsIgnoreCase(user.getPhoneNumber())) {
					isExist = true;
					break;
				}
			}
			if(isExist) {
				return new ResponseEntity<String>("Could not create another user with same username or phone number", HttpStatus.ACCEPTED);
			}else {
				if (user.getUserName()==null || user.getPhoneNumber()==null) {
					return new ResponseEntity<String>("Please enter informations in blank place (username or phone number)", HttpStatus.ACCEPTED);
				}else {
					// enregistrement d'un utilisateur
					user.setUserType(user.getUserType());
					user.setUserStatus(UserStatus.VALID.getStatut());
					user.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassWord()));
					User currentUser = userRepository.save(user);
					return new ResponseEntity<User>(currentUser, HttpStatus.OK);
				}

			}
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
			/*Map<String, Object> result = new HashMap<>();
			result.put("message", "internal server error");
			result.put("value", "");
			result.put("code", "0");
			return result;*/
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
				User currentUser1 = userRepository.findByUserName(username); 
				currentUser1.setUserStatus(UserStatus.DELETED.getStatut()); 
				userRepository.saveAndFlush(currentUser1); 
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
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
				User currentUser1 = userRepository.findById(user.getUserId()).get(); 
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
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
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
				User currentUser1 = userRepository.findById(userId).get(); 
				currentUser1.setName(name); 
				userRepository.saveAndFlush(currentUser1); 
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK); 
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
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
				User currentUser1 = userRepository.findById(userId).get(); 
				currentUser1.setUserName(username); 
				userRepository.saveAndFlush(currentUser1); 
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK); 
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
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
				User currentUser1 = userRepository.findById(userId).get(); 
				currentUser1.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password)); 
				userRepository.saveAndFlush(currentUser1); 
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK); 
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
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
				User currentUser1 = userRepository.findById(userId).get(); 
				currentUser1.setMail(mail); 
				userRepository.saveAndFlush(currentUser1); 
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK); 
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
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
				User currentUser1 = userRepository.findById(userId).get(); 
				currentUser1.setPhoneNumber(phonenumber); 
				userRepository.saveAndFlush(currentUser1); 
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK); 
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> login(Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName()); 
			if (!(currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut()))
					&& !(currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut()))) { 

				currentUser.setUserStatus(UserStatus.SIGNEDIN.getStatut()); 
				userRepository.saveAndFlush(currentUser); 
				return new ResponseEntity<User>(currentUser, HttpStatus.OK); 
			} else {
				return new ResponseEntity<String>(
						"you are not allowed to acces this system contact Boost Soft for more details",
						HttpStatus.FORBIDDEN); 
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> findAll(Principal principal) {
		try {
			List<User> currentUsers = userRepository.findAll(); 
			User currentUser = userRepository.findByUserName(principal.getName());
			if (currentUser.getUserStatus().equalsIgnoreCase(UserStatus.BLOCKED.getStatut())
					|| currentUser.getUserStatus().equalsIgnoreCase(UserStatus.DELETED.getStatut())) {
				return new ResponseEntity<String>("this user are not allowed", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<List<User>>(currentUsers, HttpStatus.OK); 
			}

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

	@Override
	public HttpEntity<? extends Object> logout(Principal principal) {
		try {
			User currentUser = userRepository.findByUserName(principal.getName()); 
			currentUser.setUserStatus(UserStatus.SIGNEDOUT.getStatut()); 
			userRepository.saveAndFlush(currentUser); 
			return new ResponseEntity<User>(currentUser, HttpStatus.OK); 
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
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
				List<User> currentUsers = userRepository.findByName(name); 
				return new ResponseEntity<List<User>>(currentUsers, HttpStatus.OK); 
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
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
				User currentUser1 = userRepository.findByUserName(username); 
				currentUser1.setUserStatus(UserStatus.BLOCKED.getStatut()); 
				userRepository.saveAndFlush(currentUser1); 
				return new ResponseEntity<User>(currentUser1, HttpStatus.OK); 
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
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
