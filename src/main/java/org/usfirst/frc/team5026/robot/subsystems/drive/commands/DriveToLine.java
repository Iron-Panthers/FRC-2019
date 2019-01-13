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

public class DriveToLine extends Command {

  public DriveToLine() {
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
    Robot.hardware.leftM.set(ControlMode.PercentOutput, Constants.DRIVE_TO_LINE_SPD);
    Robot.hardware.rightM.set(ControlMode.PercentOutput, Constants.DRIVE_TO_LINE_SPD);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.hardware.frontLightSensorLeft.getVoltage() > Constants.ODS_TAPE_SEEN && 
    Robot.hardware.backLightSensorRight.getVoltage() > Constants.ODS_TAPE_SEEN ||
    Robot.hardware.frontLightSensorRight.getVoltage() > Constants.ODS_TAPE_SEEN && 
    Robot.hardware.backLightSensorLeft.getVoltage() > Constants.ODS_TAPE_SEEN ;
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
