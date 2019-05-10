/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

import com.revrobotics.CANEncoder;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class PF_Follow extends Command {
	private CANEncoder leftEncoder;
	private CANEncoder rightEncoder;

	private EncoderFollower left;
	private EncoderFollower right;

	public PF_Follow(Trajectory trajectory) {
		requires(Robot.drive);
		left = new EncoderFollower(trajectory);
		right = new EncoderFollower(trajectory);
		leftEncoder = Robot.hardware.leftDriveEncoder;
		rightEncoder = Robot.hardware.rightDriveEncoder;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Unfortunately, Pathfinder expects ticks like from TalonSRX, not double from
		// SparkMax rotations. This might not work as intended
		left.configureEncoder((int) leftEncoder.getPosition(), (int) Constants.Drivebase.ENCODER_REVOLUTIONS_PER_WHEEL_REVOLUTION,
				Constants.Drivebase.WHEEL_DIAMETER_METERS);
		right.configureEncoder((int) rightEncoder.getPosition(), (int) Constants.Drivebase.ENCODER_REVOLUTIONS_PER_WHEEL_REVOLUTION,
				Constants.Drivebase.WHEEL_DIAMETER_METERS);

		// TODO: Replace magic numbers
		left.configurePIDVA(1.0, 0.0, 0.0, 1 / Constants.Drivebase.MAX_VELOCITY, 0);
		right.configurePIDVA(1.0, 0.0, 0.0, 1 / Constants.Drivebase.MAX_VELOCITY, 0);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
  	protected void execute() {
		int leftPosition = (int) leftEncoder.getPosition();
		int rightPosition = (int) rightEncoder.getPosition();
		double l = left.calculate(leftPosition);
		double r = right.calculate(rightPosition);

		double gyro_heading = Robot.drive.getYaw();  // Assuming the gyro is giving a value in degrees
		double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

		// This allows the angle difference to respect 'wrapping', where 360 and 0 are the same value
		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
		angleDifference = angleDifference % 360.0;
		if (Math.abs(angleDifference) > 180.0) {
			angleDifference = (angleDifference > 0) ? angleDifference - 360 : angleDifference + 360;
		} 
		//TODO: Replace magic numbers
		double turn = Constants.Drivebase.PATHFINDER_TURN_SENSITIVITY * angleDifference;

		Robot.drive.set(l + turn, r - turn);
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
