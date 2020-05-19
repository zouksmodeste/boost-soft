package boostSoft.boostTest.service.api;

import java.security.Principal;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import boostSoft.boostTest.data.User;

@Service
public interface UserServiceApi {

	public abstract HttpEntity<? extends Object> create(User user);
	public abstract HttpEntity<? extends Object> findAll();
	public abstract HttpEntity<? extends Object> delete(String name);
	public abstract HttpEntity<? extends Object> findByUserName(String username);
	public abstract HttpEntity<? extends Object> update(User user);
	public abstract HttpEntity<? extends Object> updateName(int userId, String name);
	public abstract HttpEntity<? extends Object> updateSubName(int userId, String subname);
	public abstract HttpEntity<? extends Object> updateUserName(int userId, String username);
	public abstract HttpEntity<? extends Object> updatePassWord(int userId, String password);
	public abstract HttpEntity<? extends Object> updateMail(int userId, String mail);
	public abstract HttpEntity<? extends Object> updatePhoneNumber(int userId, String phonenumber);
	public abstract HttpEntity<? extends Object> validateUser(int validateMessage, String username);
	public abstract HttpEntity<? extends Object> login(Principal principal);
	public abstract HttpEntity<? extends Object> logout(Principal principal);
	public abstract HttpEntity<? extends Object> findByName(String name);
	public abstract HttpEntity<? extends Object> verifyAccount(String username);
	public abstract HttpEntity<? extends Object> resetPasswordUser(String password, int validateMessage, String username);
	public abstract HttpEntity<? extends Object> blockedUser(String username);
	public abstract HttpEntity<? extends Object> suspendedUser(String username);
}
