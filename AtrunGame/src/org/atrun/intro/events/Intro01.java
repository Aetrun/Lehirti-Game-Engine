package org.atrun.intro.events;

import org.atrun.intro.IntroImage;
import org.lehirti.events.EventNode;
import org.lehirti.gui.Key;

public class Intro01 extends EventNode {
  @Override
  protected void doEvent() {
    setText("Hallo");
    setBackgroundImage(IntroImage.INTRO_01);
    setImage(IntroImage.INTRO_02);
    
    setInputOption(Key.NEXT, new Intro02());
  }
}
