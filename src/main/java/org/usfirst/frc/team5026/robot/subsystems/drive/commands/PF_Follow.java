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
import jaci.pathfinder.modifiers.TankModifier;

public class PF_Follow extends Command {
	EncoderFollower left;
	EncoderFollower right;
	CANEncoder leftDriveEncoder;
	CANEncoder rightDriveEncoder;

	public PF_Follow(Trajectory trajectory) {
		requires(Robot.drive);
		left = new EncoderFollower(trajectory);
		right = new EncoderFollower(trajectory);
		leftDriveEncoder = Robot.hardware.leftDriveEncoder;
		rightDriveEncoder = Robot.hardware.rightDriveEncoder;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		int leftEncPosition = (int) Robot.hardware.driveLeft1.getEncoder().getPosition();
		int rightEncPosition = (int) Robot.hardware.driveRight1.getEncoder().getPosition();

		left.configureEncoder(leftEncPosition, (int) Constants.Drivebase.TICKS_PER_WHEEL_REVOLUTION,
				(int) Constants.Drivebase.WHEEL_DIAMETER_METERS);
		right.configureEncoder(rightEncPosition, (int) Constants.Drivebase.TICKS_PER_WHEEL_REVOLUTION,
				(int) Constants.Drivebase.WHEEL_DIAMETER_METERS);

		left.configurePIDVA(Constants.PathfinderConstants.PATHFINDER_KP, Constants.PathfinderConstants.PATHFINDER_KI,
				Constants.PathfinderConstants.PATHFINDER_KD, 1 / Constants.Drivebase.MAX_VELOCITY,
				Constants.PathfinderConstants.PATHFINDER_A);

		right.configurePIDVA(Constants.PathfinderConstants.PATHFINDER_KP, Constants.PathfinderConstants.PATHFINDER_KI,
				Constants.PathfinderConstants.PATHFINDER_KD, 1 / Constants.Drivebase.MAX_VELOCITY,
				Constants.PathfinderConstants.PATHFINDER_A);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double encoder_position_left = leftDriveEncoder.getPosition();
		double encoder_position_right = rightDriveEncoder.getPosition();
		double l = left.calculate((int) encoder_position_left);
		double r = right.calculate((int) encoder_position_right);

		double gyro_heading = Robot.hardware.gyro.getAbsoluteCompassHeading(); // Assuming the gyro is giving a value in
																				// degrees
		double desired_heading = Pathfinder.r2d(left.getHeading()); // Should also be in degrees

		// This allows the angle difference to respect 'wrapping', where 360 and 0 are
		// the same value
		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
		angleDifference = angleDifference % 360.0;
		if (Math.abs(angleDifference) > 180.0) {
			angleDifference = (angleDifference > 0) ? angleDifference - 360 : angleDifference + 360;
		}

		double turn = 0.8 * (-1.0 / 80.0) * angleDifference;

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
		Robot.drive.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
