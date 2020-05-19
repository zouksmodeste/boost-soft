package boostSoft.boostTest.utils;

public enum RoleEnum {
	ROLE_CUSTOMER("ROLE_CUSTOMER"),
	ROLE_ADMIN("ROLE_ADMIN");

	
	private String role;

	private RoleEnum() {
	}

	private RoleEnum(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
