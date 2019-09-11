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
    public Person(){
        //Public Key
        rand = new Random();
        p = RSA.randPrime(256, 32768, rand);
        while((q = RSA.randPrime(256, 32768, rand))==p);
        m = p*q;
        n = (p-1)*(q-1);
        e = RSA.relPrime(n, rand);

        //Private Key
        d = RSA.inverse(e, n);
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
        if(msg.length()%2!=0) {
            msg+=(char)0;
        }
        long[] cipher = new long[msg.length()/2];
        long txt;
        int counter = 0;
        for(int i = 0; i < msg.length()/2; i++) {
            txt = RSA.modPower(RSA.toLong(msg, i*2), recipient.getE(), recipient.getM());
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
        String msg = new String();
        for(int i = 0; i < message.length; i++) {
            message[i] = RSA.modPower(message[i], d, m);
            msg = msg + RSA.longTo2Chars(message[i]);
        }
        return msg;
    }



}