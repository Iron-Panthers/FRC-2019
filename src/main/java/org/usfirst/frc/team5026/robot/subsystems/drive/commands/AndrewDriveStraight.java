/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.PIDAndrew;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AndrewDriveStraight extends Command {

  private PIDAndrew pidAndrew = new PIDAndrew(0, 0, 0, Constants.Drivebase.F);
  private double distance;
  private double target;
  private TalonSRX encoderMotor = Robot.hardware.driveRight1;

  /**
   * Give distance in inches
   */
  public AndrewDriveStraight(double distance) {
    requires(Robot.drive);
    this.distance = distance * Constants.Drivebase.INCHES_TO_TICKS;
    pidAndrew.setCruiseSpeed(Constants.Drivebase.DRIVE_CRUISE_VELOCITY);
    pidAndrew.setMaxAccel(Constants.Drivebase.DRIVE_ACCELERATION);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.target = this.distance + this.encoderMotor.getSelectedSensorPosition();
    pidAndrew.reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double currentPos = this.encoderMotor.getSelectedSensorPosition();
    double currentSpeed = this.encoderMotor.getSelectedSensorVelocity()*10; // multiply by 10 to account for `per 100 ms to per second` conversion
    double power = pidAndrew.getMotorPower(currentPos, currentSpeed, this.target);
    Robot.drive.set(power);

    SmartDashboard.putNumber("current power", power);
    SmartDashboard.putNumber("current pos", currentPos);
    SmartDashboard.putNumber("wanted pos", currentPos + pidAndrew.getPathPosition(pidAndrew.getPathTime()));
    SmartDashboard.putNumber("current speed", currentSpeed);
    SmartDashboard.putNumber("wanted speed", pidAndrew.getPathSpeed(pidAndrew.getPathTime()));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.drive.set(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.drive.set(0);
  }
}
