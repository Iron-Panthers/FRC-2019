/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

import org.usfirst.frc.team5026.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class PF_Follow extends Command {
  public PF_Follow(Trajectory trajectory) {
	  requires(Robot.drive);
	  EncoderFollower left = new EncoderFollower(trajectory);
	  EncoderFollower right = new EncoderFollower(trajectory);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    left.configureEncoder(Robot.hardware.driveLeft1.getEncoderPosition(), Constants.drivebase.TICKS_PER_WHEEL_REVOLUTION, Constants.drivebase.WHEEL_DIAMETER_METERS);
    right.configureEncoder(Robot.hardware.driveRight1.getEncoderPosition(), Constants.drivebase.TICKS_PER_WHEEL_REVOLUTION, Constants.drivebase.WHEEL_DIAMETER_METERS);

    left.configurePIDVA(1.0, 0.0, 0.0, 1 / Constants.MAX_VELOCITY, 0);
    right.configurePIDVA(1.0, 0.0, 0.0, 1 / Constants.MAX_VELOCITY, 0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double encoder_position_left = Robot.hardware.driveLeft1.getEncoderPosition();
    double encoder_position_right = Robot.hardware.driveRight1.getEncoderPosition();
	  double l = left.calculate(encoder_position_left);
    double r = right.calculate(encoder_position_right);

    double gyro_heading = ... your gyro code here ...    // Assuming the gyro is giving a value in degrees
    double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

    // This allows the angle difference to respect 'wrapping', where 360 and 0 are the same value
    double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    angleDifference = angleDifference % 360.0;
    if (Math.abs(angleDifference) > 180.0) {
      angleDiff = (angleDifference > 0) ? angleDifference - 360 : angleDiff + 360;
    } 

    double turn = 0.8 * (-1.0/80.0) * angleDifference;

    Robot.drive.setLeftMotors(l + turn);
    Robot.drive.setRightMotors(r - turn);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
