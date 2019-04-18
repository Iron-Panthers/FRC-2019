/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.climb.commands;

import org.usfirst.frc.team5026.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class CancelClimb extends InstantCommand {
	/**
	 * A command that halts the climb when called
	 */
	public CancelClimb() {
		requires(Robot.climb);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.climb.cancelClimb();
	}
}
