package com.team871.config;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public interface IRobot {
  /**
   * @return front left motor
   */
  MotorController getFrontLeftMotor();

  /**
   * @return rear left motor
   */
  MotorController getRearLeftMotor();

  /**
   * @return front right motor
   */
  MotorController getFrontRightMotor();

  /**
   * @return rear right motor
   */
  MotorController getRearRightMotor();

  MotorController getShoulderMotor();

  MotorController getArmExtensionMotor();

  MotorController getWristMotor();

  MotorController getClawMotor();

  MotorController getLeftIntakeMotor();

  MotorController getRightIntakeMotor();

  /**
   * @return gyro
   */
  IGyro gyro();

  CommandXboxController getDrivetrainContoller();

  CommandXboxController getArmController();

  PIDController getBalancePID();

  DistanceEncoder getExtensionEncoder();

  PitchEncoder getWristPitchEncoder();

  PitchEncoder getShoulderPitchEncoder();

  double getLeftXDeadband();

  double getLeftYDeadband();

  double getRightXDeadband();

  double getRightYDeadband();

  double getOffsetWristValue();
}
