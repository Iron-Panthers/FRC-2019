/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team5026.robot.Robot;

import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class PointTurn extends Command {
  double leftFrontVoltage;
  double rightFrontVoltage;
  double leftBackVoltage;
  double rightBackVoltage;
  public PointTurn() {
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
    leftFrontVoltage = Robot.hardware.frontLightSensorLeft.getVoltage();
    rightFrontVoltage = Robot.hardware.frontLightSensorRight.getVoltage();
    leftBackVoltage = Robot.hardware.backLightSensorLeft.getVoltage();
    rightBackVoltage = Robot.hardware.backLightSensorRight.getVoltage();

    Robot.hardware.leftM.set(ControlMode.PercentOutput, -(leftFrontVoltage + rightBackVoltage) + 
    (rightFrontVoltage + leftBackVoltage));
    Robot.hardware.rightM.set(ControlMode.PercentOutput, (leftFrontVoltage + rightBackVoltage) - 
    (rightFrontVoltage + leftBackVoltage));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (leftFrontVoltage + rightBackVoltage) - (rightFrontVoltage + leftBackVoltage) < Constants.ACCEPTABLE_STRAIGHTNESS;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.hardware.leftM.set(ControlMode.PercentOutput, 0);
    Robot.hardware.rightM.set(ControlMode.PercentOutput, 0);
  }
}
