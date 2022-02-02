package uk.ac.rhul.rms;

/**
 * A password request to be hashed and checked for permissions with the SQL database.
 *
 * @author Lucas Kimber
 *
 */
public class AccessRequest {
  private int accessToken;

  /**
   * Takes a username and password in preparation for giving a user access
   * to the system.  
   *
   * @param username The username for the prospective login.
   * @param password The password for the prospective login.
   */
  public AccessRequest(String username, String password) {
    this.accessToken = username.hashCode() + password.hashCode();
  }

  /**
   * Returns the composite hash of the username and password.
   *
   * @return int The access token to be used when trying to gain access to the database.
   */
  public int getAccessToken() {
    return this.accessToken;
  }
}
