package org.usfirst.frc.team5026.robot.subsystems.tShirtCannon;
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5026.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Cannon extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private TalonSRX cannonMotor;
	private DoubleSolenoid solenoidForPressurizing; // This is just so that the compressor runs

	public Cannon() {
		cannonMotor = Robot.hardware.cannonMotor;
		this.solenoidForPressurizing = Robot.hardware.solenoidForPressurizing;
	}

	public void setCannonPower(double power) {
		cannonMotor.set(ControlMode.PercentOutput, power);
	}

	public void stopCannon() {
		cannonMotor.set(ControlMode.PercentOutput, 0);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
