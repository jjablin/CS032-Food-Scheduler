package food;

import java.io.*;
import java.sql.*;
import java.util.*;
import org.h2.jdbcx.*;

public class FoodDatabase {
  String _fileURL;
  Connection _conn;

  public FoodDatabase(String fileURL) {
    _fileURL = fileURL;
    String url = "jdbc:h2:" + _fileURL;
    try {
      DriverManager.registerDriver(new org.h2.Driver());
      _conn = DriverManager.getConnection(url, "sa", "sa");
    } catch(SQLException sqle) {
      throw new IllegalStateException("Failed to initialize database: " +
                                      sqle.getMessage());
    }
  }

  public void close() {
    try {
      _conn.close();
    } catch(SQLException sqle) {
      throw new IllegalStateException("Failed to close database connection: " +
                                      sqle.getMessage());
    }
  }

  public void clear() {
    StringBuilder cmd = new StringBuilder();
    cmd.append("drop all objects delete files");
    executeCommand(cmd.toString());
  }

  public void createUsersTable() {
    ArrayList< DatabaseVariable > variables = new ArrayList< DatabaseVariable >();
    variables.add(new DatabaseVariable(DatabaseType.STRING, "username"));
    variables.add(new DatabaseVariable(DatabaseType.SERIALIZABLE, "account"));
    createTable("users", variables);
  }

  public boolean addUser(UserAccount account) {
    int result;
    StringBuilder cmd = new StringBuilder();
    cmd.append("insert into ");
    cmd.append("users(username, account) values(?, ?)");
    PreparedStatement pstmt = null;
    try {
      pstmt = _conn.prepareStatement(cmd.toString());
      pstmt.setString(1, account.getUsername());

      // Serialize UserAccount object.
      Pack pack = new Pack(account);
      pstmt.setBinaryStream(2, pack.getStream(), pack.getLength());
      result = pstmt.executeUpdate();
      pstmt.close();
    } catch(SQLException sqle) {
      throw new IllegalStateException("Problems executing pstmt: " +
                                      sqle.getMessage());
    } finally {
      try {
        pstmt.close();
      } catch(Throwable ignore) {
      }
    }
    return result > 0;
  }

  public Account getUser(UserAccount account) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("select account from users where username='");
    cmd.append(account.getUsername());
    cmd.append("'");

    try {
      Statement stmt = _conn.createStatement();
      ResultSet resultSet = stmt.executeQuery(cmd.toString());

      if(!resultSet.next())
        return new NullAccount();

      UserAccount result = (UserAccount)resultSet.getObject(1);

      // Check if password is correct.
      return (result.getPassword().equals(account.getPassword())) ?
        result : new NullAccount();
    } catch(SQLException sqle) {
      throw new IllegalStateException("Failed attempting to query database.");
    }
  }

  public boolean updateUser(UserAccount account) {
    removeUser(account);
    return addUser(account);
  }

  public boolean removeUser(UserAccount account) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("delete from users where username='");
    cmd.append(account.getUsername());
    cmd.append("'");
    return executeCommand(cmd.toString());
  }

  public void createTable(String tablename, List< DatabaseVariable > variables) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("create table if not exists ");
    cmd.append(tablename);
    cmd.append("(");
    ListIterator< DatabaseVariable > it = variables.listIterator();
    while(it.hasNext()) {
      cmd.append(it.next().toString());
      cmd.append(", ");
    }
    cmd.append(")");
    executeCommand(cmd.toString());
  }

  public void dropTable(String tablename) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("drop table ");
    cmd.append(tablename);
    executeCommand(cmd.toString());
  }

  public void writeUsersCSV(String filename) {
    writeCSV("users", filename);
  }

  // FROM tablename, TO filename
  void writeCSV(String tablename, String filename) {
    StringBuilder cmd = new StringBuilder();
    cmd.append("call csvwrite('");
    cmd.append(filename);
    cmd.append("', ");
    cmd.append("'select * from ");
    cmd.append(tablename);
    cmd.append("')");
    executeCommand(cmd.toString());
  }

  boolean executeCommand(String cmd) {
    boolean result;
    Statement stmt = null;
    try {
      stmt = _conn.createStatement();
      result = stmt.execute(cmd);
      stmt.close();
    } catch(SQLException sqle) {
      throw new IllegalStateException("Problems executing stmt: " +
                                      sqle.getMessage());
    } finally {
      try {
        stmt.close();
      } catch(Throwable ignore) {
      }
    }
    return result;
  }
}