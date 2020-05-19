package boostSoft.boostTest.data;

public enum UserType {

	CUSTOMER("CUSTOMER"),
	ADMIN("ADMIN");
	
	private String statut;

	private UserType(String statut) {
		this.statut = statut;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}
	
	
}
