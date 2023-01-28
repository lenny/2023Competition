package com.team871.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.team871.commands.DriveCommand;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
  private final MecanumDrive mecanum;

  private final MotorController frontLeftMotor;

  private final MotorController backLeftMotor;

  private final MotorController frontRightMotor;

  private final MotorController backRightMotor;

  public DriveTrain(
      MotorController frontLeftMotor,
      MotorController frontRightMotor,
      MotorController backLeftMotor,
      MotorController backRightMotor) {
    this.frontRightMotor = frontRightMotor;
    this.frontLeftMotor = frontLeftMotor;
    this.backRightMotor = backRightMotor;
    this.backLeftMotor = backLeftMotor;
    mecanum = new MecanumDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
  }

  public void driveMecanum(double xValue, double yValue, double zValue) {
    mecanum.driveCartesian(xValue, yValue, zValue);
  }

  public CommandBase driveCommand(XboxController xboxController) {
    return new DriveCommand(this, xboxController);
  }

  public CommandBase balanceCommand(AHRS gyro) {
    PIDController balancePID = new PIDController(0.03, 0.000001, 0);
    PIDCommand command =
        new PIDCommand(
            balancePID, gyro::getRoll, 0, output -> mecanum.driveCartesian(0, output, 0), this);
    return command;
  }

  public CommandBase driveForwardCommand(double speed) {
    return run(
        () -> {
          frontLeftMotor.set(speed);
          frontRightMotor.set(speed);
          backLeftMotor.set(speed);
          backRightMotor.set(speed);
        });
  }

  public CommandBase driveDuration(double speed, double millis) {
    return driveForwardCommand(5).withTimeout(5).andThen(driveForwardCommand(0));
  }
}
