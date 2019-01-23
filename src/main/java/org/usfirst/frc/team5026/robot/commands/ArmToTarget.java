/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class ArmToTarget extends Command {

  private double target;
  private double currentError;
  private double errorSum;
  private double errorChange;
  private double lastError;
  private long lastTimeOutOfThreshold;

  public ArmToTarget(double targetHeight) {
    this.target = Math.asin(targetHeight)/Constants.IntakeArm.TICKS_TO_DEGREES;
    requires(Robot.intakeArm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    currentError = target - Robot.intakeArm.getCurrentAngle();
    errorChange = currentError - lastError;
    errorSum += currentError;
    lastError = currentError;

    double power = (Constants.IntakeArm.INTAKE_ARM_P * currentError)
                    + (Constants.IntakeArm.INTAKE_ARM_I * errorSum)
                    + (Constants.IntakeArm.INTAKE_ARM_D * errorChange);
    
    Robot.intakeArm.moveArm(power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    long currentTime = System.currentTimeMillis();
    if(Math.abs(currentError) > Constants.IntakeArm.ERROR_TOLERANCE) {
      lastTimeOutOfThreshold = currentTime;
      return false;
    }
    return currentTime - lastTimeOutOfThreshold > Constants.IntakeArm.ERROR_TOLERANCE_TIME;
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