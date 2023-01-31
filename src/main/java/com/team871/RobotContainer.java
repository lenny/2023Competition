// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team871;

import com.kauailabs.navx.frc.AHRS;
import com.team871.config.IRobot;
// import com.team871.config.RobotConfigFrisbroTest;
import com.team871.config.RobotConfigFrisbro;
import com.team871.config.RobotConfigScorpion;
import com.team871.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.Collections;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final DriveTrain drivetrain;
  private final IRobot config;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

//    config = new RobotConfigFrisbro();
    config = new RobotConfigScorpion();

    drivetrain =
        new DriveTrain(
            config.getFrontLeftMotor(),
            config.getFrontRightMotor(),
            config.getRearLeftMotor(),
            config.getRearRightMotor(),
                config.gyro());

    SmartDashboard.putData("drivetrain", drivetrain);
    SmartDashboard.putData("disableMotorsCommand", drivetrain.disableMotors());
    SmartDashboard.putData("enableMotorsCommand", drivetrain.enableMotors());
    SmartDashboard.putData("balanceCommand", drivetrain.balanceCommand(config.gyro()));
    SmartDashboard.putData("resetGyro", drivetrain.resetGyro());

    // Configure the trigger bindings
    configureBindings();

    CommandScheduler.getInstance()
        .setDefaultCommand(
            drivetrain, drivetrain.defaultCommand(config.getXboxController().getHID()));

    //CommandScheduler.getInstance().schedule(gyroReportCommand().repeatedly());
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    System.out.println("configure bindings");

    config.getXboxController().b().whileTrue(drivetrain.balanceCommand(config.gyro()));
    //        config.getXboxController().a().onTrue(
    //                drivetrain.driveDuration(5, 5));
    config.getXboxController().leftBumper().whileTrue(drivetrain.resetGyro());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    //        return Autos.exampleAuto(exampleSubsystem);
    return null;
  }


//  public CommandBase gyroReportCommand() {
//    AHRS gyro = config.gyro();
//    return Commands.run(() -> {
//
//    }, new Subsystem[] {} );
//  }
}
