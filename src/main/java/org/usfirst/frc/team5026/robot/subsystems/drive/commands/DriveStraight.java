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

public class DriveStraight extends Command {
  private double targetTicks;
  private double currentTicks;
  public DriveStraight(double inches) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    targetTicks = inches / Constants.DriveStraight.TICKS_TO_INCHES;
    requires(Robot.drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.hardware.rightDriveMotors.setMotionMagic(targetTicks);
    Robot.hardware.leftDriveMotors.setMotionMagic(targetTicks);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    currentTicks = Robot.hardware.driveRight1.getSelectedSensorPosition();
    return Math.abs(currentTicks - targetTicks) < Constants.DriveStraight.ENCODER_ERROR_TOLERANCE;
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
