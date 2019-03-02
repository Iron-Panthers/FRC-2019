/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
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
		// 5 is the max output voltage for the sensors
		double leftSensorValue = Robot.hardware.driveRight1.getSelectedSensorPosition() * Constants.LineFollow.SRX_TO_RIO_SENSOR_VOLTAGE_CONVERSION;
		double rightSensorValue = Robot.hardware.frontLightSensorRight.getVoltage();
		double centerLeftSensorValue = Robot.hardware.centerLeftLightSensor.getVoltage();
		double centerRightSensorValue = Robot.hardware.centerRightLightSensor.getVoltage();

		double leftMPower = Constants.LineFollow.LINEFOLLOW_INNER_REACTION_POWER * (centerLeftSensorValue + -centerRightSensorValue)
				+ Constants.LineFollow.LINEFOLLOW_REACTION_POWER * (leftSensorValue + -rightSensorValue);
		double rightMPower = Constants.LineFollow.LINEFOLLOW_INNER_REACTION_POWER * (centerRightSensorValue + centerLeftSensorValue)
				+ Constants.LineFollow.LINEFOLLOW_REACTION_POWER * (-leftSensorValue + rightSensorValue);
		Robot.drive.set(leftMPower, rightMPower);

		System.out.println("Output Current: " + Robot.hardware.rightDriveMotors.getOutputCurrent());

		;
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.drive.hasHitWall();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drive.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.drive.reset();
	}
}
