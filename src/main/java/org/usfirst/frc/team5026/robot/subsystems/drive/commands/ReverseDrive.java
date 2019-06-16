/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

/**
 * When triggered, changes the isReversed property of the drive subsystem. This
 * has the result of reversing the direction of the robot until the command is
 * interrupted or stopped.
 */
public class ReverseDrive extends Command {
	public ReverseDrive() {
		// This Command must not require a subsystem
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if(Robot.IS_BABY_PROOFED) {
			setReversed(Constants.Drivebase.BABY_PROOF_REVERSE_DRIVE);
		}
		else{
			reverse();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		if(Robot.IS_BABY_PROOFED) {
			setReversed(Constants.Drivebase.BABY_PROOF_REVERSE_DRIVE);
		}
		else{
			unReverse();
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		if(Robot.IS_BABY_PROOFED) {
			setReversed(Constants.Drivebase.BABY_PROOF_REVERSE_DRIVE);
		}
		else{
			unReverse();
		}
	}

	private void setReversed(boolean onOff){
		Robot.drive.isReversed = onOff;
	}

	private void reverse() { setReversed(true); }

	private void unReverse() { setReversed(false); }
}
