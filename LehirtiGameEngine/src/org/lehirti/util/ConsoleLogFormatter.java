package org.lehirti.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ConsoleLogFormatter extends Formatter {
  
  @Override
  public String format(final LogRecord rec) {
    final StringBuffer buf = new StringBuffer(1000);
    
    final Level level = rec.getLevel();
    if (level.intValue() > Level.INFO.intValue()) {
      buf.append(rec.getLevel());
      buf.append(": ");
    }
    buf.append(formatMessage(rec));
    if (rec.getThrown() != null) {
      buf.append(rec.getThrown().toString());
    }
    // buf.append(" ");
    // buf.append(calcDate(rec.getMillis()));
    // buf.append(" <");
    // buf.append(rec.getLoggerName());
    // buf.append(".");
    // buf.append(rec.getSourceMethodName());
    // buf.append(">");
    buf.append("\n");
    return buf.toString();
  }
  
  private String calcDate(final long millisecs) {
    final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final Date resultdate = new Date(millisecs);
    return date_format.format(resultdate);
  }
}
