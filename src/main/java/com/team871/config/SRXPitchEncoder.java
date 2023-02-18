package com.team871.config;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.util.sendable.SendableBuilder;

public class SRXPitchEncoder implements PitchEncoder{

    private final WPI_TalonSRX talon;
    private double degreesPerTick;

    public SRXPitchEncoder(WPI_TalonSRX talonSRX, double degreesPerTick) {

        this.talon = talonSRX;
        this.degreesPerTick = degreesPerTick;
    }

    public static double calculateDegrees(double totalDegrees)
    {
        return totalDegrees % 360;
    }

    @Override
    public double getPitch() {
        return talon.getSelectedSensorPosition() * degreesPerTick;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("pitch", this::getPitch, null);
    }

    @Override
    public void reset() {
        talon.getSensorCollection().setQuadraturePosition(0, 0);
    }
}
