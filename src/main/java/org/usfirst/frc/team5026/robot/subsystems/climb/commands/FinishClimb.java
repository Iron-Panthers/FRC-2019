/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.climb.commands;

import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FinishClimb extends CommandGroup {
	/**
	 * Finish Climb is for doing eveything invloved with climbing after robots have
	 * climbed onto the ramps
	 */
	public FinishClimb() {
		addSequential(new ClimbUpTo(Constants.Climb.CLIMB_THIRD_TARGET));
		addSequential(new TrainingWheelDriveForwardForTime(Constants.Climb.TRAINING_WHEEL_DRIVE_TIME));
		addSequential(new ClimbDownTo(Constants.Climb.CLIMB_FINAL_TARGET)); // When the robot climbs down past the
																			// platform when it is contacting it, that
																			// means that it will raise the super
																			// structure.
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}
}
