package food;

import java.util.*;

// A serializable class for storing relevant user information.
public class UserAccount extends NullAccount {

  private static final long serialVersionUID = 7526471155622776147L;

  // http://en.wikipedia.org/wiki/List_of_food_allergies -
  //  a list of food allergies.
  public enum Allergy {
    CORN, FRUIT, GARLIC, OATS, MILK, PEANUT, FISH,
      SHELLFISH, SOY, TREE_NUT, WHEAT, EGG, MSG,
      SULPHITES
  };

  String _username;
  String _password;
  Set<String> _likes;
  Set<String> _dislikes;
  Set<Allergy> _allergies;

  public UserAccount() {
    super();
    _valid = true;
    _username = "";
    _password = "";
    _likes = new HashSet<String>();
    _dislikes = new HashSet<String>();
    _allergies = new HashSet<Allergy>();
  }

  public UserAccount(String username, String password) {
    super();
    _valid = true;
    _username = username;
    _password = password;
    _likes = new HashSet<String>();
    _dislikes = new HashSet<String>();
    _allergies = new HashSet<Allergy>();
  }

  public UserAccount(String username, String password,
                 Set<String> likes, Set<String> dislikes,
                 Set<Allergy> allergies) {
    super();
    _valid = true;
    _username = username;
    _password = password;
    _likes = likes;
    _dislikes = dislikes;
    _allergies = allergies;
  }

  public void addAllergy(Allergy allergy) {
    _allergies.add(allergy);
  }

  public void addDislike(String dislike) {
    _dislikes.add(dislike);
  }

  public void addLike(String like) {
    _likes.add(like);
  }

  public void clearAllergies() {
    _allergies.clear();
  }

  public void clearDislikes() {
    _dislikes.clear();
  }

  public void clearLikes() {
    _likes.clear();
  }

  public Set getAllergies() {
    return _allergies;
  }

  public Set getDislikes() {
    return _dislikes;
  }

  public Set getLikes() {
    return _likes;
  }

  public String getPassword() {
    return _password;
  }

  public String getUsername() {
    return _username;
  }

  public void removeLike(String like) {
    _likes.remove(like);
  }

  public void removeDislike(String dislike) {
    _dislikes.remove(dislike);
  }

  public void removeAllergy(Allergy allergy) {
    _allergies.remove(allergy);
  }

  public void setUsername(String username) {
    _username = username;
  }

  public void setPassword(String password) {
    _password = password;
  }

  public String toString() {
    return getUsername() + ", " + getPassword();
  }
}
