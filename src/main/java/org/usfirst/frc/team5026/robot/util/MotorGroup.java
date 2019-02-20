package org.usfirst.frc.team5026.robot.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotorGroup {
	private String motorGroupName;
	private TalonSRX masterMotor;
	private IMotorController[] motors;

	/**
	 * MotorGroup is a flexible-size grouping of TalonSRX
	 * 
	 * @param motorGroupName the name of the MotorGroup, (displayed in
	 *                       SmartDashboard)
	 * @param motors         the motors to include in the MotorGroup. First motor is
	 *                       assumed to be the "leader motor"
	 */
	public MotorGroup(String motorGroupName, TalonSRX masterMotor, IMotorController... motors) {
		this.masterMotor = masterMotor;
		this.motors = motors;
		this.motorGroupName = motorGroupName;
		followMaster();
	}

	/**
	 * Sets all the non-master motors to follow the masterMotor
	 */
	void followMaster() {
		for (IMotorController motor : this.motors) {
			motor.follow(this.masterMotor);
		}
	}

	/**
	 * Does configuration for PID constants + feed forward constant. This is applied
	 * to only the master motor
	 * 
	 * @param kP the proportional term
	 * @param kI the integral term
	 * @param kD the derivative term
	 */
	public void configPID(double kP, double kI, double kD, double kF) {
		masterMotor.config_kP(0, kP);
		masterMotor.config_kI(0, kI);
		masterMotor.config_kD(0, kD);
		masterMotor.config_kF(0, kF);

		// masterMotor.config_IntegralZone(Constants.kSlot_Distanc, Constants.kGains_Distanc.kIzone, Constants.DriveStraight.TIMEOUT_MS);
		masterMotor.configClosedLoopPeakOutput(0, Constants.DriveStraight.PEAK_OUTPUT, Constants.DriveStraight.TIMEOUT_MS);

		masterMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.DriveStraight.TIMEOUT_MS);

		// USE THE FOLLOWING CODE IF YOU WANT TO AVERAGE TWO SENSORS
		// masterMotor.configRemoteFeedbackFilter(masterMotor.getDeviceID(),					// Device ID of Source
		// 	RemoteSensorSource.TalonSRX_SelectedSensor,	// Remote Feedback Source
		// 	0,											// Source number [0, 1]
		// 	Constants.DriveStraight.TIMEOUT_MS);		// Configuration Timeout

		// masterMotor.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0, Constants.DriveStraight.TIMEOUT_MS);	// Feedback Device of Remote Talon

		// /* Configure Sum [Sum of both QuadEncoders] to be used for Primary PID Index */
		// masterMotor.configSelectedFeedbackSensor(	FeedbackDevice.SensorSum, 
		// 											Constants.PID_PRIMARY,
		// 											Constants.kTimeoutMs);
		
		// /* Scale Feedback by 0.5 to half the sum of Distance */
		// masterMotor.configSelectedFeedbackCoefficient(	0.5, 						// Coefficient
		// 												Constants.PID_PRIMARY,		// PID Slot of Source 
		// 												Constants.kTimeoutMs);		// Configuration Timeout
		
		// USE THIS IF WE HAVE A FORWARD/REVERSE ISSUE
		// masterMotor.setSensorPhase(true);
		
		/* Set status frame periods to ensure we don't have stale data */
		masterMotor.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, Constants.DriveStraight.TIMEOUT_MS);
		masterMotor.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.DriveStraight.TIMEOUT_MS);

		// THIS VALUE SHOULD BE CHANGED TO A SAFE DEADZONE OF THE DRIVEBASE MOTORS
		/* Configure neutral deadband */
		masterMotor.configNeutralDeadband(0.001, Constants.DriveStraight.TIMEOUT_MS);
		
		/* Motion Magic Configurations */
		masterMotor.configMotionAcceleration(Constants.DriveStraight.DRIVE_CRUISE_VELOCITY, Constants.DriveStraight.TIMEOUT_MS);
		masterMotor.configMotionCruiseVelocity(Constants.DriveStraight.DRIVE_ACCELERATION, Constants.DriveStraight.TIMEOUT_MS);

		/**
		 * Max out the peak output (for all modes).  
		 * However you can limit the output of a given PID object with configClosedLoopPeakOutput().
		 */
		masterMotor.configPeakOutputForward(+1.0, Constants.DriveStraight.TIMEOUT_MS);
		masterMotor.configPeakOutputReverse(-1.0, Constants.DriveStraight.TIMEOUT_MS);

		/**
		 * 1ms per loop.  PID loop can be slowed down if need be.
		 * For example,
		 * - if sensor updates are too slow
		 * - sensor deltas are very small per update, so derivative error never gets large enough to be useful.
		 * - sensor movement is very slow causing the derivative error to be near zero.
		 */
		int closedLoopTimeMs = 3;
		masterMotor.configClosedLoopPeriod(0, closedLoopTimeMs, Constants.DriveStraight.TIMEOUT_MS);

		// I DONT KNOW WHAT THIS ONE IS, USE WITH CAUTION
		// masterMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_Targets, 10);
		zeroSensors();
	}

	public void setMotionMagic(double ticks) {
		masterMotor.set(ControlMode.MotionMagic, ticks);
	}

	/* Zero quadrature encoders on Talons */
	public void zeroSensors() {
		masterMotor.getSensorCollection().setQuadraturePosition(0, Constants.DriveStraight.TIMEOUT_MS);
	}

	/**
	 * Sets the PercentOutput power of the master motor
	 * 
	 * @param power (should be between -1.0 and 1.0)
	 */
	public void set(double power) {
		masterMotor.set(ControlMode.PercentOutput, power);
		for (IMotorController motor : this.motors) {
			SmartDashboard.putNumber(motorGroupName + " ID: " + motor.getDeviceID(), motor.getMotorOutputPercent());
		}
	}

	/**
	 * Sets the power of the master motor to be 0
	 */
	public void stop() {
		set(0);
	}

	/**
	 * Sets a MotionMagic target for the master motor of a MotorGroup
	 * 
	 * @param target the target for MotionMagic mode
	 */
	public void setTarget(double target) {
		masterMotor.set(ControlMode.MotionMagic, target);
		for (IMotorController motor : this.motors) {
			SmartDashboard.putNumber(motorGroupName + " ID: " + motor.getDeviceID(), motor.getMotorOutputPercent());
		}
	}

	/**
	 * Sets all TalonSRX in a MotorGroup to a specified neutral mode.
	 * 
	 * @param neutralMode desired neutral mode (brake/coast)
	 */
	public void setNeutralMode(NeutralMode neutralMode) {
		for (IMotorController motor : this.motors) {
			motor.setNeutralMode(neutralMode);
		}
	}

	/**
	 * Sets all TalonSRX in a MotorGroup to inverted or not.
	 * 
	 * @param isInverted boolean isInverted (true/false)
	 */
	public void setInverted(boolean isInverted) {
		masterMotor.setInverted(isInverted);
		for (IMotorController motor : this.motors) {
			motor.setInverted(isInverted);
		}
	}

	public TalonSRX getMasterMotor() {
		return masterMotor;
	}

	/**
	 * This can be put in the respective subsystem for the MotorGroup. Then, the
	 * subsystem reset() method can be called in Robot class during initialization
	 * for auton/disabled/etc.
	 */
	public void reset() {
		stop();
	}
}