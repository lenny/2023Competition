package com.team871.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.Supplier;

public class Wrist extends SubsystemBase {
  private final MotorController wristMotor;

  public Wrist(final MotorController wristMotor) {
    this.wristMotor = wristMotor;
  }

  public void moveWristPitch(final double output) {
    this.wristMotor.set(output);
  }

  public void setdefaultCommand(Supplier<Double> wristPitch) {
    setDefaultCommand(run(() -> moveWristPitch(wristPitch.get())));
  }
}
