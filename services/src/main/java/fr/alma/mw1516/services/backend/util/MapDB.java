package fr.alma.mw1516.services.backend.util;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class MapDB<K, V> {
	
	private DB db;
	private String filename, collectionName;
	private ConcurrentNavigableMap<K,V> map;

	public MapDB(String filename, String collectionName) {
		this.filename = filename;
		this.collectionName = collectionName;
	}
	
	public void put(K key, V value){
		openDB();
		map.put( key , value);
		db.commit();
		db.close();
	}
	
	public void remove( K key ){
		openDB();
		map.remove(key);
		db.commit();
		db.close();
	}
	
	public void update(K key, V value){
		openDB();
		map.replace(key, value);
		db.commit();
		db.close();
	}
	
	public V get( K key ){
		openDB();
		V result;
		result = map.get(key);
		
		db.commit();
		db.close();
		
		return result;
	}

	private void openDB() {
		DB db = DBMaker.fileDB(new File(filename))
		        .closeOnJvmShutdown()
		        .encryptionEnable("password")
		        .make(); 
		
		map = db.treeMap(collectionName);
	}
	
}
