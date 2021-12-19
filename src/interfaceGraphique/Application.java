package interfaceGraphique;

public class Application {
	
	public static void main(String[] args) {
		System.out.println("ProjetS5");
	}
	
	/**
	 * <pre>
	 *           1..1          1..1
	 * Application ------------------------> InterfaceUtilisateur
	 *           &lt;       interfaceUtilisateur
	 * </pre>
	 */
	private InterfaceUtilisateur interfaceUtilisateur;

	public void setInterfaceUtilisateur(InterfaceUtilisateur value) {
		this.interfaceUtilisateur = value;
	}

	public InterfaceUtilisateur getInterfaceUtilisateur() {
		return this.interfaceUtilisateur;
	}

	/**
	 * <pre>
	 *           1..1          1..1
	 * Application ------------------------> InterfaceServeur
	 *           &lt;       interfaceServeur
	 * </pre>
	 */
	private InterfaceServeur interfaceServeur;

	public void setInterfaceServeur(InterfaceServeur value) {
		this.interfaceServeur = value;
	}

	public InterfaceServeur getInterfaceServeur() {
		return this.interfaceServeur;
	}

	public void lancer() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}