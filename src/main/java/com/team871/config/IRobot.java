package com.team871.config;

import com.kauailabs.navx.frc.AHRS;
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

  /**
   * @return gyro
   */
  AHRS gyro();

  CommandXboxController getXboxController();

  PIDController getBalancePID();
}
