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
		if (Math.abs(Robot.oi.stick2.getY()) < Constants.IntakeArm.Y_DEADZONE) {
			power = 0;
		} 
		else if (Math.abs(Robot.oi.stick2.getY()) < Constants.IntakeArm.SLOW_Y_DEADZONE){
			// Power after scaling, before applying negative or positive depending on getY
			double tempPower = (Math.abs(Robot.oi.stick2.getY()) - Constants.IntakeArm.SLOW_Y_DEADZONE) * Constants.IntakeArm.SLOW_POWER_SCALE
					/ (1 - Constants.IntakeArm.SLOW_Y_DEADZONE);
			// Ensures the output correctly scales when the joystick has a negative getY
			power = Math.copySign(tempPower, Robot.oi.stick2.getY());
			
		}
		else {
			double tempPower = (Robot.oi.stick2.getY() - Constants.IntakeArm.Y_DEADZONE) * Constants.IntakeArm.POWER_SCALE
					/ (1 - Constants.IntakeArm.Y_DEADZONE);
			power = Math.copySign(tempPower, Robot.oi.stick2.getY());
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
}
