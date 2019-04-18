package org.usfirst.frc.team5026.robot.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class MotorGroup {
	private TalonSRX masterMotor;
	private IMotorController[] motors;

	/**
	 * Creates a new MotorGroup.
	 * <p>
	 * MotorGroup is a flexible-size grouping of CTRE motor controllers.
	 * <p>
	 * The supplied masterMotor must be a TalonSRX. The other motor controllers must
	 * implement IMotorController.
	 * <p>
	 * This class is currently not in use, and exists only for if we decide to bring
	 * back CTRE motor controllers.
	 * 
	 * @param masterMotor the master motor controller of the MotorGroup. Must be a
	 *                    TalonSRX.
	 * @param motors      other motor controllers to include in the MotorGroup.
	 */
	public MotorGroup(TalonSRX masterMotor, IMotorController... motors) {
		this.masterMotor = masterMotor;
		this.motors = motors;
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
	 * @param kF the feed forward term
	 */
	public void configPID(double kP, double kI, double kD, double kF) {
		masterMotor.config_kP(0, kP);
		masterMotor.config_kI(0, kI);
		masterMotor.config_kD(0, kD);
		masterMotor.config_kF(0, kF);
	}

	/**
	 * Sets the PercentOutput power of the master motor
	 * 
	 * @param power (should be between -1.0 and 1.0)
	 */
	public void set(double power) {
		masterMotor.set(ControlMode.PercentOutput, power);
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
	}

	/**
	 * Sets all TalonSRX in a MotorGroup to a specified neutral mode.
	 * 
	 * @param neutralMode desired neutral mode (brake/coast)
	 */
	public void setNeutralMode(NeutralMode neutralMode) {
		masterMotor.setNeutralMode(neutralMode);
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