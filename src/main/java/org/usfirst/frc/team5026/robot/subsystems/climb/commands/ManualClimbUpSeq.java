/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.climb.commands;

import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * ManualClimbUpSeq is a  sequential CommandGroup for manually raising the robot. It will slow down automatically to prevent 
 * Killing the chain.
 */
public class ManualClimbUpSeq extends CommandGroup {
	/**
	 * Creates a new ManualClimbUpSeq command group.
	 */
	public ManualClimbUpSeq() {
		addSequential(new ClimbUpTo(Constants.Climb.CLIMB_THIRD_TARGET));
		addSequential(new ClimbUpTheLastPart());
	}
}
