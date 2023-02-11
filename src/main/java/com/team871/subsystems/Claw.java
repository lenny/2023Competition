package com.team871.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Claw extends SubsystemBase {
  private static double INTAKE_MOTOR_SPEED = .02;

  private final MotorController pinchMotor;
  private final MotorController intakeMotors;

  private boolean intakeOn = false;
  private boolean intakeInverted = false;

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

  public void toggleIntakeMotors() {
    intakeOn = !intakeOn;
    intakeMotors.set(intakeOn ? INTAKE_MOTOR_SPEED : 0);
  }

  public CommandBase toggleIntakeMotorsCommand() {
    return runOnce(() -> toggleIntakeMotors());
  }

  /**
   * @param output beween 0 and 1. 1 full pinched, 0 full open
   * @return
   */
  public CommandBase pinchCommand(double output) {
    return run(() -> setPinch(output));
  }

  public CommandBase invertIntakeCommand() {
    return runOnce(
        () -> {
          intakeInverted = !intakeInverted;
          intakeMotors.setInverted(intakeInverted);
        });
  }
}
