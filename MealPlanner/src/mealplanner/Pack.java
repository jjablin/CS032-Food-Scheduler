package mealplanner;

import java.io.*;

public class Pack {
  ByteArrayInputStream _stream;
  int _length;

  public Pack(Object object) {
    try {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      ObjectOutputStream objectOutputStream =
        new ObjectOutputStream(byteArrayOutputStream);
      objectOutputStream.writeObject(object);
      byte[] objectAsBytes = byteArrayOutputStream.toByteArray();
      _stream = new ByteArrayInputStream(objectAsBytes);
      _length = objectAsBytes.length;
    } catch(IOException ioe) {
      throw new IllegalStateException("Failed to serialize object. " + ioe.getMessage());
    }
  }

  public int getLength() {
    return _length;
  }

  public ByteArrayInputStream getStream() {
    return _stream;
  }
}
