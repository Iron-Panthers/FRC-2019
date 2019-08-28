/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.tShirtCannon.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Add your docs here.
 */
public class ShootCannon extends TimedCommand {
	/**
	 * Add your docs here.
	 */
	public ShootCannon(double timeout) {
		super(timeout);
		requires(Robot.cannon);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (Robot.oi.stick1.getRawButton(4)){
			Robot.cannon.setCannonPower(Constants.TShirtCannon.CANNON_POWER);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		System.out.println("shooting");
	}

	// Called once after timeout
	@Override
	protected void end() {
		Robot.cannon.stopCannon();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.cannon.stopCannon();
	}
}
