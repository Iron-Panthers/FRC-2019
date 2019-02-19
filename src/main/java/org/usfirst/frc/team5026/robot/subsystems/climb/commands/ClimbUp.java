/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.climb.commands;

import org.usfirst.frc.team5026.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbUp extends Command {
	/**
	 * A command which climbs the robot up until interrupted.
	 */
	public ClimbUp() {
		requires(Robot.climb);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.climb.climbUp();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.climb.stopClimb();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.climb.stopClimb();
	}
}
