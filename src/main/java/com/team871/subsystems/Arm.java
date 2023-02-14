package com.team871.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.Supplier;

public class Arm extends SubsystemBase {
  private final MotorController shoulderMotor;
  private final MotorController extensionMotor;

  public Arm(final MotorController shoulder, final MotorController extetensionMotor) {
    this.shoulderMotor = shoulder;
    this.extensionMotor = extetensionMotor;
  }

  public void moveShoulderPitch(final double output) {
    shoulderMotor.set(output);
  }

  public void moveExtension(final double output) {
    extensionMotor.set(output);
  }

  public CommandBase moveShoulderPitchCommand(final double output) {
    return run(() -> moveShoulderPitch(output));
  }

  public CommandBase moveExtensionCommand(double output) {
    return run(() -> moveExtension(output));
  }

  public void setdefaultCommand(Supplier<Double> shoulderPitch, Supplier<Double> extension) {
    setDefaultCommand(
        run(
            () -> {
              moveShoulderPitch(shoulderPitch.get());
              moveExtension(extension.get());
            }));
  }
}
