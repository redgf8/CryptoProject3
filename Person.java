import java.util.Random;

/**
 * Person Class used to test public-key crypto-system, RSA
 * @author Brandon
 */
public class Person extends java.lang.Object {
	private long m; //public modulo m = (p*q)
	private long n; //public modulo n = (p-1)*(q-1)
		private long p; //random Prime number
		private long q; //random Prime number
	private long e; //public encryption exponent e
	private long d; //private decryption exponent d
	
	private Random rand;
	
	/**
	 * Constructor method to generate private and public keys.
	 * @author Brandon Pugh
	 * @throws Exception 
	 */
	public Person() throws Exception{
		//Public Key
		rand = new Random();
		p = RSA.randPrime(0, 100, rand);
		q = RSA.randPrime(0, 100, rand);
		m = p*q;
		n = (p-1)*(q-1);
		e = RSA.randPrime(0, 100, rand);
		
		//Private Key
		d = RSA.inverse(e, m);
	}
	
	/**
	 * @author Brandon Pugh
	 * @return 'm' The public modulo
	 */
	public long getM() {
		return m;
	}
	
	/**
	 * @author Brandon Pugh
	 * @return 'e' The public encryption exponent
	 */
	public long getE() {
		return e;
	}
	
	/**
	 * @author Brandon Pugh
	 * @param msg The message to be encrypted
	 * @param recipient The person that the message is intended for
	 * @return
	 */
	public long[] encryptTo(String msg, Person recipient){
		long[] cipher = new long[msg.length()/2];
		long txt;
		int counter = 0;
		for(int i = 0; i < msg.length(); i = i++) {
			txt = RSA.modPower(RSA.toLong(msg, i), recipient.getE(), recipient.getM());
			cipher[counter] = txt;
			counter++;
		}
		return cipher;
	}
	
	/**
	 * @author Brandon Pugh
	 * @param message The encrypted message longs to be decrypted back to chars
	 * @return
	 */
	public String decrypt (long[] message) {
		String msg = "";
		for(int i = 0; i < message.length; i++) {
			message[i] = RSA.modPower(message[i], d, n);
			msg = msg + RSA.longTo2Chars(message[i]);
		}
		return msg;
	}
}
