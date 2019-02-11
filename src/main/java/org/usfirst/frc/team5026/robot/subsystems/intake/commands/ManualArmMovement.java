/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.intake.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class ManualArmMovement extends Command {

	//private double armTorque;
	private double basePower;
	private double power;

	public ManualArmMovement() {
		requires(Robot.intakeArm);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		//armTorque = Robot.intakeArm.getCurrentTorque();
		// basePower = (armTorque / Constants.IntakeArm.INTAKE_ARM_MOTOR_MAX_TORQUE);
		basePower = Constants.IntakeArm.STALL_TORQUE_COEFFICIENT * Math.cos(Robot.intakeArm.getCurrentAngle() * (Math.PI / 180));
		// power = basePower + Robot.oi.joystick.getY();
		if (Math.abs(Robot.oi.stick2.getY()) < 0.1) {
			power = 0;
		} else {
			power = (Robot.oi.stick2.getY() - 0.1) * 0.7 / 0.9;
		}
		Robot.intakeArm.moveArm(basePower + power);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.intakeArm.moveArm(basePower);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.intakeArm.moveArm(basePower);
	}
}
