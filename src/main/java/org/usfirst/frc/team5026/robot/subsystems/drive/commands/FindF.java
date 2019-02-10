/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5026.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class FindF extends Command {
	TalonSRX masterMotor;
	int lastTicks = 0;
	long lastTime = 0;
	public FindF() {
		requires(Robot.drive);
		masterMotor = Robot.hardware.leftDriveMotors.getMasterMotor();
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
		Robot.drive.set(0.5);
		long seconds = System.nanoTime() / 1000000000;
		System.out.println(seconds); 
		int ticks = Robot.hardware.driveLeft1.getSelectedSensorPosition(0);
		long deltaTime = seconds - lastTime;
		int deltaTicks = ticks - lastTicks;
		// long velocity = deltaTicks / deltaTime;
		lastTime = seconds;
		lastTicks = ticks;
		// double f = 0.5 / velocity;
		System.out.println("ticks: " + ticks);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
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
