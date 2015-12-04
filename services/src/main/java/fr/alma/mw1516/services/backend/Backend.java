package fr.alma.mw1516.services.backend;

import java.util.Map;

import javax.jms.MapMessage;

import fr.alma.mw1516.api.backend.IBackend;
import fr.alma.mw1516.api.backend.IUser;
import fr.alma.mw1516.services.backend.util.OperationDB;

public class Backend implements IBackend {
	
	/**
	 * Communication avec le gestionnaire d'identification
	 */
	private AuthClient authClient;
	/**
	 * Communication avec MapDB
	 */
	private OperationDB db;
	
	private IUser currentUser;
	
	private Map<String, Double> produits;
	
	public Backend(String authUrl) {
		this.authClient = new AuthClient(authUrl);
		db = new OperationDB();
		produits.put("cafe", .50);
		produits.put("the", .60);
		produits.put("chocolat", .70);
	}

	@Override
	public void bonjour() {
		// TODO Auto-generated method stub

	}

	@Override
	public void callback(String token) throws Exception {
		IUser user = null;
		user = authClient.token(token);
		//Ajouter l'user avec un solde de 0 si pas présent.
		if (db.SelectDB(user.getId()) == null) {
			System.out.println("Nouvel utilisateur : "+user);
			db.AddDB(user.getId(), 0);
		}
		//pusher jms
		currentUser = user;
	}

	@Override
	public double solde() throws Exception {
		if (currentUser == null) {
			throw new Exception("Utilisateur non connecté");
		}
		return db.SelectDB(currentUser.getId());
	}

	@Override
	public void credit(double somme) throws Exception {
		if (currentUser == null) {
			throw new Exception("Utilisateur non connecté");
		}
		if (somme <= 0) {
			throw new Exception("La somme doit être > 0");
		}
		double soldeActuel = db.SelectDB(currentUser.getId());
		db.AddDB(currentUser.getId(), soldeActuel + somme);
		//pusher jms
	}

	@Override
	public boolean acheter(String nomProduit) throws Exception {
		if (currentUser == null) {
			throw new Exception("Utilisateur non connecté");
		}
		Double prixProd = produits.get(nomProduit);
		if (prixProd == null) {
			throw new Exception("Le produit n'existe pas");
		}
		double solde = db.SelectDB(currentUser.getId());
		if (prixProd > solde) {
			return false;
		}
		//débiter le solde
		db.AddDB(currentUser.getId(), solde - prixProd);
		//pusher jms
		return true;
	}

}
