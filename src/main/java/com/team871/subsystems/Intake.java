package com.team871.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private static final double INTAKE_MOTOR_SPEED = .25;
  private static final double INTAKE_OFF = 0;

  private final MotorController intakeMotors;

  public Intake(
      final MotorController leftIntakeMotor,
      final MotorController rightIntakeMotor) {
    this.intakeMotors = new MotorControllerGroup(leftIntakeMotor, rightIntakeMotor);
    setDefaultCommand(run(this::stop));
  }

  public void pullIn() {
    intakeMotors.set(INTAKE_MOTOR_SPEED);
  }

  public void pullOut() {
    intakeMotors.set(-INTAKE_MOTOR_SPEED);
  }

  public void stop() {
    intakeMotors.set(INTAKE_OFF);
  }
}
