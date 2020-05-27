package boostSoft.boostTest.data;

public enum CommandStatus {

	TERMINE("TERMINE"),
	CANCELED("CANCELED");
	
	private String statut;

	private CommandStatus(String statut) {
		this.statut = statut;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}
	
	

}
