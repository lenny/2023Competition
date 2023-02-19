package com.team871.subsystems;

import java.util.function.Supplier;

import com.team871.config.PitchEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Wrist extends SubsystemBase {
  public static double calculateLevelSetpoint(final double wristMountPitch) {
    return -1 * wristMountPitch;
  }

  private static final double PITCH_PID_KP = 0;
  private static final double PITCH_PID_KI = 0;
  private static final double PITCH_PID_KD = 0;

  private final MotorController wristMotor;
  private final PIDController pitchPID;
  private final PitchEncoder pitchEncoder;

  private boolean selfLevelMode = true;

  public boolean isSelfLevelMode() {
    return selfLevelMode;
  }

  public void toggleSelfLevelMode() {
    this.selfLevelMode = !selfLevelMode;
  }

  public Wrist(final MotorController wristMotor, final PitchEncoder pitchEncoder) {
    this.wristMotor = wristMotor;
    this.pitchPID = new PIDController(PITCH_PID_KP, PITCH_PID_KI, PITCH_PID_KD);
    this.pitchEncoder = pitchEncoder;
    SmartDashboard.putData("wristPitchPID", pitchPID);
    SmartDashboard.putData("wristPitchPIDCommand", wristPitchPIDCommand());
  }

  public void moveWristPitch(final double output) {
    this.wristMotor.set(output);
    SmartDashboard.putNumber("pitchEncoder", pitchEncoder.getPitch());
  }

  public void levelWrist(
      final Supplier<Double> wristPitchSupplier, final Supplier<Double> wristMountPitchSupplier) {
    final double wristPitch = wristPitchSupplier.get();
    final double setpoint = calculateLevelSetpoint(wristMountPitchSupplier.get());
    moveWristPitch(pitchPID.calculate(wristPitch, setpoint));
  }

  public CommandBase toggleSelfLevelCommand() {
    return runOnce(this::toggleSelfLevelMode);
  }

  /**
   * @param wristPitchSupplier pitch in manual mode. [-1, 1]
   * @param wristMountPitchSupplier pitch of wrist mount for adjusting wrist pitch in self-level
   *     mode
   */
  public void setDefaultCommand(
      final Supplier<Double> wristPitchSupplier, final Supplier<Double> wristMountPitchSupplier) {
    setDefaultCommand(
        run(
            () -> {
              if (selfLevelMode) {
                levelWrist(wristPitchSupplier, wristMountPitchSupplier);
              } else {
                moveWristPitch(wristPitchSupplier.get());
              }
            }));
  }
}
