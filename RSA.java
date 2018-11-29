public class RSA {

    /**
     * Find the multiplicative inverse of a long int, mod m
     * @param e The number to find the multiplicative inverse of (modulo m).
     * @param m The m with which to find the multiplicative inverse with respect to.
     * @return The inverse of e, mod m. Uses the extended Euclidean Algorithm. Returns -1 if DNE.
     * @author Daniel Haluszka
     */
    public static long inverse(int e, int m) throws IllegalArgumentException {

        //check for invalid mod input
        if (m < 1) {

            throw new IllegalArgumentException("Given mod (" + m + ") is not valid: mod must be > 0");

        }

        if (e < 1) {

            throw new IllegalArgumentException("Given number (" + e + ") is not valid: number must be > 0");

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
        u[0] = 0;
        u[1] = 1;
        v[0] = 1;
        v[1] = 0;

        //repeated steps
        int position = 2;
        while (r[position%3] != 1) {

            //set u and v values for this round
            u[position%3] = (u[(position-2)%3] - (q[position%3] * u[(position-1)%3]));
            v[position%3] = (v[(position-2)%3] - (q[position%3] * v[(position-1)%3]));

            //advance position and calculate r and q for next round
            position++;

            System.out.println("r: " + r[(position-1)%3]);
            r[position%3] = (r[(position-2)%3] % r[(position-1)%3]);
            r[position%3] = (r[(position-2)%3] / r[(position-1)%3]);

        }

        while (u[2] < 0) {

            u[2] += m;

        }

        if (u[2] > m) {

            u[2] = (u[2] % m);

        }

        return u[2];

    }

    /**
     * Raise a number, b, to a power, p, modulo m
     * @param b The number to be raised to the power pow (modulo m).
     * @param p The power to raise num by (modulo m).
     * @param m The mod to work in when performing this calculation.
     * @return b^p mod m
     * @author Daniel Haluszka
     */
    public static long modPower(int b, int p, int m) {

        return 0;

    }

    /**
     * @param a
     * @param b
     * @return The greatest common denominator of parameters a and b.
     * @author Daniel Haluszka
     */
    private static int gcd(int a, int b) {

        int r = (a%b);

        if (r == 0) {

            return b;

        } else {

            return gcd(b, r);

        }

    }

}
