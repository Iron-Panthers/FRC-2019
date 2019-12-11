/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.swerve.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.subsystems.swerve.hardware.SwerveModule;
import org.usfirst.frc.team5026.robot.subsystems.swerve.input.SwerveGamepad;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveArcadeDrive extends Command {

	public SwerveArcadeDrive() {
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		Robot.oi.sStick.update();
		Robot.oi.rStick.update();
		SmartDashboard.putNumber("desired swerve angle", Robot.oi.sStick.getSwerveAngle());
		SmartDashboard.putNumber("desired forward output", Robot.oi.sStick.getForward());

		//Robot.drive.set(Robot.oi.sStick.getSwerveAngle(), Robot.oi.sStick.getForward(), Robot.oi.rStick.getTurn());
		Robot.drive.modules[0].drive.set(ControlMode.PercentOutput, Robot.oi.rStick.getY());
		Robot.drive.modules[1].drive.set(ControlMode.PercentOutput, Robot.oi.rStick.getY());
		Robot.drive.modules[2].drive.set(ControlMode.PercentOutput, Robot.oi.rStick.getY());
		Robot.drive.modules[3].drive.set(ControlMode.PercentOutput, Robot.oi.rStick.getY());

		Robot.drive.modules[0].swerve.set(ControlMode.PercentOutput, Robot.oi.rStick.getX());
		Robot.drive.modules[1].swerve.set(ControlMode.PercentOutput, Robot.oi.rStick.getX());
		Robot.drive.modules[2].swerve.set(ControlMode.PercentOutput, Robot.oi.rStick.getX());
		Robot.drive.modules[3].swerve.set(ControlMode.PercentOutput, Robot.oi.rStick.getX());
		Robot.drive.putData();

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.drive.stop();
	}
}
