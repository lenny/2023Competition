package com.team871.config;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj2.command.CommandBase;

public interface DistanceEncoder extends Sendable {


  /**
   * @return the distance of the encoder in inches
   */
  double getDistance();

  void reset();
}
