package fr.alma.mw1516.services.backend.util;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class OperationDB {

	protected String id;
	protected double solde;
	protected DB db;
	protected ConcurrentNavigableMap<String,Double> map;
	
	public OperationDB(){
		
		super();
	}
	
	public void AddDB(String id , double solde){
		openDB();
		map.put( id , solde);
		db.commit();
		db.close();
	}
	
	public void RemoveDB( String id ){
		openDB();
		map.remove(id);
		db.commit();
		db.close();
	}
	
	public void UpdateDB(String id , double solde){
		openDB();
		map.replace( id , solde );
		db.commit();
		db.close();
	}
	
	public Double SelectDB( String id ){
		openDB();
		Double result;
		result = map.get(id);
		
		db.commit();
		db.close();
		
		return result;
	}

	private void openDB() {
		db = DBMaker.fileDB(new File("testdb"))
		        .closeOnJvmShutdown()
		        .encryptionEnable("password")
		        .make(); 
		
		map = db.treeMap("collectionName");
	}
}
