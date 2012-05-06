package tests;

import mealplanner.*;
import java.io.*;
import junit.framework.*;
import java.util.*;

public class NullAccountTest extends TestCase {

  public void testNullAccount() {
    NullAccount nullAccount = new NullAccount();
    assertFalse(nullAccount.isValid());
  }
}
