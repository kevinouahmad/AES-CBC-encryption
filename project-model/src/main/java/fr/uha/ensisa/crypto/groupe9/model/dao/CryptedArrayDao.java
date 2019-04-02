package fr.uha.ensisa.crypto.groupe9.model.dao;

import java.util.Collection;

import fr.uha.ensisa.crypto.groupe9.model.CryptedArray;


public interface CryptedArrayDao {
	public void persist(CryptedArray cryptArr);
	public void remove(CryptedArray cryptArr);
	public CryptedArray find(long id);
	public Collection<CryptedArray> findAll();
	public long count();
}
