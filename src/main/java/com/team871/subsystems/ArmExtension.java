package com.team871.subsystems;

import com.team871.config.DistanceEncoder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class ArmExtension extends SubsystemBase {

  private static final double EXTENSION_PID_KP = 0.00012207;
  private static final double EXTENSION_PID_KI = 0;
  private static final double EXTENSION_PID_KD = 0;

  private final MotorController extensionMotor;
  private final PIDController extensionPID;
  private final DistanceEncoder distanceEncoder;
  private double positionInchesSetpoint;

  public ArmExtension(final MotorController extensionMotor, final DistanceEncoder distanceEncoder) {
    this.extensionMotor = extensionMotor;
    this.extensionPID = new PIDController(EXTENSION_PID_KP, EXTENSION_PID_KI, EXTENSION_PID_KD);
    this.distanceEncoder = distanceEncoder;
    SmartDashboard.putData("extensionPID", extensionPID);
    // SmartDashboard.putData("ExtensionPIDCommand", extensionPIDCommand());
    SmartDashboard.putData("extensionEncoder", distanceEncoder);
    SmartDashboard.putData("resetCommand", resetExtensionEncoderCommand());
    SmartDashboard.putData("extensionCommand",extensionPIDCommand(()-> getPositionInchesSetpoint()));
  }

  public static double safetyRampRetract (double rawInput, double currentDistance) {
    /** negative output is retracting, so we have to use biggest number not smallest */
    double maxoutput = Math.max(currentDistance/3, .3);
    return Math.max(-maxoutput, rawInput); 
  }

  public static double safetyRampExtend (double rawInput, double currentDistance) {
    double maxoutput = Math.max(16/currentDistance, .3);
    return Math.min(maxoutput, rawInput); 
  }

  /**
   * 
   * @param output retract is negative, extend is positive, output between -1 and 1
   */
  public void moveExtension(final double output) {
    double adjustedOuputRetract = safetyRampRetract(output, distanceEncoder.getDistance());
    double adjustedOuputExtend = safetyRampExtend(output, distanceEncoder.getDistance());
    SmartDashboard.putNumber("extensionMotorOutput", adjustedOuputExtend);
    if (output<0) {
    extensionMotor.set(adjustedOuputRetract);
    } else {
      extensionMotor.set(adjustedOuputExtend);
    }
    }

  @Override
  public void initSendable(SendableBuilder builder) {
    super.initSendable(builder);

    builder.addDoubleProperty(
        "distanceSetpoint", this::getPositionInchesSetpoint, this::setPositionInchesSetpoint);
  }

  public void setdefaultCommand(Supplier<Double> extension) {
    final Command command =
        run(
            () -> {
              moveExtension(extension.get());
            });
    command.setName("ManualExtensionCommand");
    setDefaultCommand(command);
  }

  public CommandBase extensionPIDCommand(DoubleSupplier setpointSupplier) {
    final CommandBase command =
        new PIDCommand(
            extensionPID,
            distanceEncoder::getDistance,
            setpointSupplier,
            this::moveExtension,
            this);

    command.setName("PIDExtensionCommand");
    return command;
  }

  public CommandBase resetExtensionEncoderCommand() {
    return runOnce(distanceEncoder::reset);
  }

  public double getPositionInchesSetpoint() {
    return positionInchesSetpoint;
  }

  public double setPositionInchesSetpoint(double positionInchesSetpoint) {
    return positionInchesSetpoint;
  }
  }
