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
  private final DistanceEncoder extensionEncoder;
  private final PitchEncoder wristPitchEncoder;
  private final PitchEncoder shoulderPitchEncoder;

  private final double leftXDeadband = .09;
  private final double leftYDeadband = .09;
  private final double rightXDeadband = .09;
  private final double rightYDeadband = .09;

  private final double maxShoulderOffsetValue = 20;
  private static final double shoulderZero = 1.44;
  private static final double shoulderNegative90Value = 2.2478;

  private final double maxWristOffsetValue = 10;
  private static final double wristZeroOffset = -635;

  private static final double topShoulderSetpoint = -2.7;
  private static final double middleShoulderSetpoint = 16.2;
  private static final double bottomShoulderSetpoint = 62;
  private static final double topExtensionSetpoint = 19;
  private static final double middleExtensionSetpoint = 4.63;
  private static final double bottomExtensionSetpoint = 15.274;
  

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
    shoulderMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    shoulderMotor.setInverted(true);

    leftIntakeMotor = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);
    leftIntakeMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    leftIntakeMotor.setInverted(true);

    rightIntakeMotor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
    rightIntakeMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    rightIntakeMotor.setInverted(false);

    wristMotor = new WPI_TalonSRX(10);
    wristMotor.setNeutralMode(NeutralMode.Coast);
    wristMotor.setInverted(true);

    clawMotor = new WPI_TalonSRX(9);
    clawMotor.setNeutralMode(NeutralMode.Coast);

    armExtensionMotor = new WPI_TalonSRX(8);
    armExtensionMotor.setNeutralMode(NeutralMode.Coast);
    armExtensionMotor.setInverted(true);

    drivetrainController = new CommandXboxController(0);
    armController = new CommandXboxController(1);

    balancePID = new PIDController(0.03, 0.0, 0.0001);

    gyro = new Gyro();

    extensionEncoder = new SRXDistanceEncoder(armExtensionMotor, 0.00007005);
    /**
     * up 90 degrees is 380 down 90 degrees is 900, original value for degrees per tick was -.3529
     */
    final double wristDegreesPerTick = 180.0d / (380.0d - 900.0d);
    wristPitchEncoder =
        new SRXAnalogEncoderTalonSRX(wristMotor, wristZeroOffset, wristDegreesPerTick);
    /** down 90 is 1.5 and striaght out (0 degrees) is .68 */
    final double shoulderDegreesPerVolt = 90 / (shoulderNegative90Value - shoulderZero);
    shoulderPitchEncoder =
        new SparkMaxAnalogEncoder(shoulderMotor, shoulderZero, shoulderDegreesPerVolt);
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
  public DistanceEncoder getExtensionEncoder() {
    return extensionEncoder;
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

  @Override
  public double getMaxOffsetWristValue() {
    // TODO Auto-generated method stub
    return maxWristOffsetValue;
  }

  @Override
  public double getMaxOffsetShoulderValue() {
    // TODO Auto-generated method stub
    return maxShoulderOffsetValue;
  }

  @Override
  public double getTopShoulderSetpoint() {
    // TODO Auto-generated method stub
    return topShoulderSetpoint;
  }

  @Override
  public double getMiddleShoulderSetpoint() {
    // TODO Auto-generated method stub
    return middleShoulderSetpoint;
  }

  @Override
  public double getBottomShoulderSetpoint() {
    // TODO Auto-generated method stub
    return bottomShoulderSetpoint;
  }

  @Override
  public double getTopExtensionSetpoint() {
    // TODO Auto-generated method stub
    return topExtensionSetpoint;
  }

  @Override
  public double getMiddleExtensionSetpoint() {
    // TODO Auto-generated method stub
    return middleExtensionSetpoint;
  }

  @Override
  public double getBottomExtensionSetpoint() {
    // TODO Auto-generated method stub
    return bottomExtensionSetpoint;
  }
}
