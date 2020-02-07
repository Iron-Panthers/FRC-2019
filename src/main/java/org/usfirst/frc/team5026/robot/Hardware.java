package org.usfirst.frc.team5026.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.SparkMaxMotorGroup;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * This class is meant to store raw hardware instances. Examples: Motor
 * controllers, sensors, etc. Meant to contain hardware declarations that would
 * otherwise be in Robot class.
 */
public class Hardware {
	/* Drivebase motor controllers */
	public CANSparkMax driveRight1;
	public CANSparkMax driveRight2;
	public CANSparkMax driveLeft1;
	public CANSparkMax driveLeft2;

	public TalonSRX gyroTestMotor;
	public PigeonIMU gyro;

	/* Drivebase MotorGroups */
	public SparkMaxMotorGroup rightDriveMotors;
	public SparkMaxMotorGroup leftDriveMotors;

	/* IntakeArm motor controllers */
	public TalonSRX armMotor;
	public TalonSRX armIntakeMotor;

	/* Climb motor controllers and pneumatics */
	public CANSparkMax leftMotor1;
	public CANSparkMax leftMotor2;
	public CANSparkMax leftMotor3;
	public CANSparkMax rightMotor1;
	public CANSparkMax rightMotor2;
	public CANSparkMax rightMotor3;
	public TalonSRX leftWinchMotor;
	public TalonSRX rightWinchMotor;

	public DigitalInput forwardLimit, reverseLimit;

	public TalonSRX trainingWheelMotor;

	public SparkMaxMotorGroup climbMotors;

	public Solenoid superStructurePistons;
	public Solenoid hatchPiston;
	public Solenoid hatchExtenderPistons;
	public Solenoid gearShift;

	/** Motors/sensors for other subsystems will go down here */

	public Hardware() {
		/* Drivebase motor controller creation */
		driveRight1 = new CANSparkMax(Constants.Drivebase.DRIVE_R1_PORT, MotorType.kBrushless);
		driveRight2 = new CANSparkMax(Constants.Drivebase.DRIVE_R2_PORT, MotorType.kBrushless);
		driveLeft1 = new CANSparkMax(Constants.Drivebase.DRIVE_L1_PORT, MotorType.kBrushless);
		driveLeft2 = new CANSparkMax(Constants.Drivebase.DRIVE_L2_PORT, MotorType.kBrushless);

		/* Drivebase configuration */
		driveRight1.setInverted(Constants.Drivebase.IS_RIGHT_INVERTED);
		driveLeft1.setInverted(Constants.Drivebase.IS_LEFT_INVERTED);

		rightDriveMotors = new SparkMaxMotorGroup("Right Drive Motor Group", driveRight1, driveRight2);
		leftDriveMotors = new SparkMaxMotorGroup("Left Drive Motor Group", driveLeft1, driveLeft2);

		rightDriveMotors.setIdleMode(IdleMode.kBrake);
		rightDriveMotors.setOpenLoopRampRate(Constants.Drivebase.RAMP_RATE);
		leftDriveMotors.setIdleMode(IdleMode.kBrake);
		leftDriveMotors.setOpenLoopRampRate(Constants.Drivebase.RAMP_RATE);

		/* Gyro */
		gyroTestMotor = new TalonSRX(5);
		gyro = new PigeonIMU(gyroTestMotor);

		/* IntakeArm motor controller creation */
		armMotor = new TalonSRX(Constants.IntakeArm.INTAKE_ARM_MOTOR_PORT);
		armIntakeMotor = new TalonSRX(Constants.IntakeArm.INTAKE_MOTOR_PORT);
		armIntakeMotor.setInverted(Constants.IntakeArm.IS_INTAKE_INVERTED);
		armMotor.setNeutralMode(NeutralMode.Brake);
		armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

		/* Climb Subsystem creation */
		leftMotor1 = new CANSparkMax(Constants.Climb.LEFT_MOTOR_1_PORT, MotorType.kBrushless);
		leftMotor2 = new CANSparkMax(Constants.Climb.LEFT_MOTOR_2_PORT, MotorType.kBrushless);
		leftMotor3 = new CANSparkMax(Constants.Climb.LEFT_MOTOR_3_PORT, MotorType.kBrushless);
		rightMotor1 = new CANSparkMax(Constants.Climb.RIGHT_MOTOR_1_PORT, MotorType.kBrushless);
		rightMotor2 = new CANSparkMax(Constants.Climb.RIGHT_MOTOR_2_PORT, MotorType.kBrushless);
		rightMotor3 = new CANSparkMax(Constants.Climb.RIGHT_MOTOR_3_PORT, MotorType.kBrushless);
		leftWinchMotor = new TalonSRX(Constants.Climb.WINCH_LEFT_PORT);
		rightWinchMotor = new TalonSRX(Constants.Climb.WINCH_RIGHT_PORT);

		trainingWheelMotor = new TalonSRX(Constants.Climb.TRAINING_WHEEL_MOTOR_PORT);
		trainingWheelMotor.configOpenloopRamp(Constants.Climb.TRAINING_WHEEL_RAMP_RATE, Constants.Climb.TRAINING_WHEEL_TIMEOUT_MS);
		trainingWheelMotor.setInverted(true);

		// Motor Group
		// All are on the same motor group to reduce required limit switches
		climbMotors = new SparkMaxMotorGroup("Climb Motor Group", rightMotor3, leftMotor2, leftMotor3, rightMotor1,
				rightMotor2, leftMotor1);
		climbMotors.getMasterMotor().getEncoder().setPosition(0.0);
		leftMotor1.setInverted(Constants.Climb.IS_LEFT_INVERTED);
		leftMotor2.setInverted(Constants.Climb.IS_LEFT_INVERTED);
		leftMotor3.setInverted(Constants.Climb.IS_LEFT_INVERTED);
		rightMotor1.setInverted(Constants.Climb.IS_RIGHT_INVERTED);
		rightMotor2.setInverted(Constants.Climb.IS_RIGHT_INVERTED);
		rightMotor3.setInverted(Constants.Climb.IS_RIGHT_INVERTED);
		leftWinchMotor.setNeutralMode(NeutralMode.Brake);
		leftWinchMotor.setInverted(Constants.Climb.IS_LEFT_INVERTED);
		rightWinchMotor.setNeutralMode(NeutralMode.Brake);
		rightWinchMotor.setInverted(Constants.Climb.IS_RIGHT_INVERTED);

		forwardLimit = new DigitalInput(0); // Limit Switch on the side of the robot, hits when robot climbs all the way
											// up (elevator down all the way) //
											// rightMotor3.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
		reverseLimit = new DigitalInput(1); // Limit Switch nearest to the training wheels, hits when robot climbs down
											// all the way (elevator up all the way) //
											// rightMotor3.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyClosed);

		superStructurePistons = new Solenoid(Constants.Climb.SUPER_STRUCTURE_SOLENOID_PORT);
		hatchPiston = new Solenoid(Constants.IntakeArm.HATCH_PISTON_SOLENOID_PORT);
		hatchExtenderPistons = new Solenoid(Constants.IntakeArm.HATCH_EXTENDER_PISTON_SOLENOID_PORT);
		gearShift = new Solenoid(Constants.Drivebase.GEAR_SHIFT_PORT);
	}
}
