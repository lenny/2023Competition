package com.team871.subsystems;

import com.team871.config.PitchEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.DoubleSupplier;

public class PitchSubsystem extends SubsystemBase {
  private final MotorController wristMotor;
  private final PIDController pitchPID;
  private final PitchEncoder pitchEncoder;
  private double positionThetaSetpointTest;
  private String subsystemName;
  private boolean motorsEnabled = true;

  public void setMotorsEnabled(boolean motorsEnabled) {
    this.motorsEnabled = motorsEnabled;
  }

  public boolean getMotorsEnabled() {
    return motorsEnabled;
  }

  public PitchSubsystem(
      final MotorController motor,
      final PitchEncoder pitchEncoder,
      double kp,
      double ki,
      double kd,
      String subsystemName) {
    this.wristMotor = motor;
    this.pitchPID = new PIDController(kp, ki, kd);
    this.pitchEncoder = pitchEncoder;
    this.subsystemName = subsystemName;
    SmartDashboard.putData(subsystemName + "-PitchPID", pitchPID);
    SmartDashboard.putData(subsystemName + "-PitchEncoder", pitchEncoder);
    SmartDashboard.putData(subsystemName + "-DisableMotorsCommand", disableMotors());
    SmartDashboard.putData(subsystemName + "-EnableMotorsCommand", enableMotors());
  }

  public void movePitch(final double output) {
    if (motorsEnabled) {
      wristMotor.set(output);
    } else {
      wristMotor.set(0);
    }
    SmartDashboard.putNumber(subsystemName + "-motorOutput", output);
  }

  public boolean isMotorsEnabled() {
    return motorsEnabled;
  }

  //  public void setdefaultCommand(Supplier<Double> wristPitch) {
  //    setDefaultCommand(run(()-> wristPitchPIDCommand()));
  ////    setDefaultCommand(run(() -> moveWristPitch(wristPitch.get())));
  //  }

  @Override
  public void initSendable(SendableBuilder builder) {
    super.initSendable(builder);

    builder.addDoubleProperty(
        "ThetaSetpoint", this::getPositionThetaSetpointTest, this::setPositionThetaSetpointTest);
    builder.addBooleanProperty("motorsEnabled", this::getMotorsEnabled, this::setMotorsEnabled);
  }

  public CommandBase pitchPIDCommand(DoubleSupplier setpointSupplier) {
    final CommandBase command =
        new PIDCommand(pitchPID, pitchEncoder::getPitch, setpointSupplier, this::movePitch, this);

    command.setName("PitchCommand");
    return command;
  }

  public double getPositionThetaSetpointTest() {
    return positionThetaSetpointTest;
  }

  public void setPositionThetaSetpointTest(double positionThetaSetpointTest) {
    this.positionThetaSetpointTest = positionThetaSetpointTest;
  }

  public CommandBase disableMotors() {
    return runOnce(() -> motorsEnabled = false);
  }

  public CommandBase enableMotors() {
    return runOnce(() -> motorsEnabled = true);
  }
}
