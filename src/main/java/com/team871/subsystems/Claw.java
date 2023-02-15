package com.team871.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Claw extends SubsystemBase {

  private final MotorController pinchMotor;

  public Claw(
      final MotorController pinchMotor) {
    this.pinchMotor = pinchMotor;
  }

  public void setPinch(final double output) {
    pinchMotor.set(output);
  }
}
