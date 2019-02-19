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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FindF extends Command {

	private int prevTick;
	private double prevTPS = 0;
	private long prevTime = 0;
	private double totalTPS = 0;
	private double totalTA = 0;
	private double prevAngle;
	private double prevDPS = 0;
	private double totalDPS = 0;
	private double totalDA = 0;
	private int tickCount = 0;

	public FindF() {
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		prevTick = 0;
		prevTPS = 0;
		prevTime = 0;
		totalTPS = 0;
		totalTA = 0;
		prevAngle = 0;
		prevDPS = 0;
		totalDPS = 0;
		totalDA = 0;
		tickCount = 0;

		prevTime = System.currentTimeMillis();
		prevTick = Robot.hardware.driveRight1.getSelectedSensorPosition();
		prevTPS = 0;
		prevAngle = Robot.hardware.gyro.getFusedHeading();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.drive.set(0.5);
		int currentTick = Robot.hardware.driveRight1.getSelectedSensorPosition();
		SmartDashboard.putNumber("current tick", currentTick);
		long currentTime = System.currentTimeMillis();
		double dT = (double) (currentTime - prevTime);
		dT /= 1000;

		double currentTPS = (currentTick - prevTick)/dT;
		double ticksAccel = (currentTPS - prevTPS)/dT;
		prevTick = currentTick;
		prevTPS = currentTPS;
		SmartDashboard.putNumber("dt", dT);
		tickCount++;
		//if(Math.abs(ticksAccel) < 1000){
			totalTPS += currentTPS;
			SmartDashboard.putNumber("total tps", totalTPS);
			SmartDashboard.putNumber("ticks per second avg", totalTPS/tickCount);
			// System.out.println(totalTPS/tickCount);
		//} else {
			totalTA += ticksAccel;
			SmartDashboard.putNumber("ticks per second per second", ticksAccel);
			SmartDashboard.putNumber("ticks per second per second avg", totalTA/tickCount);
			// System.out.println(totalTA/tickCount);
		//}
		prevTime = System.currentTimeMillis();

		// double currentAngle = Robot.hardware.gyro.getFusedHeading();
		// double currentDPS = (currentAngle - prevAngle)/dT;
		// prevAngle = currentAngle;
		// double dDPS = currentDPS - prevDPS;
		// double degreesAccel = (dDPS)/dT;
		// prevDPS = currentDPS;
		// if (Math.abs(degreesAccel) < 1) {
		// 	totalDPS += currentDPS;
		// 	System.out.println(totalDPS/tickCount);
		// } else {
		// 	totalDA += degreesAccel;
		// 	System.out.println(totalDA/tickCount);
		// }
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
