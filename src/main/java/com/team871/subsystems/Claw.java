package com.team871.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Claw extends SubsystemBase {
  private static double INTAKE_MOTOR_SPEED = .02;

  private final MotorController pinchMotor;
  private final MotorController intakeMotors;

  public Claw(
      final MotorController pinchMotor,
      final MotorController leftIntakMotor,
      final MotorController rightIntakeMotor) {
    this.pinchMotor = pinchMotor;

    intakeMotors = new MotorControllerGroup(leftIntakMotor, rightIntakeMotor);
  }

  public void setPinch(final double output) {
    pinchMotor.set(output);
  }

  public void toggleIntakeMotorsCommand(final boolean b) {
    intakeMotors.set(b ? INTAKE_MOTOR_SPEED : 0);
  }

  public CommandBase toggleIntakeMotors(final boolean b) {
    return run(() -> toggleIntakeMotors(b));
  }
}
