package boostSoft.boostTest.data;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Command {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long commandId;
	private Date dateCreation;
	private float totalPrice;
	private Long customerPhone;
	private String status;
	private String validator;
	private CommandType commandType;
	
	@OneToMany(mappedBy = "command", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<ProductCommand> productCommand;

	public Command() {
		super();
	}

	public Command(Long commandId, Date dateCreation, float totalPrice, Long customerPhone, String status,
			String validator, CommandType commandType, List<ProductCommand> productCommand) {
		super();
		this.commandId = commandId;
		this.dateCreation = dateCreation;
		this.totalPrice = totalPrice;
		this.customerPhone = customerPhone;
		this.status = status;
		this.validator = validator;
		this.commandType = commandType;
		this.productCommand = productCommand;
	}

	public Long getCommandId() {
		return commandId;
	}

	public void setCommandId(Long commandId) {
		this.commandId = commandId;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(Long customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}

	public List<ProductCommand> getProductCommand() {
		return productCommand;
	}

	public void setProductCommand(List<ProductCommand> productCommand) {
		this.productCommand = productCommand;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commandId == null) ? 0 : commandId.hashCode());
		result = prime * result + ((commandType == null) ? 0 : commandType.hashCode());
		result = prime * result + ((customerPhone == null) ? 0 : customerPhone.hashCode());
		result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
		result = prime * result + ((productCommand == null) ? 0 : productCommand.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + Float.floatToIntBits(totalPrice);
		result = prime * result + ((validator == null) ? 0 : validator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Command other = (Command) obj;
		if (commandId == null) {
			if (other.commandId != null)
				return false;
		} else if (!commandId.equals(other.commandId))
			return false;
		if (commandType != other.commandType)
			return false;
		if (customerPhone == null) {
			if (other.customerPhone != null)
				return false;
		} else if (!customerPhone.equals(other.customerPhone))
			return false;
		if (dateCreation == null) {
			if (other.dateCreation != null)
				return false;
		} else if (!dateCreation.equals(other.dateCreation))
			return false;
		if (productCommand == null) {
			if (other.productCommand != null)
				return false;
		} else if (!productCommand.equals(other.productCommand))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (Float.floatToIntBits(totalPrice) != Float.floatToIntBits(other.totalPrice))
			return false;
		if (validator == null) {
			if (other.validator != null)
				return false;
		} else if (!validator.equals(other.validator))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Command [commandId=" + commandId + ", dateCreation=" + dateCreation + ", totalPrice=" + totalPrice
				+ ", customerPhone=" + customerPhone + ", status=" + status + ", validator=" + validator
				+ ", commandType=" + commandType + "]";
	}

		
}
