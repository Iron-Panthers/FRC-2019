/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

/**
 * Add your docs here.
 */
public class SparkMaxMotorGroup {
	private CANSparkMax masterMotor;
	private CANSparkMax[] motors;

	public SparkMaxMotorGroup(CANSparkMax masterMotor, CANSparkMax... motors) {
		this.masterMotor = masterMotor;
		this.motors = motors;
		followMaster();
	}

	private void followMaster() {
		for (CANSparkMax motor : this.motors) {
			motor.follow(this.masterMotor);
		}
	}

	/**
	 * Sets the PercentOutput power of the master motor
	 * 
	 * @param power (should be between -1.0 and 1.0)
	 */
	public void set(double power) {
		masterMotor.set(power);
		// for (CANSparkMax motor : this.motors) {
		// SmartDashboard.putNumber(motorGroupName + " ID: " + motor.getDeviceID(),
		// motor.getMotorOutputPercent());
		// }
	}

	/**
	 * Sets the power of the master motor to be 0
	 */
	public void stop() {
		set(0);
	}
	/**
	 * Sets all CANSparkMax in a MotorGroup to a specified neutral mode.
	 * 
	 * @param idleMode desired idle mode (brake/coast)
	 */
	public void setIdleMode(IdleMode idleMode) {
		for (CANSparkMax motor : this.motors) {
			motor.setIdleMode(idleMode);
		}
	}

	/**
	 * Sets all CANSparkMax in a MotorGroup to inverted or not.
	 * 
	 * @param isInverted boolean isInverted (true/false)
	 */
	public void setInverted(boolean isInverted) {
		masterMotor.setInverted(isInverted);
		for (CANSparkMax motor : this.motors) {
			motor.setInverted(isInverted);
		}
	}

	public CANSparkMax getMasterMotor() {
		return masterMotor;
	}

	public double getAppliedOutput() {
		return masterMotor.getAppliedOutput(); // TODO: Check if this outputs power
	}

}
