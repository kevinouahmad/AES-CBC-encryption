package fr.uha.ensisa.crypto.groupe9.model;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptedArray {
	private long id; //for web app only
	private byte[][][] cryptedArr;
	public byte[] IV;
	public byte[] aeskey; //for web app only

	public CryptedArray(double array[][], byte[] key) throws Exception {
		this.aeskey = key; //for web app only
		IV = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(IV);
		int width = array.length;
		int height = array[0].length;
		cryptedArr = new byte[width][height][];
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec ivSpec = new IvParameterSpec(IV);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				cryptedArr[i][j] = CryptedArray.toByteArray(array[i][j]);
				if ((i + j) % 2 == 0) {
					SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
					
					cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
					cryptedArr[i][j] = cipher.doFinal(CryptedArray.toByteArray(array[i][j]));
				} else {
					cryptedArr[i][j] = CryptedArray.toByteArray(array[i][j]);
				}
			}
		}
	}

	public double decrypt(byte[] key, int i, int j) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
			byte[] cipherText = this.cryptedArr[i][j];
			if ((i + j) % 2 == 0) {
				SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				IvParameterSpec ivSpec = new IvParameterSpec(IV);
				cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
				return CryptedArray.toDouble(cipher.doFinal(cipherText));
			}
			return CryptedArray.toDouble(cipherText);
	}

	public static byte[] toByteArray(double value) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putDouble(value);
		return bytes;
	}

	public static double toDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}
	
	public static byte[] randomAesKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);

		// Generate Key
		SecretKey aeskey = keyGenerator.generateKey();
		return aeskey.getEncoded();
	}

	public static void main(String[] args) throws Exception {
		int width = ThreadLocalRandom.current().nextInt(15, 101);
		int height = ThreadLocalRandom.current().nextInt(15, 101);
		double[][] array = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				array[i][j] = Double.MIN_VALUE + (Double.MAX_VALUE - Double.MIN_VALUE) * new Random().nextDouble();
			}
		}
		
		byte[] aeskey = CryptedArray.randomAesKey();
		CryptedArray cryptedArr = new CryptedArray(array, aeskey);

		double percentEncrypted = 0.0;
		double percentDecrypted = 0.0;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (array[i][j] == CryptedArray.toDouble(cryptedArr.cryptedArr[i][j])) {
					percentEncrypted++;
				}
				if (array[i][j] == cryptedArr.decrypt(aeskey, i, j)) {
					percentDecrypted++;
				}
			}
		}

		percentEncrypted /= width * height;
		percentDecrypted /= width * height;

		System.out.println("There is " + percentEncrypted * 100 + "% similarity between initial array and encrypted array");
		System.out.println("There is " + percentDecrypted * 100 + "% similarity between initial array and encrypted then decrypted array");
	}

	public long getId() { //for web app only
		return id;
	}

	public void setId(long id) { //for web app only
		this.id = id;
	}
	
	public byte[] get(int i, int j) { //for web app only
		return this.cryptedArr[i][j];
	}
}
