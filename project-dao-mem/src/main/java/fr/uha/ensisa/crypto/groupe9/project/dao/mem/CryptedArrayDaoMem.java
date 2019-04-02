package fr.uha.ensisa.crypto.groupe9.project.dao.mem;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import fr.uha.ensisa.crypto.groupe9.model.CryptedArray;
import fr.uha.ensisa.crypto.groupe9.model.dao.CryptedArrayDao;

public class CryptedArrayDaoMem implements CryptedArrayDao {
	private final Map<Long, CryptedArray> store = Collections.synchronizedMap(new TreeMap<Long, CryptedArray>());
	
	public void persist(CryptedArray cryptArr) {
		this.store.put(cryptArr.getId(), cryptArr);
	}

	public void remove(CryptedArray cryptArr) {
		this.store.remove(cryptArr.getId());
	}

	public CryptedArray find(long id) {
		return this.store.get(id);
	}

	public Collection<CryptedArray> findAll() {
		return this.store.values();
	}

	public long count() {
		return this.store.size();
	}

}
