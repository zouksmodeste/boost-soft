package boostSoft.boostTest.data;

public enum CommandType {

	FACTURE("FACTURE");
	
	private String statut;

	private CommandType(String statut) {
		this.statut = statut;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}
	
	
}
