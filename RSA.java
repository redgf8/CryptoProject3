import java.util.Arrays;

public class RSA {

    /**
     * Find the multiplicative inverse of a long int, mod m
     * @param e The number to find the multiplicative inverse of (modulo m).
     * @param m The m with which to find the multiplicative inverse with respect to.
     * @return The inverse of e, mod m. Uses the extended Euclidean Algorithm. Returns -1 if DNE.
     * @author Daniel Haluszka
     */
    public static long inverse(long e, long m) throws IllegalArgumentException {

        //TODO: change implementation to stack?
        //TODO: implement functionality for negative e?

        //check for invalid input
        if (m < 1) {

            throw new IllegalArgumentException("Given value for m (" + m + ") is not valid: modulo must be > 0");

        }

        if (e < 1) {

            throw new IllegalArgumentException("Given value for e (" + e + ") is not valid: number must be > 0");

        }

        //check for simple case
        if (m == 1) {

            return 0;

        }


        //inverse only exists if e and m are relatively prime
        if (gcd(e, m) != 1) {

            return -1;

        }

        //only ever need to reference two spaces back at most
        long[] r = new long[3];
        long[] q = new long[3];
        long[] u = new long[3];
        long[] v = new long[3];

        //setup
        r[0] = m;
        r[1] = e;
        r[2] = (m % e);
        q[2] = (m / e);
        u[1] = 1;
        v[0] = 1;

        //repeated steps, loop runs until remainder is one
        int position = 2; //used for backtracking from the current index without rearranging array each time by using mod
        int positionMod = 2; //keeps track of actual index of current values
        while (r[positionMod] != 1) {

            //set u and v values for this round
            u[positionMod] = (u[(position-2)%3] - (q[position%3] * u[(position-1)%3]));
            v[positionMod] = (v[(position-2)%3] - (q[position%3] * v[(position-1)%3]));

            //advance position and calculate r and q for next round
            position++;
            positionMod = position % 3;

            r[positionMod] = (r[(position-2)%3] % r[(position-1)%3]);
            q[positionMod] = (r[(position-2)%3] / r[(position-1)%3]);

        }

        //perform u and v calculations one more time after remainder is 1 to get result
        u[positionMod] = (u[(position-2)%3] - (q[position%3] * u[(position-1)%3]));
        v[positionMod] = (v[(position-2)%3] - (q[position%3] * v[(position-1)%3]));

        //make sure the result is within the modular limits
        u[positionMod] = trueMod(u[positionMod], m);

        return u[positionMod];

    }

    /**
     * Raise a number, b, to a power, p, modulo m
     * @param b The number to be raised to the power pow (modulo m).
     * @param p The power to raise num by (modulo m).
     * @param m The mod to work in when performing this calculation.
     * @return b^p mod m
     * @author Daniel Haluszka
     */
    public static long modPower(long b, long p, long m) throws IllegalArgumentException {

        //TODO: check for overflow?

        //check for invalid input
        if (p < 0) {

            throw new IllegalArgumentException("Given value for p (" + p + ") is not valid: p must be positive");

        }

        //check for simple cases
        if (p == 0) {

            return 1;

        }

        if (p == 1) {

            return b%m;

        }

        long result = b;

        //multiply b by itself p times, reducing mod m at each step
        for (int i = 0; i <= p; i++) {

            result *= b;
            result = (result % m);

        }

        return result;

    }

    /**
     * Find the greatest common denominator of two numbers.
     * @param a First number to find gcd of with b
     * @param b Second number to find gcd of with a
     * @return The greatest common denominator of parameters a and b. Uses the Euclidean algorithm.
     * @author Daniel Haluszka
     */
    private static long gcd(long a, long b) {

        //find remainder
        long r = trueMod(a, b);

        //if remainder = 0, b is gcd
        if (r == 0) {

            return b;

        } else {

            //otherwise recur on b and remainder
            return gcd(b, r);

        }

    }

    /**
     * Adds functionality in modulo for negative numbers.
     * @param e The modulo operand
     * @param m The modulo
     * @return e (mod m)
     * @author Daniel Haluszka
     */
    private static long trueMod(long e, long m) {

        long result = e;

        //check for negative number
        while (result < 0) {

            //add modulo until positive
            result += m;

        }

        //perform normal modulo
        if (result > m) {

            result = (result % m);

        }

        return result;

    }
     /***
     * Display an array of longs on stdout
     * @param cipher cipher code to be displayed
     * @author Jamie Walder
     */
    public static void show(long[] cipher) {
    	System.out.println(Arrays.toString(cipher));
    }
    /***
     * Find a random prime number
     * @author Jamie Walder
     * @param m lower range of prime
     * @param n uper range of prime
     * @param rand a random prime number
     * @return A random prime in the range m..n, using rand to generate the number
     * @throws Exception an exception thrown if the range does not contain a prime number
     */
    public static long randPrime(int m,int n,java.util.Random rand) throws Exception {
    	if(m>n) {
    		throw new IllegalArgumentException("m must be less than n");
    	}
    	int original=rand.nextInt(n-m)+m;
    	//takes care of even 
    	if(original%2==0) {
    		original+=1;
    	}
    	int go=0;
    	for(int num=original;go<2;num=(num+2)) {
    		if(num>n) {
    			num=m%2==0?m+1:m;
    		}
    		if(num==original) {
    			go++;
    		}
    		//an unfortunate consequence of jumping all the evens
    		if(num==3) {
    			return num;
    		}
    		for(int i=3;i<num;i+=2) {
    			if(num%i==0) {
    				break;
    			}
    			else if(i==num-2){
    				return num;
    			}
    		}
    		
    	}
    	throw new Exception("No Random in given Range");
    }
    /***
     * Find a random number relatively prime to a given long int
     * @author Jamie Walder
     * @param n first number 
     * @param rand a random number that is relatively prime to n
     * @return a random number relatively prime to n
     */
    public static long relPrime(long n,java.util.Random rand) {
    	long num=rand.nextLong();
    	if(num<0) {
    		num*=-1;
    	}
    	num%=n;
    	if(num==0) {
    		num++;
    	}
    	
    	while(gcd(n,num)>1) {
    		num+=1%n;
    	}
    	return num;
    }
    /***
     * @author Jamie Walder
     * Convert two numeric chars to long int
     * @param msg the message we need the number from
     * @param p the position to create the int from
     * @return the two digit number beginning at position p of msg as a long int.
     */
    public static long toLong(java.lang.String msg,int p) {
        if(msg.length()%2!=0) {
    		throw new IllegalArgumentException("incoming message should be even in length.");
    	}
    	long temp=(int)msg.toCharArray()[p];
    	temp=temp<<32;
    	temp+=(int)msg.toCharArray()[p+1];
    	return temp;
    }
    /***
     * Convert a long to 2 chars
     * @author Jamie Walder
     * @param x 
     * @return The string made up two numeric digits representing x
     */
    public static java.lang.String longTo2Chars(long x){
    	String temp="";
    	temp+=(char)(x>>32);
    	temp+=(char)((int)x);
    	return temp;
    }

}
