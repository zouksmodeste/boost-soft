package boostSoft.boostTest.data;

public enum ProductStatus {

	DISPONIBLE("DISPONIBLE"),
	EN_COURS_ATTRIBUTION("EN_COURS_ATTRIBUTION"),
	EN_COURS_DE_REMPLACEMENT("EN_COURS_DE_REMPLACEMENT"),
	PRET("PRET"),
	RESERVE("RESERVE"),
	DELETED("DELETED"),
	INSUFFISANT("INSUFFISANT"),
	VENDU("VENDU");
	
	private String statut;

	private ProductStatus(String statut) {
		this.statut = statut;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}
	
}
