/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class SparkMaxMotorGroup {
	private CANSparkMax masterMotor;
	private CANSparkMax[] motors;

	public SparkMaxMotorGroup(CANSparkMax masterMotor, CANSparkMax... motors) {
		this.masterMotor = masterMotor;
		this.motors = motors;
		setup(masterMotor);
		for (CANSparkMax element : motors) {
			setup(element);
		}
		// followMaster();
	}

	public void setup(CANSparkMax m_motor) {
		/**
		 * The restoreFactoryDefaults method can be used to reset the configuration
		 * parameters in the SPARK MAX to their factory default state. If no argument is
		 * passed, these parameters will not persist between power cycles
		 */
		m_motor.restoreFactoryDefaults();

		/**
		 * Parameters can be set by calling the appropriate Set method on the
		 * CANSparkMax object whose properties you want to change
		 * 
		 * Set methods will return one of three CANError values which will let you know
		 * if the parameter was successfully set: CANError.kOk CANError.kError
		 * CANError.kTimeout
		 */
		if (m_motor.setIdleMode(IdleMode.kBrake) != CANError.kOK) {
			SmartDashboard.putString("Idle Mode", "Error");
		}

		/**
		 * Similarly, parameters will have a Get method which allows you to retrieve
		 * their values from the controller
		 */
		if (m_motor.getIdleMode() == IdleMode.kCoast) {
			SmartDashboard.putString("Idle Mode", "Coast");
		} else {
			SmartDashboard.putString("Idle Mode", "Brake");
		}

		// Set ramp rate to 0
		if (m_motor.setOpenLoopRampRate(0) != CANError.kOK) {
			SmartDashboard.putString("Ramp Rate", "Error");
		}
	}

	// // Broken, do not use follow
	// private void followMaster() {
	// for (CANSparkMax motor : this.motors) {
	// // motor.follow(this.masterMotor);
	// }
	// }

	/**
	 * Sets the PercentOutput power of the master motor
	 * 
	 * @param power (should be between -1.0 and 1.0)
	 */
	public void set(double power) {
		masterMotor.set(power);
		for (CANSparkMax motor : this.motors) {
			motor.set(power);
			// SmartDashboard.putNumber(motorGroupName + " ID: " + motor.getDeviceID(),
			// motor.getMotorOutputPercent());
		}
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
