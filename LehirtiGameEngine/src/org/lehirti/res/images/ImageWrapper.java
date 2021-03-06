package org.lehirti.res.images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.lehirti.state.StateObject;
import org.lehirti.util.PathFinder;

/**
 * Collection of all image alternatives representing one ImageKey
 */
public final class ImageWrapper {
  private final ImageKey key;
  private final List<ImageProxy> proxies;
  private ImageProxy image;
  private int currentlyDisplayedImageNr = 0;
  
  public ImageWrapper(final ImageKey key) {
    this.key = key;
    this.proxies = new ArrayList<ImageProxy>(5);
    parseAll(PathFinder.getCoreImageProxyFile(key));
    parseAll(PathFinder.getModImageProxyFile(key));
    
    pinRandomImage();
  }
  
  private void parseAll(final File dir) {
    if (!dir.isDirectory()) {
      return;
    }
    final File[] imageProxies = dir.listFiles(new FileFilter() {
      @Override
      public boolean accept(final File pathname) {
        return pathname.getName().endsWith(PathFinder.PROXY_FILENAME_SUFFIX);
      }
    });
    for (final File imageProxyFile : imageProxies) {
      final ImageProxy imageProxy = ImageProxy.getInstance(imageProxyFile);
      if (imageProxy != null) {
        this.proxies.add(imageProxy);
      }
    }
  }
  
  public int getCurrentImageNr() {
    return this.currentlyDisplayedImageNr;
  }
  
  public void pinRandomImage() {
    if (this.proxies.isEmpty()) {
      this.image = new ImageProxy();
    } else {
      this.currentlyDisplayedImageNr = StateObject.DIE.nextInt(this.proxies.size());
      this.image = this.proxies.get(this.currentlyDisplayedImageNr);
    }
  }
  
  public void pinNextImage() {
    if (this.proxies.isEmpty()) {
      return;
    } else {
      this.currentlyDisplayedImageNr++;
      this.currentlyDisplayedImageNr %= this.proxies.size();
      this.image = this.proxies.get(this.currentlyDisplayedImageNr);
    }
  }
  
  public void setPlacement(final Properties placement) {
    this.image.setPlacement(placement);
    this.image.writeProxyFile();
  }
  
  public Properties getPlacement() {
    return this.image.getPlacement();
  }
  
  public BufferedImage getImage() {
    return this.image.getImage();
  }
  
  public int[] calculateCoordinates(final int width, final int height) {
    return this.image.calculateCoordinates(width, height);
  }
  
  /**
   * @param alternativeImageFile
   * @return image added
   */
  public boolean addAlternativeImage(final File alternativeImageFile) {
    final ImageProxy imageProxy = ImageProxy.createNew(PathFinder.getModImageProxyFile(this.key), alternativeImageFile);
    if (imageProxy != null) {
      this.proxies.add(imageProxy);
      this.currentlyDisplayedImageNr = this.proxies.size() - 1;
      this.image = imageProxy;
      return true;
    }
    return false;
  }
  
  public ImageKey getKey() {
    return this.key;
  }
  
  @Override
  public int hashCode() {
    return this.key.hashCode();
  }
  
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof ImageWrapper)) {
      return false;
    }
    final ImageWrapper other = (ImageWrapper) o;
    return this.key.equals(other.key);
  }
  
  public String toButtonString() {
    return "<html>" + this.key.getClass().getSimpleName() + "<br/>" + this.key.name() + "</html>";
  }
}
