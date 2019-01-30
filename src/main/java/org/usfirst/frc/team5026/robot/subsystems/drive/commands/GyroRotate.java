/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class GyroRotate extends Command {
  private double targetDegrees;
  private double currentDegrees;
  public GyroRotate(double degrees) {
    targetDegrees = degrees;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double[] ypr = new double[3];
    Robot.hardware.gyro.getYawPitchRoll(ypr);
    currentDegrees = ypr[0];
    if(targetDegrees > currentDegrees){
      Robot.drive.rotateLeft(Constants.DriveStraight.ROTATE_POWER);
    }
    else if(targetDegrees < currentDegrees){
      Robot.drive.rotateRight(Constants.DriveStraight.ROTATE_POWER);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(currentDegrees - targetDegrees) < Constants.DriveStraight.GYRO_ERROR_TOLERANCE;
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
  }
}
