package com.team871.config;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.util.sendable.SendableBuilder;

public class SRXPitchEncoder implements PitchEncoder {
  public static double calculateDegrees(double totalDegrees) {
    double degrees = totalDegrees % 360;
    return degrees > 180 ? -1 * (360 - degrees) : degrees;
  }

  private final WPI_TalonSRX talon;
  private double degreesPerTick;

  public SRXPitchEncoder(final WPI_TalonSRX talonSRX, final double degreesPerTick) {
    this.talon = talonSRX;
    this.degreesPerTick = degreesPerTick;
  }

  @Override
  public void initSendable(final SendableBuilder builder) {
    builder.addDoubleProperty("pitch", this::getPitch, null);
  }

  @Override
  public double getPitch() {
    return talon.getSelectedSensorPosition() * degreesPerTick;
  }

  @Override
  public void reset() {
    talon.getSensorCollection().setQuadraturePosition(0, 0);
  }
}
