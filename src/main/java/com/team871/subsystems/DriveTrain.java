package com.team871.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
    private final MecanumDrive mecanum;
    private final PIDController balancePID;

    public DriveTrain(MotorController frontLeftMotor,
                      MotorController frontRightMotor,
                      MotorController backLeftMotor,
                      MotorController backRightMotor) {
        mecanum = new MecanumDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
        balancePID = new PIDController(0.03, 0.0, 0.0001);
        balancePID.setSetpoint(0.0);
    }

//    public DriveTrain() {
//        mecanum = new MecanumDrive(RobotConfig., backLeftMotor, frontRightMotor, backRightMotor);
//        balancePID = new PIDController(0.03, 0.0, 0.0001);
//        balancePID.setSetpoint(0.0);
//        SmartDashboard.putData("balancePID", balancePID);
//    }

    public void driveMecanum(double xValue, double yValue, double zValue) {
        mecanum.driveCartesian(xValue, yValue, zValue);
}

}


