package com.team871.subsystems;

import com.team871.config.IGyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {

  private static final double BALANCE_PID_KP = 0.005;
  private static final double BALANCE_PID_KI = 0;
  private static final double BALANCE_PID_KD = 0;

  private static final double ROTATION_PID_KP = 0.02;
  private static final double ROTATION_PID_KI = 0;
  private static final double ROTATION_PID_KD = 0;

  private final MecanumDrive mecanum;
  private final IGyro gyro;
  private final PIDController balancePID;
  private final PIDController rotationPID;

  private boolean motorsEnabled = true;

  public DriveTrain(
      final MotorController frontLeftMotor,
      final MotorController frontRightMotor,
      final MotorController backLeftMotor,
      final MotorController backRightMotor,
      final IGyro gyro) {
    super();
    mecanum = new MecanumDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
    this.gyro = gyro;

    balancePID = new PIDController(BALANCE_PID_KP, BALANCE_PID_KI, BALANCE_PID_KD);

    rotationPID = new PIDController(ROTATION_PID_KP, ROTATION_PID_KI, ROTATION_PID_KD);
    rotationPID.setSetpoint(0);
  }

  @Override
  public void initSendable(final SendableBuilder builder) {
    super.initSendable(builder);
    SmartDashboard.putData("DisableMotorsCommand", disableMotors());
    SmartDashboard.putData("EnableMotorsCommand", enableMotors());
    SmartDashboard.putData("BalanceCommand", balanceCommand());
    SmartDashboard.putData("BalancePID", balancePID);
    SmartDashboard.putData("RotationPID", rotationPID);
    builder.addBooleanProperty("MotorStatus", this::isMotorsEnabled, null);
  }

  public boolean isMotorsEnabled() {
    return motorsEnabled;
  }

  private void driveMecanum(final double xValue, final double yValue, final double zValue) {
    SmartDashboard.putNumber("mecanumX", xValue);
    SmartDashboard.putNumber("mecanumY", yValue);
    SmartDashboard.putNumber("mecanumZ", zValue);
    SmartDashboard.putBoolean("motorStatus", motorsEnabled);
    if (motorsEnabled) {
      mecanum.driveCartesian(xValue, yValue, zValue);
    } else {
      mecanum.driveCartesian(0, 0, 0);
    }
  }

  public CommandBase defaultCommand(final XboxController xboxController) {
    return run(
        () -> {
          driveMecanum(
              -xboxController.getLeftY(), xboxController.getLeftX(), xboxController.getRightX());
        });
  }

  public CommandBase balanceCommand() {
    balancePID.reset();
    rotationPID.reset();
    return new PIDCommand(
        balancePID,
        gyro::getPitch,
        0,
        output -> {
          final double rotationPIDOutput = rotationPID.calculate(gyro.getYaw());
          SmartDashboard.putNumber("pitchPIDOutput", output);
          SmartDashboard.putNumber("yawPIDOutput", rotationPIDOutput);
          // positive pitch should be forward and negative pitch should be backwards
          driveMecanum(-output, 0, rotationPIDOutput);
        },
        this);
  }

  public CommandBase disableMotors() {
    return runOnce(() -> motorsEnabled = false);
  }

  public CommandBase enableMotors() {
    return runOnce(() -> motorsEnabled = true);
  }

  public CommandBase driveForwardCommand(final double speed) {
    return run(() -> driveMecanum(speed, 0, rotationPID.calculate(gyro.getYaw())));
  }

  public CommandBase driveDurationCommand(final double speed, final double duration) {
    return driveForwardCommand(speed)
        .withTimeout(duration)
        .andThen(stopCommand().withTimeout(2))
        .andThen(stopCommand());
  }

  public Command stopCommand() {
    return run(() -> driveMecanum(0, 0, 0));
  }

  public CommandBase rotateCommand(final double degrees) {
    rotationPID.reset();
    return new PIDCommand(
        rotationPID, gyro::getYaw, degrees, output -> driveMecanum(0, 0, output), this);
  }
}
