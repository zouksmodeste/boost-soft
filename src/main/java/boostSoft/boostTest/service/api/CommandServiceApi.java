package boostSoft.boostTest.service.api;

import java.security.Principal;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import boostSoft.boostTest.data.Command;

@Service
public interface CommandServiceApi {

	public abstract HttpEntity<? extends Object> createCommand(Command command, Principal principal);
	public abstract HttpEntity<? extends Object> confirmCommand(Long commandId);
	public abstract HttpEntity<? extends Object> cancelCommand(Long commandId);
	public abstract HttpEntity<? extends Object> deleverCommand(Long commandId);
	public abstract HttpEntity<? extends Object> allCancelCommand();
	public abstract HttpEntity<? extends Object> allConfirmCommand();
	public abstract HttpEntity<? extends Object> alldeleverCommand();
	public abstract HttpEntity<? extends Object> allUserCommand(Long customerPhone);
	public abstract HttpEntity<? extends Object> findByOrderByDateCreationDesc();
	public abstract HttpEntity<? extends Object> findByOrderByDateCreationAsc();
	public abstract HttpEntity<? extends Object> findByValidator(String validator);
	public abstract HttpEntity<? extends Object> updateCommand(Command command);
}
