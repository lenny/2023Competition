package com.team871.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
  private final MecanumDrive mecanum;

  private final MotorController frontLeftMotor;

  private final MotorController backLeftMotor;

  private final MotorController frontRightMotor;

  private final MotorController backRightMotor;
  private final AHRS gyro;
  private boolean motorsEnabled = true;

  public DriveTrain(
      MotorController frontLeftMotor,
      MotorController frontRightMotor,
      MotorController backLeftMotor,
      MotorController backRightMotor,
      AHRS gyro
  ) {
    super();
    this.frontRightMotor = frontRightMotor;
    this.frontLeftMotor = frontLeftMotor;
    this.backRightMotor = backRightMotor;
    this.backLeftMotor = backLeftMotor;
    mecanum = new MecanumDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
    this.gyro = gyro;

  }

  private void driveMecanum(double xValue, double yValue, double zValue) {
    SmartDashboard.putNumber("mecanumX", xValue);
    SmartDashboard.putNumber("mecanumY", yValue);
    SmartDashboard.putNumber("mecanumZ", zValue);
    SmartDashboard.putBoolean("motorStatus", motorsEnabled);
    if (motorsEnabled) mecanum.driveCartesian(xValue, yValue, zValue);
  }

  public CommandBase defaultCommand(XboxController xboxController) {
    return run(()->{
      driveMecanum(xboxController.getLeftY(), xboxController.getLeftX(), xboxController.getRightX());
    });
  }

  public CommandBase balanceCommand(AHRS gyro) {
    PIDController balancePID = new PIDController(0.033, 0, 0.000009);
    PIDController rotationPID = new PIDController(0.00277, 0, 0);
    rotationPID.setSetpoint(0);

    PIDCommand command =
        new PIDCommand(
            balancePID, gyro::getRoll, 0, output -> {
              double rotationPIDOutput = rotationPID.calculate(gyro.getYaw());
             SmartDashboard.putNumber("pitchPIDOutput", output);
             SmartDashboard.putNumber("yawPIDOutput", rotationPIDOutput);
              driveMecanum(output,0,rotationPIDOutput);
        }, this);
    return command;
  }

  public CommandBase disableMotors() {
    return runOnce(() -> {
     motorsEnabled = false;
    });
  }

  public CommandBase enableMotors() {
    return runOnce(() -> {
      motorsEnabled = true;
    });
  }

  public CommandBase driveForwardCommand(double speed) {
    return runOnce(
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

  public CommandBase resetGyro() {
    return runOnce(()-> gyro.reset());
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("gyroPitch", gyro.getPitch());
    SmartDashboard.putNumber("gyroRoll", gyro.getRoll());
    SmartDashboard.putNumber("gyroYaw", gyro.getYaw());
    SmartDashboard.putBoolean("motorStatus", motorsEnabled);

  }
}
