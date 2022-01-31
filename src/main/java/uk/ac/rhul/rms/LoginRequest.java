package uk.ac.rhul.rms;

/**
 * A password request to be hashed and checked for permissions with the SQL database.
 *
 * @author Lucas Kimber
 *
 */
public class LoginRequest {
  private String username;
  private int password;

  /**
   * Takes a username and password in preparation for giving a user access
   * to the system.  
   *
   * @param username The username for the prospective login.
   * @param password The password for the prospective login.
   */
  public LoginRequest(String username, String password) {
    this.username = username;
    this.password = password.hashCode();
  }
  
  public String getAccessToken() {
    return "";
  }
}
