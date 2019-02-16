/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.climb.commands;

import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SetupClimb extends CommandGroup {
	/**
	 * Setup Climb is used for setting up the robot by doing every task necessary
	 * before others get onto the ramps. It is used after aligning up next to the 3rd level platform
	 */
	public SetupClimb() {
		addSequential(new ExtendSuperStructurePistons());
		addSequential(new ClimbUpTo(Constants.Climb.CLIMB_FIRST_TARGET)); // Climbs in order to deploy training wheels
		addSequential(new DeployTrainingWheels());
		addSequential(new ClimbUpTo(Constants.Climb.CLIMB_SECOND_TARGET)); // Climbs in order to deploy ramps passively
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
