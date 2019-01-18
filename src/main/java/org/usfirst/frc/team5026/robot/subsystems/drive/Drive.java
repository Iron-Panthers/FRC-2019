/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import org.usfirst.frc.team5026.robot.subsystems.drive.commands.DriveWithJoystick;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Drive extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	// Drive Motors
	public TalonSRX leftMotor;
	public TalonSRX rightMotor;

	public Drive() {
		leftMotor = Robot.hardware.leftM;
		rightMotor = Robot.hardware.rightM;
	}

	/**
	 * Moves by using one power to set both sides to
	 * 
	 * @param power Percent Output
	 */
	public void move(double power) {
		leftMotor.set(ControlMode.PercentOutput, power);
		rightMotor.set(ControlMode.PercentOutput, power);
	}

	/**
	 * Moves by setting left power and right power seperately
	 * 
	 * @param leftPower  Percent Output
	 * @param rightPower Percent Output
	 */
	public void move(double leftPower, double rightPower) {
		leftMotor.set(ControlMode.PercentOutput, leftPower);
		rightMotor.set(ControlMode.PercentOutput, rightPower);
	}

	public void stop() {
		move(0);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
