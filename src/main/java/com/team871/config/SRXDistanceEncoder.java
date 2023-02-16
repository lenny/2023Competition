package com.team871.config;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.util.sendable.SendableBuilder;

public class SRXDistanceEncoder implements DistanceEncoder {

  private final TalonSRX Talon;
  private double inchesPerTick;

  public SRXDistanceEncoder(TalonSRX talonSRX, double inchesPerTick) {

    this.Talon = talonSRX;
    this.inchesPerTick = inchesPerTick;
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.addDoubleProperty("distance", this::getDistance, null);
  }

  @Override
  public double getDistance() {
    return Talon.getSelectedSensorPosition() * inchesPerTick;
  }
}
