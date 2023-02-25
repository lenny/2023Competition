package com.team871.subsystems;

import com.team871.config.PitchEncoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.Supplier;

public class Shoulder extends SubsystemBase {
  private final MotorController shoulderMotor;
  private PitchEncoder shoulderEncoder;

  public Shoulder(final MotorController shoulder, final PitchEncoder shoulderEncoder) {
    this.shoulderMotor = shoulder;
    this.shoulderEncoder = shoulderEncoder;
  }

  public void moveShoulderPitch(final double output) {
    shoulderMotor.set(output);
    SmartDashboard.putNumber("shoulderEncoder", shoulderEncoder.getPitch());
  }

  public CommandBase moveShoulderPitchCommand(final double output) {
    return run(() -> moveShoulderPitch(output));
  }

  public void setdefaultCommand(Supplier<Double> shoulderPitch) {
    setDefaultCommand(run(() -> moveShoulderPitch(shoulderPitch.get())));
  }
}
