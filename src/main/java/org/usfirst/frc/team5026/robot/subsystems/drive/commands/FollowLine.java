/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class FollowLine extends Command {
	public FollowLine() {
		requires(Robot.drive);
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
		//5 is the max output voltage for the sensors
		double leftMPower = Constants.LineFollow.LINEFOLLOW_BASE_POWER
				+ Constants.LineFollow.LINEFOLLOW_REACTION_POWER * (-Robot.hardware.frontLightSensorLeft.getVoltage() / 5
						+ Robot.hardware.frontLightSensorRight.getVoltage() / 5);
		double rightMPower = Constants.LineFollow.LINEFOLLOW_BASE_POWER
				+ Constants.LineFollow.LINEFOLLOW_REACTION_POWER * (Robot.hardware.frontLightSensorLeft.getVoltage() / 5
						- Robot.hardware.frontLightSensorRight.getVoltage() / 5);

		Robot.drive.move(leftMPower, rightMPower);

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.drive.hasHitWall();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.drive.stop();
	}
}
