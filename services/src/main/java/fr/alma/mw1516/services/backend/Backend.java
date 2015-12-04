package fr.alma.mw1516.services.backend;

import java.util.Map;


import fr.alma.mw1516.api.backend.IBackend;
import fr.alma.mw1516.api.backend.IUser;
import fr.alma.mw1516.services.backend.util.Log;
import fr.alma.mw1516.services.backend.util.MapDB;

public class Backend implements IBackend {
	
	/**
	 * Communication avec le gestionnaire d'identification
	 */
	private AuthClient authClient;
	/**
	 * Communication avec MapDB
	 */
	private MapDB<String, Double> db;
	
	private Map<String, Double> produits;
	
	public Backend(String authUrl) {
		this.authClient = new AuthClient(authUrl);
		db = new MapDB<>("soldes", "soldes");
		produits.put("cafe", .50);
		produits.put("the", .60);
		produits.put("chocolat", .70);
	}

	@Override
	public void bonjour() {
		// TODO Auto-generated method stub

	}

	@Override
	public IUser callback(String token) throws Exception {
		IUser user = null;
		user = authClient.token(token);
		//Ajouter l'user avec un solde de 0 si pas présent.
		if (db.get(user.getId()) == null) {
			System.out.println("Nouvel utilisateur : "+user);
			db.put(user.getId(), 0.);
		}
		return user;
	}

	@Override
	public double solde(String uid) throws Exception {
		return db.get(uid);
	}

	@Override
	public void credit(String uid, double somme) throws Exception {
		if (somme <= 0) {
			throw new Exception("La somme doit être > 0");
		}
		double soldeActuel = db.get(uid);
		db.update(uid, soldeActuel + somme);
		Log.getInstance().pushRefill(uid, somme, soldeActuel);
	}

	@Override
	public boolean acheter(String uid, String nomProduit) throws Exception {
		Double prixProd = produits.get(nomProduit);
		if (prixProd == null) {
			throw new Exception("Le produit n'existe pas");
		}
		double solde = db.get(uid);
		if (prixProd > solde) {
			return false;
		}
		//débiter le solde
		db.update(uid, solde - prixProd);
		Log.getInstance().pushDrink(uid, nomProduit, prixProd, solde);
		return true;
	}

}
