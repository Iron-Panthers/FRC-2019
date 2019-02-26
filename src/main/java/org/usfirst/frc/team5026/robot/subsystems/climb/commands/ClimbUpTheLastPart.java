/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.climb.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbUpTheLastPart extends Command {
  private double startMeasuredVelocity;
    /**
   * climbs the robot all the way up to the hard limit after it passes the encoder position specified for ClimbUpTo
   */
  public ClimbUpTheLastPart() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.climb);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    startMeasuredVelocity = Robot.climb.climbMotors.getMasterMotor().getEncoder().getVelocity();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.climb.climbWithPower(Constants.Climb.END_TOP_CLIMB_POWER_SCALAR/startMeasuredVelocity);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.climb.topLimitSwitch.get();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climb.stopClimb();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.climb.stopClimb();
  }
}
