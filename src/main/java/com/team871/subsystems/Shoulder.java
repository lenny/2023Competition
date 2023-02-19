package com.team871.subsystems;

import com.team871.config.PitchEncoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.Supplier;

public class Shoulder extends SubsystemBase {
  private final MotorController shoulderMotor;
  private final PitchEncoder pitchEncoder;

  public Shoulder(final MotorController shoulder, final PitchEncoder pitchEncoder) {
    this.shoulderMotor = shoulder;
    this.pitchEncoder = pitchEncoder;
  }

  public void moveShoulderPitch(final double output) {
    SmartDashboard.putNumber("pitchMotor", output);
    shoulderMotor.set(output);
  }

  public void setDefaultCommand(Supplier<Double> shoulderPitch) {
    setDefaultCommand(run(() -> moveShoulderPitch(shoulderPitch.get())));
  }

  public double getPitch() {
    return pitchEncoder.getPitch();
  }
}
