package com.team871.config;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotConfig implements IRobot {
  private final CANSparkMax frontLeft;
  private final CANSparkMax frontRight;
  private final CANSparkMax rearLeft;
  private final CANSparkMax rearRight;
  private final CANSparkMax shoulderMotor;
  private final CANSparkMax leftIntakeMotor;
  private final CANSparkMax rightIntakeMotor;
  private final WPI_TalonSRX wristMotor;
  private final WPI_TalonSRX clawMotor;
  private final WPI_TalonSRX armExtensionMotor;

  private final CommandXboxController drivetrainController;
  private final CommandXboxController armController;

  private final PIDController balancePID;

  private final IGyro gyro;

  private final DistanceEncoder armExtensionEncoder;
  private final PitchEncoder wristPitchEncoder;
  private final PitchEncoder shoulderPitchEncoder;

  private final double leftXDeadband = .09;
  private final double leftYDeadband = .09;
  private final double rightXDeadband = .09;
  private final double rightYDeadband = .09;

  public RobotConfig() {
    /* sets front left motor to CanSparkMax motor controller with device id 1 */
    frontLeft = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
    frontLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
    frontLeft.setInverted(false);

    /* sets front right motor to CanSparkMax motor controller with device id 2 */
    frontRight = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
    frontRight.setIdleMode(CANSparkMax.IdleMode.kCoast);
    frontRight.setInverted(true);

    /* sets rear left motor to CanSparkMax motor controller with device id 3 */
    rearLeft = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
    rearLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
    rearLeft.setInverted(false);

    /* sets rear right motor to CanSparkMax motor controller with device id 4 */
    rearRight = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
    rearRight.setIdleMode(CANSparkMax.IdleMode.kCoast);
    rearRight.setInverted(true);

    shoulderMotor = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
    shoulderMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    shoulderMotor.setInverted(true);

    leftIntakeMotor = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);
    leftIntakeMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    leftIntakeMotor.setInverted(true);

    rightIntakeMotor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
    rightIntakeMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    rightIntakeMotor.setInverted(false);

    wristMotor = new WPI_TalonSRX(10);
    wristMotor.setNeutralMode(NeutralMode.Coast);

    clawMotor = new WPI_TalonSRX(9);
    clawMotor.setNeutralMode(NeutralMode.Coast);

    armExtensionMotor = new WPI_TalonSRX(8);
    armExtensionMotor.setNeutralMode(NeutralMode.Coast);
    armExtensionMotor.setInverted(true);

    drivetrainController = new CommandXboxController(0);
    armController = new CommandXboxController(1);

    balancePID = new PIDController(0.03, 0.0, 0.0001);

    gyro = new Gyro();

    /** up 90 degrees is 380 down 90 degrees is 900 */
    wristPitchEncoder = new SRXAnalogEncoderTalonSRX(wristMotor, 635, -.3529);
    /** down 90 is 1.5 and striaght out (0 degrees) is .68 */
    shoulderPitchEncoder = new SparkMaxAnalogEncoder(shoulderMotor, .68, -109.75);

    armExtensionEncoder = new SRXDistanceEncoder(armExtensionMotor, 0.00006104);
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
  public IGyro gyro() {
    return gyro;
  }

  @Override
  public CommandXboxController getDrivetrainContoller() {
    return drivetrainController;
  }

  @Override
  public PIDController getBalancePID() {
    return balancePID;
  }

  @Override
  public DistanceEncoder getArmExtensionEncoder() {
    return armExtensionEncoder;
  }

  @Override
  public PitchEncoder getWristPitchEncoder() {
    return wristPitchEncoder;
  }

  @Override
  public PitchEncoder getShoulderPitchEncoder() {
    return shoulderPitchEncoder;
  }

  @Override
  public double getLeftXDeadband() {
    return leftXDeadband;
  }

  @Override
  public double getLeftYDeadband() {
    return leftYDeadband;
  }

  @Override
  public double getRightXDeadband() {
    return rightXDeadband;
  }

  @Override
  public double getRightYDeadband() {
    return rightYDeadband;
  }

  @Override
  public MotorController getShoulderMotor() {
    return shoulderMotor;
  }

  @Override
  public MotorController getArmExtensionMotor() {
    return armExtensionMotor;
  }

  @Override
  public MotorController getWristMotor() {
    return wristMotor;
  }

  @Override
  public MotorController getClawMotor() {
    return clawMotor;
  }

  @Override
  public MotorController getLeftIntakeMotor() {
    return leftIntakeMotor;
  }

  @Override
  public MotorController getRightIntakeMotor() {
    return rightIntakeMotor;
  }

  @Override
  public CommandXboxController getArmController() {
    return armController;
  }
}
