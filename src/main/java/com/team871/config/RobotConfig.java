//package com.team871.config;
//
//import com.kauailabs.navx.frc.AHRS;
//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMaxLowLevel;
//import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.motorcontrol.MotorController;
//import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//
//public class RobotConfig implements IRobot {
//    private final CANSparkMax frontLeft;
//    private final CANSparkMax frontRight;
//    private final CANSparkMax rearLeft;
//    private final CANSparkMax rearRight;
//    private final AHRS gyro;
//
//    private final XboxController controller;
//
//
//    public RobotConfig() {
//        /* sets front left motor to CanSparkMax motor controller with device id 1 */
//        frontLeft = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
//        frontLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
//        frontLeft.setInverted(true);
//
//        /* sets front right motor to CanSparkMax motor controller with device id 2 */
//        frontRight = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
//        frontRight.setIdleMode(CANSparkMax.IdleMode.kCoast);
//        frontRight.setInverted(false);
//
//        /* sets rear left motor to CanSparkMax motor controller with device id 3 */
//        rearLeft = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
//        rearLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
//        rearLeft.setInverted(true);
//
//        /* sets rear right motor to CanSparkMax motor controller with device id 4 */
//        rearRight = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
//        rearRight.setIdleMode(CANSparkMax.IdleMode.kCoast);
//        rearRight.setInverted(false);
//
//        gyro = new AHRS();
//
//        controller = new XboxController(0);
//    }
//    @Override
//    public MotorController getFrontLeftMotor() {
//        return frontLeft;
//    }
//
//    @Override
//    public MotorController getRearLeftMotor() {
//        return rearLeft;
//    }
//
//    @Override
//    public MotorController getFrontRightMotor() {
//        return frontRight;
//    }
//
//    @Override
//    public MotorController getRearRightMotor() {
//        return rearRight;
//    }
//
//    @Override
//    public AHRS gyro() {
//        return gyro;
//    }
//
//    @Override
//    public XboxController getXboxController() {
//        return controller;
//    }
//}
