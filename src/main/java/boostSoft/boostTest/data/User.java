package boostSoft.boostTest.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        }
)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	private String name;
	private String subName;
	private String userName;
	private String passWord;
	private String mail;
	private String phoneNumber;
	private UserType userType;
	private String userStatus;
	private Boolean validate;
	private Integer validateMessage;
	private String store;
	
	
	public User() {
		super();
	}


	public User(int userId, String name, String subName, String userName, String passWord, String mail,
			String phoneNumber, UserType userType, String userStatus, Boolean validate, Integer validateMessage) {
		super();
		this.userId = userId;
		this.name = name;
		this.subName = subName;
		this.userName = userName;
		this.passWord = passWord;
		this.mail = mail;
		this.phoneNumber = phoneNumber;
		this.userType = userType;
		this.userStatus = userStatus;
		this.validate = validate;
		this.validateMessage = validateMessage;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSubName() {
		return subName;
	}


	public void setSubName(String subName) {
		this.subName = subName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassWord() {
		return passWord;
	}


	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public UserType getUserType() {
		return userType;
	}


	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	public String getUserStatus() {
		return userStatus;
	}


	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}


	public Boolean getValidate() {
		return validate;
	}


	public void setValidate(Boolean validate) {
		this.validate = validate;
	}


	public Integer getValidateMessage() {
		return validateMessage;
	}


	public void setValidateMessage(Integer validateMessage) {
		this.validateMessage = validateMessage;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((passWord == null) ? 0 : passWord.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((subName == null) ? 0 : subName.hashCode());
		result = prime * result + userId;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userStatus == null) ? 0 : userStatus.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		result = prime * result + ((validate == null) ? 0 : validate.hashCode());
		result = prime * result + ((validateMessage == null) ? 0 : validateMessage.hashCode());
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
		User other = (User) obj;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (passWord == null) {
			if (other.passWord != null)
				return false;
		} else if (!passWord.equals(other.passWord))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (subName == null) {
			if (other.subName != null)
				return false;
		} else if (!subName.equals(other.subName))
			return false;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userStatus == null) {
			if (other.userStatus != null)
				return false;
		} else if (!userStatus.equals(other.userStatus))
			return false;
		if (userType != other.userType)
			return false;
		if (validate == null) {
			if (other.validate != null)
				return false;
		} else if (!validate.equals(other.validate))
			return false;
		if (validateMessage == null) {
			if (other.validateMessage != null)
				return false;
		} else if (!validateMessage.equals(other.validateMessage))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", subName=" + subName + ", userName=" + userName
				+ ", passWord=" + passWord + ", mail=" + mail + ", phoneNumber=" + phoneNumber + ", userType="
				+ userType + ", userStatus=" + userStatus + ", validate=" + validate + ", validateMessage="
				+ validateMessage + "]";
	}
	
	
}
