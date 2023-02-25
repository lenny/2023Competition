package com.team871.subsystems;

import com.team871.config.PitchEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.Supplier;

public class Wrist extends SubsystemBase {
  private static final double WRIST_PID_KP = 0.048;
  private static final double WRIST_PID_KI = 0;
  private static final double WRIST_PID_KD = 0;
  private static final double TOTAL_SHOULDER_ANGLE = 90;

  private final MotorController wristMotor;
  private final PIDController pitchPID;
  private final PitchEncoder wristEncoder;
  private double positionThetaSetpointTest;

  public Wrist(final MotorController wristMotor, final PitchEncoder wristEncoder) {
    this.wristMotor = wristMotor;
    this.pitchPID = new PIDController(WRIST_PID_KP, WRIST_PID_KI, WRIST_PID_KD);
    this.wristEncoder = wristEncoder;
    SmartDashboard.putData("wristPitchPID", pitchPID);
    //    SmartDashboard.putData("wristPitchPIDCommand", wristPitchPIDCommand());
  }

  public void moveWristPitch(final double output) {
    this.wristMotor.set(output);
    SmartDashboard.putNumber("wristEncoder", wristEncoder.getPitch());
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
  }

  public CommandBase wristPitchPIDCommand(Supplier<Double> armPositionTheta) {
    final CommandBase command =
        new PIDCommand(
            pitchPID,
            wristEncoder::getPitch,
            () -> this.positionThetaSetpoint(armPositionTheta.get()),
            this::moveWristPitch,
            this);

    command.setName("WristPitchCommand");
    return command;
  }

  public double getPositionThetaSetpointTest() {
    return positionThetaSetpointTest;
  }

  public void setPositionThetaSetpointTest(double positionThetaSetpointTest) {
    this.positionThetaSetpointTest = positionThetaSetpointTest;
  }

  public double positionThetaSetpoint(double armPositionTheta) {
    return armPositionTheta * -1;
  }
}
