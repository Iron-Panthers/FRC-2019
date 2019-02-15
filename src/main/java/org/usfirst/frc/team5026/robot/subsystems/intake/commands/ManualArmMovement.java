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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		basePower = Robot.intakeArm.getBasePower();
		if (Math.abs(Robot.oi.stick2.getY()) < Constants.IntakeArm.Y_DEADZONE) {
			power = 0;
		} else {
			power = (Robot.oi.stick2.getY() - Constants.IntakeArm.Y_DEADZONE) * Constants.IntakeArm.POWER_SCALE / (1 - Constants.IntakeArm.Y_DEADZONE);
		}
		if(Robot.intakeArm.getCurrentAngle() > 180) {
			power = 0;
		}
		SmartDashboard.putNumber("angle: ", Robot.intakeArm.getCurrentAngle());
		SmartDashboard.putNumber("basePower: ", basePower);
		SmartDashboard.putNumber("power: ", power);
		SmartDashboard.putNumber("Joystick y: ", Robot.oi.stick2.getY());
		SmartDashboard.putNumber("Motor Output: ", Robot.hardware.armMotor.getMotorOutputPercent());
		SmartDashboard.putNumber("encoder: ", Robot.hardware.armMotor.getSensorCollection().getPulseWidthPosition());
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
