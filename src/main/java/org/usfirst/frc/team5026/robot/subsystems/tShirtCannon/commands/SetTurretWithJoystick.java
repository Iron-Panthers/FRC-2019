/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.tShirtCannon.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class SetTurretWithJoystick extends Command {
  public SetTurretWithJoystick() {
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
	  int invertMultiplier = (Constants.TShirtCannon.TURRET_INVERTED) ? -1 : 1;
	  Robot.hardware.turretMotor.set(ControlMode.PercentOutput, Robot.oi.stick1.getZ() * invertMultiplier);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
	  Robot.turret.setTurret(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
	  Robot.turret.setTurret(0);
  }
}
