/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

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
		double scaledRight = scalePower(rightPower, leftPower);
		double scaledLeft = scalePower(leftPower, rightPower);
		SmartDashboard.putNumber("Right Power", scaledRight);
		SmartDashboard.putNumber("Left Power", scaledLeft);
		//Uses ScalePower method to scale the power of both sides if either is over 1
		Robot.drive.set(scaledLeft, scaledRight);
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
			return power / Math.max(power, otherPower);
		}
		return power;
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
