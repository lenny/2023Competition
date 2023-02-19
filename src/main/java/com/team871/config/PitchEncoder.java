package com.team871.config;

import edu.wpi.first.util.sendable.Sendable;

public interface PitchEncoder extends Sendable {

  /**
   * Convert 360 degree based rawDegrees to [-180,180] degrees
   *
   * @param rawDegrees 360 degree based raw degrees
   * @return [180,180] degrees
   */
  public static double toSemiCircleDegrees(double rawDegrees) {
    double degrees = rawDegrees % 360;
    return degrees > 180 ? -1 * (360 - degrees) : degrees;
  }

  /**
   * @return the rotation around the Y axis using NEU (north east up) orientation. -180 to 180
   */
  double getPitch();

  void reset();
}
