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

public class DriveShift extends Command {
	
	public DriveShift() {
		// This command must not require a subsystem
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		if (!Constants.Drivebase.IS_BABY_PROOFED){
			Robot.drive.shiftLow();
			System.out.println("Shift low!");
		}
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		if (!Constants.Drivebase.IS_BABY_PROOFED){
			System.out.println("Shift high");
			Robot.drive.shiftHigh();
		}
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		if (!Constants.Drivebase.IS_BABY_PROOFED){
			System.out.println("Shift high!");
			Robot.drive.shiftHigh();
		}
	}
}
