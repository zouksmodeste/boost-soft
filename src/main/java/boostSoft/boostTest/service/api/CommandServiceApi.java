package boostSoft.boostTest.service.api;

import java.security.Principal;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import boostSoft.boostTest.data.Command;

@Service
public interface CommandServiceApi {

	public abstract HttpEntity<? extends Object> createCommand(Command command, Principal principal);
	public abstract HttpEntity<? extends Object> allUserCommand(Long customerPhone, Principal principal);
	public abstract HttpEntity<? extends Object> findByOrderByDateCreationDesc();
	public abstract HttpEntity<? extends Object> findByOrderByDateCreationAsc();
	public abstract HttpEntity<? extends Object> findByValidator(String validator, Principal principal);
	public abstract HttpEntity<? extends Object> updateCommand(Command command, Principal principal);
	public abstract HttpEntity<? extends Object> findAll(Principal principal);
	public abstract HttpEntity<? extends Object> lastCommand(Principal principal);
}
