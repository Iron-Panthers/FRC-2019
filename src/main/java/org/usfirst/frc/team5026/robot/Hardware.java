package org.usfirst.frc.team5026.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;

import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.LightSensorGroup;
import org.usfirst.frc.team5026.robot.util.MotorGroup;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * This class is meant to store raw hardware instances. Examples: Motor
 * controllers, sensors, etc. Meant to contain hardware declarations that would
 * otherwise be in Robot class.
 */
public class Hardware {
	/* Drivebase motor controllers */
	public TalonSRX driveRight1;
	public VictorSPX driveRight2;
	public TalonSRX driveLeft1;
	public VictorSPX driveLeft2;

	public TalonSRX gyroTestMotor;
	public PigeonIMU gyro;

	/* Drivebase MotorGroups */
	public MotorGroup rightDriveMotors;
	public MotorGroup leftDriveMotors;
	public TalonSRX armMotor;

	/* IntakeArm motor controllers */
	public TalonSRX armMotor;
	public TalonSRX armIntakeMotor;

	/** Motors/sensors for other subsystems will go down here */

	// Line Follow
	public AnalogInput centerLightSensor;
	public AnalogInput backLightSensorRight;
	public AnalogInput backLightSensorLeft;
	public AnalogInput frontLightSensorRight;
	public AnalogInput frontLightSensorLeft;
	public LightSensorGroup lightSensors;

	// Drive Straight Auto
	public PigeonIMU gyro;
	
	public Hardware() {
		/* Drivebase motor controller creation */
		driveRight1 = new TalonSRX(Constants.Drivebase.DRIVE_R1_PORT);
		driveRight2 = new VictorSPX(Constants.Drivebase.DRIVE_R2_PORT);
		driveLeft1 = new TalonSRX(Constants.Drivebase.DRIVE_L1_PORT);
		driveLeft2 = new VictorSPX(Constants.Drivebase.DRIVE_L2_PORT);
		driveRight1.setInverted(true); //for linefollow testing only
		driveLeft1.setInverted(false);//for linefollow testing only
		driveRight1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		driveLeft1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		driveRight1.configMotionCruiseVelocity(Constants.DriveStraight.DRIVE_CRUISE_VELOCITY);
		driveLeft1.configMotionCruiseVelocity(Constants.DriveStraight.DRIVE_CRUISE_VELOCITY);
		driveRight1.configMotionAcceleration(Constants.DriveStraight.DRIVE_ACCELERATION);
		driveLeft1.configMotionAcceleration(Constants.DriveStraight.DRIVE_ACCELERATION);


		rightDriveMotors = new MotorGroup("Drive (right) motor group", driveRight1, driveRight2);
		leftDriveMotors = new MotorGroup("Drive (left) motor group", driveLeft1, driveLeft2);

		rightDriveMotors.configPID(Constants.Drivebase.P, Constants.Drivebase.I, Constants.Drivebase.D,
				Constants.Drivebase.F);
		leftDriveMotors.configPID(Constants.Drivebase.P, Constants.Drivebase.I, Constants.Drivebase.D,
				Constants.Drivebase.F);

		rightDriveMotors.setNeutralMode(NeutralMode.Brake);
		leftDriveMotors.setNeutralMode(NeutralMode.Brake);

		frontLightSensorLeft = new AnalogInput(Constants.LineFollow.FRONT_LEFT_SENSOR_PORT);
        frontLightSensorRight = new AnalogInput(Constants.LineFollow.FRONT_RIGHT_SENSOR_PORT);
        // backLightSensorLeft = new AnalogInput(Constants.LineFollow.BACK_LEFT_SENSOR_PORT);
        // backLightSensorRight = new AnalogInput(Constants.LineFollow.BACK_RIGHT_SENSOR_PORT);
		centerLightSensor = new AnalogInput(Constants.LineFollow.CENTER_SENSOR_PORT);
		lightSensors = new LightSensorGroup(5, frontLightSensorLeft, centerLightSensor, frontLightSensorRight);
		/* Gyro */
		gyroTestMotor = new TalonSRX(5);
		gyro = new PigeonIMU(gyroTestMotor);
		/* IntakeArm motor controller creation */
		armMotor = new TalonSRX(Constants.IntakeArm.INTAKE_ARM_MOTOR_PORT);
		armIntakeMotor = new TalonSRX(Constants.IntakeArm.INTAKE_MOTOR_PORT);
		armMotor.setNeutralMode(NeutralMode.Brake);
	}
}
