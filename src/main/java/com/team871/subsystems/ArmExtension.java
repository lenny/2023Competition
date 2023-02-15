package com.team871.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.Supplier;

public class ArmExtension extends SubsystemBase {

    private final MotorController extensionMotor;

    public ArmExtension(final MotorController extensionMotor) {
        this.extensionMotor = extensionMotor;
    }

    public void moveExtension(final double output) {
        extensionMotor.set(output);
    }

    public CommandBase moveExtensionCommand(double output) {
        return run(() -> moveExtension(output));
    }

    public void setdefaultCommand(Supplier<Double> extension) {
        setDefaultCommand(
                run(() -> {
                    moveExtension(extension.get());
                }));
    }
}