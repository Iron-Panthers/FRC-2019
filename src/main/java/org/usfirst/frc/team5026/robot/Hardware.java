package org.usfirst.frc.team5026.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.SparkMaxMotorGroup;
import org.usfirst.frc.team5026.robot.util.SwerveMC;
import org.usfirst.frc.team5026.robot.util.SwerveMotorGroup;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * This class is meant to store raw hardware instances. Examples: Motor
 * controllers, sensors, etc. Meant to contain hardware declarations that would
 * otherwise be in Robot class.
 */
public class Hardware {

	/* Swerve Stuff*/

	public SwerveMC swerveFrontRight;
	public SwerveMC swerveFrontLeft;
	public SwerveMC swerveBackRight;
	public SwerveMC swerveBackLeft;

	public SwerveMotorGroup swerveMotors;

	/* Drive Motors*/

	public TalonSRX driveFrontRight;
	public TalonSRX driveFrontLeft;
	public TalonSRX driveBackRight;
	public TalonSRX driveBackLeft;
	
	/* Gyro */
	public TalonSRX gyroTestMotor;
	public PigeonIMU gyro;

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

	public DigitalInput forwardLimit, reverseLimit;

	public TalonSRX trainingWheelMotor;

	public SparkMaxMotorGroup climbMotors;

	public DoubleSolenoid superStructurePistons;
	public DoubleSolenoid hatchPiston;
	public DoubleSolenoid gearShift;

	/** Motors/sensors for other subsystems will go down here */

	public Hardware() {
		
		/* Swerve motor and motor group instanciation and configuration */
		swerveFrontRight = new SwerveMC(Constants.Drivebase.SWERVE_FRONT_RIGHT_PORT, Constants.Drivebase.SWERVE_P, Constants.Drivebase.SWERVE_D);
		swerveFrontLeft = new SwerveMC(Constants.Drivebase.SWERVE_FRONT_LEFT_PORT, Constants.Drivebase.SWERVE_P, Constants.Drivebase.SWERVE_D);
		swerveBackRight = new SwerveMC(Constants.Drivebase.SWERVE_BACK_RIGHT_PORT, Constants.Drivebase.SWERVE_P, Constants.Drivebase.SWERVE_D);
		swerveBackLeft = new SwerveMC(Constants.Drivebase.SWERVE_BACK_LEFT_PORT, Constants.Drivebase.SWERVE_P, Constants.Drivebase.SWERVE_D);

		//set inverted here if you want. e.g. swerveFrontRight.setInverted(true);

		swerveMotors = new SwerveMotorGroup(swerveFrontRight, swerveFrontLeft, swerveBackRight, swerveBackLeft);

		/* Drive motor instanciation and configuration */
		driveFrontRight = new TalonSRX(Constants.Drivebase.DRIVE_FRONT_RIGHT_PORT);
		driveFrontLeft = new TalonSRX(Constants.Drivebase.DRIVE_FRONT_LEFT_PORT);
		driveBackRight = new TalonSRX(Constants.Drivebase.DRIVE_BACK_RIGHT_PORT);
		driveBackLeft = new TalonSRX(Constants.Drivebase.DRIVE_BACK_LEFT_PORT);

		//set inverted, idle mode or ramp rate here if you so desire

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

		trainingWheelMotor = new TalonSRX(Constants.Climb.TRAINING_WHEEL_MOTOR_PORT);
		trainingWheelMotor.configOpenloopRamp(Constants.Climb.TRAINING_WHEEL_RAMP_RATE, Constants.Climb.TRAINING_WHEEL_TIMEOUT_MS);
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

		forwardLimit = new DigitalInput(0); // Limit Switch on the side of the robot, hits when robot climbs all the way
											// up (elevator down all the way) //
											// rightMotor3.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
		reverseLimit = new DigitalInput(1); // Limit Switch nearest to the training wheels, hits when robot climbs down
											// all the way (elevator up all the way) //
											// rightMotor3.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyClosed);

		superStructurePistons = new DoubleSolenoid(Constants.Climb.SUPER_STRUCTURE_SOLENOID_PORT_1,
				Constants.Climb.SUPER_STRUCTURE_SOLENOID_PORT_2);
		hatchPiston = new DoubleSolenoid(Constants.Climb.HATCH_PISTON_SOLENOID_PORT_1,
				Constants.Climb.HATCH_PISTON_SOLENOID_PORT_2);
		gearShift = new DoubleSolenoid(Constants.Drivebase.GEAR_SHIFT_PORT_1, Constants.Drivebase.GEAR_SHIFT_PORT_2);
	}
}
