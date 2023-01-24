package com.team871.config;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class RobotConfigFrisbroTest implements IRobot {
    private final WPI_TalonSRX frontLeft;
    private final WPI_TalonSRX frontRight;
    private final WPI_TalonSRX rearLeft;
    private final WPI_TalonSRX rearRight;
    private final AHRS gyro;

    private final XboxController controller;


    public RobotConfigFrisbroTest() {
        /* sets front left motor to CanSparkMax motor controller with device id 1 */
        frontLeft = new WPI_TalonSRX(1);

        /* sets front right motor to CanSparkMax motor controller with device id 2 */
        frontRight = new WPI_TalonSRX(2);
        frontRight.setInverted(true);
        /* sets rear left motor to CanSparkMax motor controller with device id 3 */
        rearLeft = new WPI_TalonSRX(3);

        /* sets rear right motor to CanSparkMax motor controller with device id 4 */
        rearRight = new WPI_TalonSRX(4);
        rearRight.setInverted(true);

        gyro = new AHRS();

        controller = new XboxController(0);
    }
    @Override
    public MotorController getFrontLeftMotor() {
        return frontLeft;
    }

    @Override
    public MotorController getRearLeftMotor() {
        return rearLeft;
    }

    @Override
    public MotorController getFrontRightMotor() {
        return frontRight;
    }

    @Override
    public MotorController getRearRightMotor() {
        return rearRight;
    }

    @Override
    public AHRS gyro() {
        return gyro;
    }

    @Override
    public XboxController getXboxController() {
        return controller;
    }
}
