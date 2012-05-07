package mealplanner;

import java.security.*;

public class SHAHash {

  static String salt = "cs32-MeAlPl@nNeR-project";

  public static String getHash(String input) {

    input += salt;

    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("SHA-256");
    } catch(NoSuchAlgorithmException nsae) {
      System.err.println("Attempting to hash password... SHA-256 does not exist.");
      System.exit(1);
    }
    md.update(input.getBytes());
    byte data[] = md.digest();

    return new java.math.BigInteger(1, data).toString();
  }
}
