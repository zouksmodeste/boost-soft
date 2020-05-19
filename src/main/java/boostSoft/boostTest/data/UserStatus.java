package boostSoft.boostTest.data;

public enum UserStatus {
	BLOCKED("BLOCKED"),
	SIGNEDIN("SIGNEDIN"),
	SIGNEDOUT("SIGNEDOUT"),
	SUSPENDED("SUSPENDED"),
	VALID("VALID"),
	DELETED("DELETED"),
	AWAITINGVALIDATION("AWAITINGVALIDATION");
	
	
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
