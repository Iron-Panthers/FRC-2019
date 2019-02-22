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
 * Setup Climb is a sequential CommandGroup used for setting up the robot, by
 * doing every task necessary before others get onto the ramps. It is used after
 * aligning with the 3rd level platform.
 */
public class SetupClimb extends CommandGroup {
	/**
	 * Creates a new SetupClimb CommandGroup.
	 */
	public SetupClimb() {
		addSequential(new ExtendSuperStructurePistons());
		addSequential(new ClimbUpTo(Constants.Climb.CLIMB_FIRST_TARGET)); // Climbs in order to deploy training wheels
		addSequential(new DeployTrainingWheels());
		addSequential(new ClimbUpTo(Constants.Climb.CLIMB_SECOND_TARGET)); // Climbs in order to deploy ramps passively
	}
}
