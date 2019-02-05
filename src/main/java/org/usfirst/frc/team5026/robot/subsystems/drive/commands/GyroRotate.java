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
  private double deltaDegrees;
  private double initialDegrees;
  private double currentDegrees;
  private double currentError;
  private double errorChange;
  private double errorSum;
  private double lastError;
  private int counter;
  public GyroRotate(double degrees) {
    deltaDegrees = degrees;
    initialDegrees = Robot.hardware.gyro.getFusedHeading();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    errorSum = 0;
    lastError = 0;
    currentDegrees = initialDegrees;
    counter = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    currentDegrees = Robot.hardware.gyro.getFusedHeading();
    currentError = (currentDegrees - initialDegrees) - deltaDegrees;
    System.out.println(currentError);
    errorChange = currentError - lastError;
    errorSum += currentError;
    lastError = currentError;
    double power = Constants.DriveStraight.ROTATE_F + (Constants.DriveStraight.ROTATE_P * currentError) + (Constants.DriveStraight.ROTATE_I * errorSum) + (Constants.DriveStraight.ROTATE_D * errorChange);
    Robot.drive.rotate(power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Math.abs(currentDegrees - (initialDegrees + deltaDegrees)) < Constants.DriveStraight.GYRO_ERROR_TOLERANCE){
      counter++;
    }
    else{
      counter = 0;
    }
    return counter >= 50;
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
