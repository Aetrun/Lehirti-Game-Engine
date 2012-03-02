package org.lehirit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {
  public static void copyFileTODO(final File sourceFile, final File destFile) {
    try {
      copyFile(sourceFile, destFile);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static void copyFile(final File sourceFile, final File destFile) throws IOException {
    if (!destFile.exists()) {
      destFile.createNewFile();
    }
    
    FileChannel source = null;
    FileChannel destination = null;
    
    try {
      source = new FileInputStream(sourceFile).getChannel();
      destination = new FileOutputStream(destFile).getChannel();
      destination.transferFrom(source, 0, source.size());
    } finally {
      if (source != null) {
        source.close();
      }
      if (destination != null) {
        destination.close();
      }
    }
  }
}
