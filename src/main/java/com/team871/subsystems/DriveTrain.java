package com.team871.subsystems;

import com.team871.config.Gyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {

  private static class DriveDurationInput implements Sendable {
    private double speed = 0;
    private double duration = 0;

    public double getSpeed() {
      return speed;
    }

    public void setSpeed(double speed) {
      this.speed = speed;
    }

    public double getDuration() {
      return duration;
    }

    public void setDuration(double duration) {
      this.duration = duration;
    }

    public void reset() {
      this.speed = 0;
      this.duration = 0;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType("DriveDurationInput");
      builder.addDoubleProperty("speed", this::getSpeed, this::setSpeed);
      builder.addDoubleProperty("duration", this::getDuration, this::setDuration);
      builder.setActuator(true);
      builder.setSafeState(this::reset);
    }

    @Override
    public String toString() {
      return "DriveDurationInput{" + "speed=" + speed + ", duration=" + duration + '}';
    }
  }

  private static final double BALANCE_PID_KP = 0.005;
  private static final double BALANCE_PID_KI = 0;
  private static final double BALANCE_PID_KD = 0;

  private static final double ROTATION_PID_KP = 0.02;
  private static final double ROTATION_PID_KI = 0;
  private static final double ROTATION_PID_KD = 0;

  private final MecanumDrive mecanum;

  private final Gyro gyro;
  private boolean motorsEnabled = true;
  private final PIDController balancePID;
  private final PIDController rotationPID;

  private final DriveDurationInput driveDurationInput;

  public DriveTrain(
      MotorController frontLeftMotor,
      MotorController frontRightMotor,
      MotorController backLeftMotor,
      MotorController backRightMotor,
      Gyro gyro) {
    super();
    mecanum = new MecanumDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
    this.gyro = gyro;

    balancePID = new PIDController(BALANCE_PID_KP, BALANCE_PID_KI, BALANCE_PID_KD);

    rotationPID = new PIDController(ROTATION_PID_KP, ROTATION_PID_KI, ROTATION_PID_KD);

    rotationPID.setSetpoint(0);

    driveDurationInput = new DriveDurationInput();
  }

  public DriveDurationInput getDriveDurationInput() {
    return driveDurationInput;
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    super.initSendable(builder);
    SmartDashboard.putData("DisableMotorsCommand", disableMotors());
    SmartDashboard.putData("EnableMotorsCommand", enableMotors());
    SmartDashboard.putData("BalanceCommand", balanceCommand());
    SmartDashboard.putData("BalancePID", balancePID);
    SmartDashboard.putData("RotationPID", rotationPID);
    SmartDashboard.putData("DriveDurationCommand", driveDurationCommand());
    builder.addBooleanProperty("MotorStatus", this::isMotorsEnabled, null);
  }

  public boolean isMotorsEnabled() {
    return motorsEnabled;
  }

  private void driveMecanum(double xValue, double yValue, double zValue) {
    SmartDashboard.putNumber("mecanumX", xValue);
    SmartDashboard.putNumber("mecanumY", yValue);
    SmartDashboard.putNumber("mecanumZ", zValue);
    SmartDashboard.putBoolean("motorStatus", motorsEnabled);
    if (motorsEnabled) mecanum.driveCartesian(xValue, yValue, zValue);
  }

  public CommandBase defaultCommand(XboxController xboxController) {
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
          double rotationPIDOutput = rotationPID.calculate(gyro.getYaw());
          SmartDashboard.putNumber("pitchPIDOutput", output);
          SmartDashboard.putNumber("yawPIDOutput", rotationPIDOutput);
          // positive pitch should be forward and negative pitch should be backwards
          driveMecanum(-output, 0, rotationPIDOutput);
        },
        this);
  }

  public CommandBase disableMotors() {
    return runOnce(
        () -> {
          motorsEnabled = false;
        });
  }

  public CommandBase enableMotors() {
    return runOnce(
        () -> {
          motorsEnabled = true;
        });
  }

  public CommandBase driveForwardCommand(double speed) {
    return run(
        () -> {
          driveMecanum(speed, 0, 0);
        });
  }

  public CommandBase driveDurationCommand() {
    return new ProxyCommand(
            () -> {
              return driveForwardCommand(driveDurationInput.speed)
                  .withTimeout(driveDurationInput.duration);
            })
        .andThen(driveForwardCommand(0));
  }
}
