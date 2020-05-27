package boostSoft.boostTest.data;

public enum UserStatus {
	BLOCKED("BLOCKED"),
	SIGNEDIN("SIGNEDIN"),
	SIGNEDOUT("SIGNEDOUT"),
	VALID("VALID"),
	DELETED("DELETED");
	
	
	private String statut;

	private UserStatus(String statut) {
		this.statut = statut;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}
	
	

}
