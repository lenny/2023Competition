package com.team871.config;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj2.command.CommandBase;

public interface IGyro extends Sendable {

  public CommandBase resetGyroCommand();

  public double getYaw();
  /**
   * @return the rotation around the Y axis using NEU (north east up) orientation. -180 to 180
   */
  public double getPitch();
}
