package fr.uha.ensisa.crypto.groupe9.project.dao.mem;

import fr.uha.ensisa.crypto.groupe9.model.dao.CryptedArrayDao;
import fr.uha.ensisa.crypto.groupe9.model.dao.DaoFactory;

public class DaoFactoryMem implements DaoFactory {
	public final CryptedArrayDao cryptedArrayDao = new CryptedArrayDaoMem();
	
	public CryptedArrayDao getCryptedArrayDao() {
		return cryptedArrayDao;
	}

}
