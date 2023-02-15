package com.team871.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.Supplier;

public class Shoulder extends SubsystemBase {
  private final MotorController shoulderMotor;

  public Shoulder(final MotorController shoulder) {
    this.shoulderMotor = shoulder;
  }

  public void moveShoulderPitch(final double output) {
    shoulderMotor.set(output);
  }

  public CommandBase moveShoulderPitchCommand(final double output) {
    return run(() -> moveShoulderPitch(output));
  }

  public void setdefaultCommand(Supplier<Double> shoulderPitch) {
    setDefaultCommand(
        run(
            () -> {
              moveShoulderPitch(shoulderPitch.get());
            }));
  }
}
