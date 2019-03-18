/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.intake.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class ManualArmMovement extends Command {
	// private double armTorque;
	private double basePower;
	private double power;

	public ManualArmMovement() {
		requires(Robot.intakeArm);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		basePower = Robot.intakeArm.getBasePower();
		double joystickY = Robot.oi.stick2.getY();					// Makes code easy to read //
		double slowPower = Constants.IntakeArm.SLOW_POWER_SCALE;   //                        //    
		double fastPower = Constants.IntakeArm.POWER_SCALE;       ///////////////////////////
		if (Math.abs(joystickY) < Constants.IntakeArm.Y_DEADZONE) {
			power = 0;
		} 
		else if (Math.abs(joystickY) < Constants.IntakeArm.SLOW_Y_DEADZONE){
			// If within slow deadzone, multiply by slow scalar
			// Power after scaling, before applying negative or positive depending on getY
			double tempSlowPower = interpolate(joystickY, Constants.IntakeArm.Y_DEADZONE, Constants.IntakeArm.SLOW_Y_DEADZONE, 0, slowPower);
			// Ensures the output correctly scales when the joystick has a negative getY
			power = Math.copySign(slowPower, joystickY);
			
		}
		else {
			double tempPower = interpolate(joystickY, Constants.IntakeArm.SLOW_Y_DEADZONE, 1, slowPower, fastPower);
			power = Math.copySign(tempPower, joystickY);
		}
		Robot.intakeArm.moveArm(basePower + power);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.intakeArm.moveArm(basePower);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.intakeArm.moveArm(basePower);
	}
	/**
	 * maps a value from within a specific range to another range
	 */
	private double interpolate(double value, double min, double max, double newMin, double newMax){
		if(max <= min || newMax <= newMin) { return value;}// the maximum values MUST be greater than the minimum values
		return (value - min) / (max - min) * (newMax - newMin) + newMin;
	}
}
