package com.team871.config;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.util.sendable.SendableBuilder;

public class SRXAnalogEncoderTalonSRX implements PitchEncoder {

  private final WPI_TalonSRX talon;
  private double zeroOffset;
  private double degreesPerVolt;

  public SRXAnalogEncoderTalonSRX(WPI_TalonSRX talonSRX, double zeroOffset, double degreesPerVolt) {

    this.talon = talonSRX;
    this.zeroOffset = zeroOffset;
    this.degreesPerVolt = degreesPerVolt;
  }
  // this is a test method
  public double calculateDegrees(double rawInput) {
    return (rawInput - zeroOffset) * degreesPerVolt;
  }

  @Override
  public double getPitch() {
    return calculateDegrees(talon.getSelectedSensorPosition());
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.addDoubleProperty("pitch", this::getPitch, null);
  }

  @Override
  public void reset() {
    talon.getSensorCollection().setQuadraturePosition(0, 0);
  }
}
