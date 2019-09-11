public class Driver {

    public static void main(String args[]) throws Exception {
        Person Alice = new Person();
        Person Bob = new Person();

        System.out.println("Alice's Public Mod: " + Alice.getE());
        System.out.println("Bob's Public Mod: " + Bob.getE());

        System.out.println(Bob.encryptTo("Hello", Alice));
        System.out.println(RSA.modPower(234, 6234, 15));

    }

}