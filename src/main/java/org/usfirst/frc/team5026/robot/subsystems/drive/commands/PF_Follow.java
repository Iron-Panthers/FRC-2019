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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class PF_Follow extends Command {
	private CANEncoder leftEncoder;
	private CANEncoder rightEncoder;

	private EncoderFollower left;
	private EncoderFollower right;

	private double p;
	private double i;
	private double d;
	private double v;
	private double a;

	public PF_Follow(Trajectory trajectory) {
		requires(Robot.drive);
		left = new EncoderFollower(trajectory);
		right = new EncoderFollower(trajectory);
		leftEncoder = Robot.hardware.leftDriveEncoder;
		rightEncoder = Robot.hardware.rightDriveEncoder;

		SmartDashboard.putNumber("Pathfinder P", Constants.Drivebase.P);
		SmartDashboard.putNumber("Pathfinder I", Constants.Drivebase.I);
		SmartDashboard.putNumber("Pathfinder D", Constants.Drivebase.D);
		SmartDashboard.putNumber("Pathfinder V", Constants.Drivebase.V);
		SmartDashboard.putNumber("Pathfinder A", Constants.Drivebase.A);
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

		p = SmartDashboard.getNumber("Pathfinder P", Constants.Drivebase.P);
		i = SmartDashboard.getNumber("Pathfinder P", Constants.Drivebase.I);
		d = SmartDashboard.getNumber("Pathfinder P", Constants.Drivebase.D);
		v = SmartDashboard.getNumber("Pathfinder P", Constants.Drivebase.V);
		a = SmartDashboard.getNumber("Pathfinder P", Constants.Drivebase.A);
		left.configurePIDVA(p, i, d, v, a);
		right.configurePIDVA(p, i, d, v, a);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
  	protected void execute() {
		int leftPosition = (int) leftEncoder.getPosition();
		int rightPosition = (int) rightEncoder.getPosition();
		double l = left.calculate(leftPosition);
		double r = right.calculate(rightPosition);

		double gyro_heading = ((Robot.drive.getYaw() % 360) + 360) % 360;  // Assuming the gyro is giving a value in degrees
		double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees
		double angleDifference =  desired_heading - gyro_heading;
		if(Math.abs(angleDifference) > 180) {
			angleDifference = (angleDifference < 0) ? angleDifference + 360 : angleDifference - 360;
		}

		SmartDashboard.putNumber("desired angle", desired_heading);
		SmartDashboard.putNumber("angle error:", angleDifference);
		
		//TODO: Replace magic numbers
		double turn = Constants.Drivebase.PATHFINDER_TURN_SENSITIVITY * angleDifference;

		Robot.drive.set(l - turn, r + turn);
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
