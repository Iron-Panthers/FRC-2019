/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class FollowLine extends Command {
  public FollowLine() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double leftMPower = 1 - Robot.hardware.frontLightSensorLeft.getVoltage()/5 + Robot.hardware.frontLightSensorRight.getVoltage()/5;
    double rightMPower = 1 + Robot.hardware.frontLightSensorLeft.getVoltage()/5 - Robot.hardware.frontLightSensorRight.getVoltage()/5;
    
    Robot.hardware.leftM.set(ControlMode.PercentOutput, leftMPower);
    Robot.hardware.rightM.set(ControlMode.PercentOutput, rightMPower);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.hardware.rightM.getBusVoltage() >= Constants.BIGWALL;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.hardware.leftM.set(ControlMode.PercentOutput, 0);
    Robot.hardware.rightM.set(ControlMode.PercentOutput, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
