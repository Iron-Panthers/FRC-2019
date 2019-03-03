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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FollowLine extends Command {

	double lastError;
	double lastTime;

	public FollowLine() {
		requires(Robot.drive);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		lastError = Double.MAX_VALUE;
		lastTime = System.currentTimeMillis();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// 5 is the max output voltage for the sensors
		//adjusted values
		double leftSensorValue = Constants.LineFollow.LINEFOLLOW__LEFT1_DISTANCE_FROM_CENTER * Robot.hardware.frontLightSensorLeft.getVoltage();
		double rightSensorValue =  Constants.LineFollow.LINEFOLLOW__RIGHT1_DISTANCE_FROM_CENTER * Robot.hardware.frontLightSensorRight.getVoltage();
		double centerLeftSensorValue = Constants.LineFollow.LINEFOLLOW__LEFT0_DISTANCE_FROM_CENTER  * Robot.hardware.centerLeftLightSensor.getVoltage();
		double centerRightSensorValue = Constants.LineFollow.LINEFOLLOW__RIGHT0_DISTANCE_FROM_CENTER  * Robot.hardware.centerRightLightSensor.getVoltage();

		//an average of all the adjusted values. Determines the location of the line, positive is left, negative is right
		double error = (leftSensorValue + rightSensorValue + centerLeftSensorValue + centerRightSensorValue);
		double dError = error - lastError;
		if (lastError == Double.MAX_VALUE) {
			dError = 0;
		}
		lastError = error;
		double dT = (double)(System.currentTimeMillis() - lastTime) / 1000.0;
		dError = dError / dT;

		SmartDashboard.putNumber("error", error);
		SmartDashboard.putNumber("dError", dError);

		double leftMPower = Constants.LineFollow.LINEFOLLOW_BASE_POWER - Constants.LineFollow.LINEFOLLOW_P * error - Constants.LineFollow.LINEFOLLOW_D * dError;
		double rightMPower = Constants.LineFollow.LINEFOLLOW_BASE_POWER + Constants.LineFollow.LINEFOLLOW_P * error + Constants.LineFollow.LINEFOLLOW_D * dError;
		
		Robot.drive.set(leftMPower, rightMPower);
		SmartDashboard.putNumber("left motor output", leftMPower);
		SmartDashboard.putNumber("right motor output", rightMPower);


		System.out.println("Output Current: " + Robot.hardware.rightDriveMotors.getOutputCurrent());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
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
