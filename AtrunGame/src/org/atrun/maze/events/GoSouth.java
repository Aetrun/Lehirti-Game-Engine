package org.atrun.maze.events;

import org.atrun.maze.state.MazeState;

public class GoSouth extends MazeEvent {
  private static final long serialVersionUID = 1L;
  
  @Override
  protected void doEvent() {
    doEventMain(MazeState.goSouth());
  }
}
