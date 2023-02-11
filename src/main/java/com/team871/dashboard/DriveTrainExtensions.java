package com.team871.dashboard;

import com.team871.subsystems.DriveTrain;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import java.util.function.Supplier;

public class DriveTrainExtensions implements Sendable {

  private final DriveTrain drivetrain;

  public DriveTrainExtensions(final DriveTrain drivetrain) {
    this.drivetrain = drivetrain;

    SmartDashboard.putData("driveDuration", delegateCommand(this::driveDurationCommand));
    SmartDashboard.putData("rotate", delegateCommand(this::rotateCommand));

    delegateCommand(
        () -> {
          return driveDurationCommand();
        });
    delegateCommand(this::driveDurationCommand);
  }

  double speed = 0;
  double duration = 0;
  double degrees = 0;

  public double getDegrees() {
    return degrees;
  }

  public void setDegrees(final double degrees) {
    this.degrees = degrees;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(final double speed) {
    this.speed = speed;
  }

  public double getDuration() {
    return duration;
  }

  public void setDuration(final double duration) {
    this.duration = duration;
  }

  public void reset() {
    drivetrain.stopCommand();
  }

  @Override
  public void initSendable(final SendableBuilder builder) {
    builder.addDoubleProperty("speed", this::getSpeed, this::setSpeed);
    builder.addDoubleProperty("duration", this::getDuration, this::setDuration);
    builder.addDoubleProperty("degrees", this::getDegrees, this::setDegrees);
    builder.setActuator(true);
    builder.setSafeState(this::reset);
  }

  @Override
  public String toString() {
    return "TestInputs [speed=" + speed + ", duration=" + duration + ", degrees=" + degrees + "]";
  }

  private CommandBase driveDurationCommand() {
    return drivetrain.driveDurationCommand(speed, duration);
  }

  private CommandBase rotateCommand() {
    final CommandBase command = drivetrain.rotateCommand(degrees);
    command.setName("rotate");
    return command;
  }

  private CommandBase delegateCommand(final Supplier<Command> supplier) {
    final CommandBase cmd = new ProxyCommand(supplier);
    cmd.addRequirements(drivetrain);
    return cmd;
  }
}
