package org.lehirti;

public enum Key {
  PREVIOUS('r'),
  NEXT('f');
  
  public final char c;
  
  private Key(final char c) {
    this.c = c;
  }
  
  public static Key getByChar(final char c) {
    for (final Key key : values()) {
      if (key.c == c) {
        return key;
      }
    }
    return null;
  }
}
