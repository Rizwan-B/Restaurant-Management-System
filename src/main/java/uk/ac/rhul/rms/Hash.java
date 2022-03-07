package uk.ac.rhul.rms;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hash {

  private String password;
  private String username;
  private int length;

  public Hash(String username, String password) {
    this.username = username;
    this.password = password;
    this.length = 512;
  }

  private String makeHash(String value, String salt, int iterations, int length) throws NoSuchAlgorithmException, InvalidKeySpecException{
    SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
    char[] valueArray = value.toCharArray();
    byte[] saltArray = salt.getBytes();
    PBEKeySpec spec = new PBEKeySpec(valueArray, saltArray, iterations, this.length);
    SecretKey key = skf.generateSecret(spec);
    return Hex.encodeHexString(key.getEncoded());
  }

  public String getHashPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
    String salt = "S4YN0TiNG";
    String hashedPassword = makeHash(this.password, salt, 5000, this.length);
    return makeHash(hashedPassword + this.username, salt, 5000, this.length);
  }

  public String getSessionID() throws NoSuchAlgorithmException, InvalidKeySpecException {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMuuuuHHmmss");
    LocalDateTime now = LocalDateTime.now();
    return makeHash(dtf.format(now), " ", 10000, this.length);
  }


}
