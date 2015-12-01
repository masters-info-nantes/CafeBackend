package fr.alma.mw1516.api.backend;

/**
 * Contrat du backend
 * @author E15A968H
 *
 */
public interface IBackend {

	/**
	 * Appel d'identification préalable à tout autre appel du backend.
	 */
	public void bonjour();
	
	/**
	 * Demande d'authentification.
	 * Le backend vérifie le token donné auprès du gestionnaire d'identification.
	 * @param token Token généré par le gestionnaire d'identification.
	 */
	public void callback(String token);
	
	/**
	 * Consulter le solde de l'utilisateur actuel.
	 * @return Solde de l'utilisateur.
	 */
	public int solde();
	
	/**
	 * Créditer le solde de l'utilisateur actuel.
	 * @param somme Montant à ajouter au solde
	 */
	public void credit(int somme);
	
	/**
	 * Acheter une boisson.
	 * Débite le solde du prix de la boisson, si possible.
	 * @param nomProduit Nom de la boisson.
	 */
	public void acheter(String nomProduit);
}
