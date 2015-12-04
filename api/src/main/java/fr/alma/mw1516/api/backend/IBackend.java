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
	public void callback(String token) throws Exception;
	
	/**
	 * Consulter le solde de l'utilisateur actuel.
	 * @return Solde de l'utilisateur.
	 * @throws Exception 
	 */
	public double solde() throws Exception;
	
	/**
	 * Créditer le solde de l'utilisateur actuel.
	 * @param somme Montant à ajouter au solde
	 * @throws Exception 
	 */
	public void credit(double somme) throws Exception;
	
	/**
	 * Acheter une boisson.
	 * Débite le solde du prix de la boisson, si possible.
	 * @param nomProduit Nom de la boisson.
	 * @return true si le paiement a réussi, false si le solde est insuffisant.
	 * @throws Exception 
	 */
	public boolean acheter(String nomProduit) throws Exception;
}
