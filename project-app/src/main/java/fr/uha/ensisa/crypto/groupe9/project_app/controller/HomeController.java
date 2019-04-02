package fr.uha.ensisa.crypto.groupe9.project_app.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.uha.ensisa.crypto.groupe9.model.CryptedArray;
import fr.uha.ensisa.crypto.groupe9.model.dao.DaoFactory;

@Controller
public class HomeController {
	@Autowired
	public DaoFactory daoFactory;

	@RequestMapping(value="")
	public ModelAndView home(@RequestParam(required=true) long cryptedArrayId, 
			@RequestParam(required=false, defaultValue="") String action) throws Exception {
		ModelAndView ret = new ModelAndView("home");
		int width = 5;
		int height = 5;
		String[][] arrayS = new String[height][width];
		CryptedArray cryptedArr;
		
		if (cryptedArrayId == 0) {
			double[][] array = new double[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					array[i][j] = Double.MIN_VALUE + (Double.MAX_VALUE - Double.MIN_VALUE) * new Random().nextDouble();
				}
			}
			
			byte[] aeskey = CryptedArray.randomAesKey();
			cryptedArr = new CryptedArray(array, aeskey);
			
			long id = daoFactory.getCryptedArrayDao().count() + 1;
			while (daoFactory.getCryptedArrayDao().find(id) != null) id++;
			
			cryptedArr.setId(id);
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					arrayS[i][j] = cryptedArr.decrypt(aeskey, i, j) + "";
				}
			}
			
			daoFactory.getCryptedArrayDao().persist(cryptedArr);
			ret.addObject("arrayId", cryptedArr.getId());
			ret.addObject("height", height);
			ret.addObject("width", width);
			ret.addObject("first", arrayS);
			ret.addObject("key", new String(aeskey));
		}
		else {
			cryptedArr = daoFactory.getCryptedArrayDao().find(cryptedArrayId);
			byte[] aesKey = cryptedArr.aeskey;
			if (action.compareTo("encrypt") == 0) {
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						arrayS[i][j] = CryptedArray.toDouble(cryptedArr.get(i, j)) + "";
					}
				}
			}
			else if (action.compareTo("decrypt") == 0) {
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						arrayS[i][j] = cryptedArr.decrypt(aesKey, i, j) + "";
					}
				}
			}
			
			ret.addObject("arrayId", cryptedArr.getId());
			ret.addObject("height", height);
			ret.addObject("width", width);
			ret.addObject("first", arrayS);
			ret.addObject("key", new String(aesKey));
		}
		return ret;
	}
}
