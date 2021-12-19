package baseDeDonnee;

public class BDD {
	private String login;
	private String url;

	protected void setLogin(String value) {
		this.login = value;
	}

	protected String getLogin() {
		return this.login;
	}

	private String motDePasse;

	protected void setMotDePasse(String value) {
		this.motDePasse = value;
	}

	protected String getMotDePasse() {
		return this.motDePasse;
	}

	protected void setUrl(String value) {
		this.url = value;
	}

	protected String getUrl() {
		return this.url;
	}

	public void BDD(BDD login, String motDePasse, String url) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer seDeconnecter() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public String requete(String requete) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer seConnecter() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}