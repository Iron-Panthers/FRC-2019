/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.tShirtCannon.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HoldTurretPosition extends Command {
	double error;
	double p;
	public HoldTurretPosition() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.turret);
		error = 0;
		p = Constants.TShirtCannon.TURRET_P;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		error = 0 - Robot.turret.getYaw(); // The target will always be the starting position of the turret
		Robot.turret.setTurret(p * error);
		p = SmartDashboard.getNumber("Turret P", p);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() { // This will never end because it will always try to maintain the position
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.turret.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.turret.reset();
	}
}
