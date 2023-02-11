package com.team871.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Wrist extends SubsystemBase {
  private final MotorController wristMotor;

  public Wrist(final MotorController wristMotor) {
    this.wristMotor = wristMotor;
  }

  public void moveWristPitch(final double output) {
    this.wristMotor.set(output);
  }
}
