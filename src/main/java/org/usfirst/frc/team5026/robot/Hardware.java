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
import edu.wpi.first.wpilibj.DoubleSolenoid;

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

	public DigitalInput forwardLimit, reverseLimit;




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
		gyroTestMotor = new TalonSRX(3);
		gyro = new PigeonIMU(gyroTestMotor);

		/* IntakeArm motor controller creation */

		// Motor Group
		// All are on the same motor group to reduce required limit switches

		forwardLimit = new DigitalInput(0); // Limit Switch on the side of the robot, hits when robot climbs all the way
											// up (elevator down all the way) //
											// rightMotor3.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
		reverseLimit = new DigitalInput(1); // Limit Switch nearest to the training wheels, hits when robot climbs down
											// all the way (elevator up all the way) //
											// rightMotor3.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyClosed);

	}
}
