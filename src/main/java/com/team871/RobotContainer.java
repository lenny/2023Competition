// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team871;

import com.team871.config.Gyro;
import com.team871.config.IGyro;
import com.team871.config.IRobot;
import com.team871.config.RobotConfig;
import com.team871.config.SimulationGyro;
import com.team871.dashboard.DriveTrainExtensions;
import com.team871.subsystems.Arm;
import com.team871.subsystems.Claw;
import com.team871.subsystems.DriveTrain;
import com.team871.subsystems.Wrist;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final DriveTrain drivetrain;
  private final Arm arm;
  private final Wrist wrist;
  private final Claw claw;
  private final IRobot config;
  private final IGyro gyro;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    //    config = new RobotConfigFrisbro();
    config = new RobotConfig();
    gyro = RobotBase.isReal() ? new Gyro() : new SimulationGyro();

    drivetrain =
        new DriveTrain(
            config.getFrontLeftMotor(),
            config.getFrontRightMotor(),
            config.getRearLeftMotor(),
            config.getRearRightMotor(),
            gyro);

    arm = new Arm(config.getShoulderMotor(), config.getArmExtensionMotor());

    wrist = new Wrist(config.getWristMotor());

    claw =
        new Claw(config.getClawMotor(), config.getLeftIntakeMotor(), config.getRightIntakeMotor());

    SmartDashboard.putData("DriveTrain", drivetrain);
    SmartDashboard.putData("Arm", arm);
    SmartDashboard.putData("Wrist", wrist);
    SmartDashboard.putData("Claw", claw);
    SmartDashboard.putData("Gyro", gyro);
    SmartDashboard.putData("DriveTrainTest", new DriveTrainExtensions(drivetrain));

    // Configure the trigger bindings
    configureBindings();

    DriverStation.silenceJoystickConnectionWarning(true);

    CommandScheduler.getInstance()
        .setDefaultCommand(
            drivetrain, drivetrain.defaultCommand(config.getDrivetrainContoller().getHID()));

    // Suppress "Joystick Button 2 on port 0 not available, check if controller is plugged in"
    // flooding in console
    DriverStation.silenceJoystickConnectionWarning(true);
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

    configureDrivetrainControllerBindings();
    configureArmControllerBindings();
    configureClawBindings();
    configureWristBindings();
  }

  private void configureClawBindings() {
    final CommandXboxController controller = config.getArmController();

    controller.a().onTrue(claw.toggleIntakeMotorsCommand());

    controller.rightBumper().onTrue(claw.invertIntakeCommand());

    claw.setdefaultCommand(() -> controller.getRightX());
  }

  private void configureWristBindings() {
    final CommandXboxController controller = config.getArmController();

    wrist.setdefaultCommand(() -> controller.getRightY());
  }

  private void configureArmControllerBindings() {
    final CommandXboxController controller = config.getArmController();

    arm.setdefaultCommand(() -> controller.getLeftY(), () -> controller.getLeftX());
  }

  private void configureDrivetrainControllerBindings() {
    final CommandXboxController drivetrainController = config.getDrivetrainContoller();
    drivetrainController.b().whileTrue(drivetrain.balanceCommand());
    drivetrainController.leftBumper().whileTrue(gyro.resetGyroCommand());
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
}
