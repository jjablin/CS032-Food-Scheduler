

package mealplanner;

import java.io.*;

public class NullAccount implements Account, Serializable {

  private static final long serialVersionUID = 7526471155622776147L;

  boolean _valid;

  public NullAccount() {
    _valid = false;
  }

  public boolean isValid() {
    return _valid;
  }
}