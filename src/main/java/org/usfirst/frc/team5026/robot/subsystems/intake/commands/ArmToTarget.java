/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.intake.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ArmToTarget extends InstantCommand {

	private double target;

	/**
	 * Takes in heights and converts to a target angle
	 * @param targetHeight
	 * @param isFront
	 */
	public ArmToTarget(double targetHeight, boolean isFront) {
		if (!isFront) {
			this.target = 180 - (Math.asin(targetHeight / Constants.IntakeArm.ARM_LENGTH)
					* Constants.IntakeArm.RADIANS_TO_DEGREES);
		} else {
			this.target = (Math.asin(targetHeight / Constants.IntakeArm.ARM_LENGTH)
					* Constants.IntakeArm.RADIANS_TO_DEGREES);
		}
	}

	/**
	 * Takes in an angle and sets it as the target
	 * @param targetAngle
	 */
	public ArmToTarget(double targetAngle) {
		this.target = targetAngle;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.intakeArm.target = this.target;
	}

}
