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
	// private double armTorque;
	private double basePower;
	private double power;

	public ManualArmMovement() {
		requires(Robot.intakeArm);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		basePower = Robot.intakeArm.getBasePower();
		double joystickY = Robot.oi.stick2.getY();
		double slowPower = Constants.IntakeArm.SLOW_POWER_SCALE;
		double fastPower = Constants.IntakeArm.POWER_SCALE;
		double yDeadzone = Constants.IntakeArm.Y_DEADZONE;
		double slowYDeadzone = Constants.IntakeArm.SLOW_Y_DEADZONE;

		if (Math.abs(joystickY) < yDeadzone) {
			power = 0;
		} else if (Math.abs(joystickY) < slowYDeadzone) {
			double[] joystickYMinmax = { yDeadzone, slowYDeadzone };
			double[] powerMinMax = { 0, slowPower };
			
			// If within slow deadzone, multiply by slow scalar
			// Power after scaling, before applying negative or positive depending on getY
			double tempSlowPower = interpolate(joystickY, joystickYMinmax, powerMinMax);
			// Ensures the output correctly scales when the joystick has a negative getY
			power = Math.copySign(tempSlowPower, joystickY);
		} else {
			double[] joystickYMinMax = { slowYDeadzone, 1 };
			double[] powerMinMax = { slowPower, fastPower };

			double tempPower = interpolate(joystickY, joystickYMinMax, powerMinMax);
			power = Math.copySign(tempPower, joystickY);
		}
		Robot.intakeArm.autoRetractHatch();
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
		Robot.intakeArm.target = Robot.intakeArm.getCurrentAngle();
		Robot.intakeArm.moveArm(basePower);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.intakeArm.target = Robot.intakeArm.getCurrentAngle();
		Robot.intakeArm.moveArm(basePower);
	}

	/**
	 * Maps a value from within a specified range to another range.
	 */
	private double interpolate(double value, double[] minMax, double[] newMinMax) {
		double min = minMax[0], max = minMax[1], newMin = newMinMax[0], newMax = newMinMax[1];
		// If either of the maximum values are not greater than the minimum values
		if (max <= min || newMax <= newMin) {
			return value;
		}
		return (value - min) / (max - min) * (newMax - newMin) + newMin;
	}
}
