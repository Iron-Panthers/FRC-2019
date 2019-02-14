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

public class ArmToTarget extends Command {

	private double target;
	private double currentError;
	private double errorSum;
	private double errorChange;
	private double lastError;
	private long lastTimeOutOfThreshold;
	private double basePower;

	public ArmToTarget(double targetHeight, boolean isFront) {
		if (!isFront) {
			this.target = 180 - (Math.asin(targetHeight / Constants.IntakeArm.ARM_LENGTH) / Constants.IntakeArm.DEGRESS_TO_RADIANS);
		} else {
			this.target = (Math.asin(targetHeight / Constants.IntakeArm.ARM_LENGTH) / Constants.IntakeArm.DEGRESS_TO_RADIANS);
		}
		requires(Robot.intakeArm);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		currentError = target - Robot.intakeArm.getCurrentAngle();
		errorChange = currentError - lastError;
		errorSum += currentError;
		lastError = currentError;
		basePower = Robot.intakeArm.getBasePower();

		double power = -1 * (Constants.IntakeArm.INTAKE_ARM_P * currentError) + (Constants.IntakeArm.INTAKE_ARM_I * errorSum)
				+ (Constants.IntakeArm.INTAKE_ARM_D * errorChange);
		
		Robot.intakeArm.moveArm(power + basePower);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		long currentTime = System.currentTimeMillis();
		if (Math.abs(currentError) > Constants.IntakeArm.ERROR_TOLERANCE) {
			lastTimeOutOfThreshold = currentTime;
			return false;
		}
		return currentTime - lastTimeOutOfThreshold > Constants.IntakeArm.ERROR_TOLERANCE_TIME;
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
