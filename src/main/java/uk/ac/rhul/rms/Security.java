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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hashes the username and password.
 *
 * @author Mohamed Yusuf
 */
public class Security {

  private String password;
  private String username;
  private int length;
  private final String SQL_TYPES = "TABLE, TABLESPACE, PROCEDURE, FUNCTION, TRIGGER, KEY, VIEW, MATERIALIZED VIEW, LIBRARY" +
      "DATABASE LINK, DBLINK, INDEX, CONSTRAINT, TRIGGER, USER, SCHEMA, DATABASE, PLUGGABLE DATABASE, BUCKET, " +
      "CLUSTER, COMMENT, SYNONYM, TYPE, JAVA, SESSION, ROLE, PACKAGE, PACKAGE BODY, OPERATOR" +
      "SEQUENCE, RESTORE POINT, PFILE, CLASS, CURSOR, OBJECT, RULE, USER, DATASET, DATASTORE, " +
      "COLUMN, FIELD, OPERATOR";
  private final String[] SQL_REGEXPS = {
      "(?i)(.*)(\\b)+(OR|AND)(\\s)+(true|false)(\\s)*(.*)",
      "(?i)(.*)(\\b)+(OR|AND)(\\s)+(\\w)(\\s)*(\\=)(\\s)*(\\w)(\\s)*(.*)",
      "(?i)(.*)(\\b)+(OR|AND)(\\s)+(equals|not equals)(\\s)+(true|false)(\\s)*(.*)",
      "(?i)(.*)(\\b)+(OR|AND)(\\s)+([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(\\=)(\\s)*([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(.*)",
      "(?i)(.*)(\\b)+(OR|AND)(\\s)+([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(\\!\\=)(\\s)*([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(.*)",
      "(?i)(.*)(\\b)+(OR|AND)(\\s)+([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(\\<\\>)(\\s)*([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(.*)",
      "(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)(.*)",
      "(?i)(.*)(\\b)+INSERT(\\b)+\\s.*(\\b)+INTO(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+UPDATE(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+DELETE(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+UPSERT(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+SAVEPOINT(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+CALL(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+ROLLBACK(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+KILL(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+DROP(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+CREATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+ALTER(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+TRUNCATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+LOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+UNLOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+RELEASE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
      "(?i)(.*)(\\b)+DESC(\\b)+(\\w)*\\s.*(.*)",
      "(?i)(.*)(\\b)+DESCRIBE(\\b)+(\\w)*\\s.*(.*)",
      "(.*)(/\\*|\\*/|;){1,}(.*)",
      "(.*)(-){2,}(.*)",
  };


  /**
   * Constructor.
   *
   * @param username username.
   * @param password password.
   */
  public Security(String username, String password) {
    this.username = username;
    this.password = password;
    this.length = 512;
  }

  private String makeHash(String value, String salt, int iterations, int length)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
    char[] valueArray = value.toCharArray();
    byte[] saltArray = salt.getBytes();
    PBEKeySpec spec = new PBEKeySpec(valueArray, saltArray, iterations, this.length);
    SecretKey key = skf.generateSecret(spec);
    return Hex.encodeHexString(key.getEncoded());
  }


  /**
   * Returns the hash password.
   *
   * @return the hash password.
   * @throws NoSuchAlgorithmException Invalid algorithm.
   * @throws InvalidKeySpecException Invalid key.
   */
  public String getHashPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
    String salt = "S4YN0TiNG";
    String hashedPassword = makeHash(this.password, salt, 5000, this.length);
    return makeHash(hashedPassword + this.username, salt, 5000, this.length);
  }

  /**
   * Returns session ID.
   *
   * @return Session ID.
   * @throws NoSuchAlgorithmException Invalid algorithm.
   * @throws InvalidKeySpecException Invalid key.
   */
  public String getSessionID() throws NoSuchAlgorithmException, InvalidKeySpecException {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMuuuuHHmmss");
    LocalDateTime now = LocalDateTime.now();
    return makeHash(dtf.format(now), " ", 10000, this.length);
  }


  public boolean isSqlInjectionSafe(String dataString){
    List<Pattern> validationPatterns = buildPatterns(this.SQL_REGEXPS);
    if(dataString.isEmpty()){
      return true;
    }

    for(Pattern pattern : validationPatterns){
      if(matches(pattern, dataString)){
        return false;
      }
    }
    return true;
  }

  private static boolean matches(Pattern pattern, String dataString){
    Matcher matcher = pattern.matcher(dataString);
    return matcher.matches();
  }

  private static List<Pattern> buildPatterns(String[] expressionStrings){
    List<Pattern> patterns = new ArrayList<Pattern>();
    for(String expression : expressionStrings){
      patterns.add(getPattern(expression));
    }
    return patterns;
  }

  private static Pattern getPattern(String regEx){
    return Pattern.compile(regEx, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
  }

}
