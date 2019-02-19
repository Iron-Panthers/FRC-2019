/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

import java.util.ArrayList;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.JoystickWrapper;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDrive extends Command {

	private double leftPower, rightPower;
	private JoystickWrapper stick = Robot.oi.stick1;

	public ArcadeDrive() {
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		SmartDashboard.putString("Drive mode", "Arcade drive");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		stick.update();
		rightPower = stick.findRightPower() + stick.skim(stick.findLeftPower());
		leftPower = stick.findLeftPower() + stick.skim(stick.findRightPower());
		double[] powers = {leftPower, rightPower};
		powers = scalePower(powers);
		SmartDashboard.putNumber("Right Power", powers[1]);
		SmartDashboard.putNumber("Left Power", powers[0]);
		//Uses ScalePower method to scale the power of both sides if either is over 1
		Robot.drive.set(powers[0], powers[1]);

		// if (stick.getAxisCount() == 4) {
		// 	SmartDashboard.putString("Joystick Type", "Normal Joystick");
		// } else if (stick.getAxisCount() == 5) {
		// 	SmartDashboard.putString("Joystick Type", "Thrustmaster");
		// }
	}

	/**
	 * scalePower returns a correctly scaled power result if the power is greater than 1. It uses both powers, finds the highest one, if it is greater than one it divides both by that power. It returns the scaled value for the first parameter.
	 * 
	 * @param power The power you are attempting to scale
	 * @param otherPower The power of the other motor to determine the correct value to use for scaling
	 * @return Returns the scaled output of the power parameter
	 */
	public double scalePower(double power, double otherPower) {
		if (Math.abs(power) > 1.0 || Math.abs(otherPower) > 1.0){
			if (Math.abs(power) > Math.abs(otherPower)){ //Needs to check abs value to find the greater one
				return power / Math.abs(power);
			}
			return power / Math.abs(otherPower);
		}
		return power;
	}

	/**
	 * Scales an array of powers
	 * @param powers An array of powers, left power, then right power
	 * @return An array of scaled powers
	 */
	public double[] scalePower(double[] powers){
		// Checks if the array needs to be scaled
		boolean isOverOne = false;
		for (double element: powers){
			if (Math.abs(element) > 1){
				isOverOne = true;
			}
		}
		if (!isOverOne){
			return powers;
		}
		double currentMax = powers[0];
		// Finds the highest value
		for (int i = 0; i < powers.length; i++){
			if (Math.abs(powers[i]) > currentMax){
				currentMax = Math.abs(powers[i]);
			}
		}
		// Scales all values
		for (int i = 0; i < powers.length; i++){
			powers[i] /= currentMax;
		}

		return powers;
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
