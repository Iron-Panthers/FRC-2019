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
 * FinishClimb is a completely sequential CommandGroup for doing everything
 * invloved with climbing after robots have climbed onto the ramps.
 */
public class FinishClimb extends CommandGroup {
	/**
	 * Creates a new FinishClimb command group.
	 */
	public FinishClimb() {
		addSequential(new ClimbUpTo(Constants.Climb.CLIMB_THIRD_TARGET));
		addSequential(new ClimbUpTheLastPart());
		addSequential(new TrainingWheelDriveForwardForTime(Constants.Climb.TRAINING_WHEEL_DRIVE_TIME));
		addSequential(new ClimbDownTo(Constants.Climb.CLIMB_FINAL_TARGET)); // When the robot climbs down past the
																			// platform when it is contacting it, that
																			// means that it will raise the super
																			// structure.
		addSequential(new ClimbDownTheLastPart());
	}
}
