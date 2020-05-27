package boostSoft.boostTest.service.api;

import java.security.Principal;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import boostSoft.boostTest.data.User;

@Service
public interface UserServiceApi {

	public abstract HttpEntity<? extends Object> create(User user);
	public abstract HttpEntity<? extends Object> findAll(Principal principal);
	public abstract HttpEntity<? extends Object> delete(String username, Principal principal);
	public abstract HttpEntity<? extends Object> findByUserName(String username, Principal principal);
	public abstract HttpEntity<? extends Object> update(User user, Principal principal);
	public abstract HttpEntity<? extends Object> updateName(int userId, String name,Principal principal);
	public abstract HttpEntity<? extends Object> updateUserName(int userId, String username,Principal principal);
	public abstract HttpEntity<? extends Object> updatePassWord(int userId, String password, Principal principal);
	public abstract HttpEntity<? extends Object> updateMail(int userId, String mail,Principal principal);
	public abstract HttpEntity<? extends Object> updatePhoneNumber(int userId, String phonenumber,Principal principal);
	public abstract HttpEntity<? extends Object> login(Principal principal);
	public abstract HttpEntity<? extends Object> logout(Principal principal);
	public abstract HttpEntity<? extends Object> findByName(String name, Principal principal);
	public abstract HttpEntity<? extends Object> blockedUser(String username, Principal principal);
	public abstract HttpEntity<? extends Object> resetPassword(String username,String newPassword,Principal principal);
}
