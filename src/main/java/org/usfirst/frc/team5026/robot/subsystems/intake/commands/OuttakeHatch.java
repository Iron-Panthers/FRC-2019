/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.intake.commands;

import org.usfirst.frc.team5026.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Add your docs here.
 */
public class OuttakeHatch extends TimedCommand {
	/**
	 * Add your docs here.
	 */
	public OuttakeHatch(double timeout) {
		super(timeout);
		requires(Robot.intake);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("OuttakeHatch");
		// Close the intake mechanism to prepare for outtake
		Robot.intake.hatchOuttake();
		// Use the hatch outtake pistons to push the hatch off (SuperStructurePistons for now)
		Robot.intake.extendHatchPistons();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Called once after timeout
	@Override
	protected void end() {
		// Retract the outtake pistons after the timeout
		Robot.intake.retractHatchPistons();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
