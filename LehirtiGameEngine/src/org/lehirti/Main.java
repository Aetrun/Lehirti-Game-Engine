package org.lehirti;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import org.lehirti.events.Event;
import org.lehirti.gui.ImageArea;
import org.lehirti.gui.ImageEditor;
import org.lehirti.gui.Key;
import org.lehirti.gui.TextArea;
import org.lehirti.res.images.ImageWrapper;
import org.lehirti.state.StaticInitializer;
import org.lehirti.util.ClassFinder;

public abstract class Main {
  
  public static TextArea TEXT_AREA;
  public static ImageArea IMAGE_AREA;
  
  public static Event nextEvent = null;
  
  public static final Object LAST_KEY_TYPED_LOCK = new Object();
  public static Key LAST_KEY_TYPED;
  
  public static final Random DIE = new Random();
  
  private void createAndShowGUI() {
    
    final JFrame frame = new JFrame(getGameName());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    TEXT_AREA = new TextArea();
    TEXT_AREA.addKeyListener(new KeyListener() {
      @Override
      public void keyPressed(final KeyEvent e) {
      }
      
      @Override
      public void keyReleased(final KeyEvent e) {
      }
      
      @Override
      public void keyTyped(final KeyEvent e) {
        final Key key = Key.getByChar(e.getKeyChar());
        if (key != null) {
          if (key == Key.CTRL_S) {
            editImages();
          } else {
            synchronized (LAST_KEY_TYPED_LOCK) {
              LAST_KEY_TYPED = key;
              LAST_KEY_TYPED_LOCK.notifyAll();
            }
          }
        }
        e.consume();
      }
      
      private void editImages() {
        final List<ImageWrapper> allImages = IMAGE_AREA.getAllImages();
        new ImageEditor(allImages, IMAGE_AREA);
      }
    });
    
    IMAGE_AREA = new ImageArea();
    
    frame.getContentPane().add(IMAGE_AREA, BorderLayout.CENTER);
    frame.getContentPane().add(TEXT_AREA, BorderLayout.EAST);
    
    frame.pack();
    frame.setVisible(true);
  }
  
  protected void engineMain(final String[] args) throws InterruptedException, InvocationTargetException {
    /*
     * load all modules
     */
    new ClassFinder().findSubclasses(StaticInitializer.class.getName());
    
    javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
    
    while (true) {
      nextEvent.execute();
    }
  }
  
  abstract protected String getGameName();
}