/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.JoystickWrapper;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDrive extends Command {

	private double leftPower, rightPower;
	private JoystickWrapper stick = Robot.oi.stick1;

	public ArcadeDrive() {
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		SmartDashboard.putString("Drive mode", "Arcade drive");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		stick.update();
		rightPower = stick.findRightPower() + stick.skim(stick.findLeftPower());
		leftPower = stick.findLeftPower() + stick.skim(stick.findRightPower());
		SmartDashboard.putNumber("Right Power", rightPower);
		SmartDashboard.putNumber("Left Power", leftPower);

		// SmartDashboard.putNumber("left enc", Robot.hardware.leftDriveMotors.getMasterMotor().getSelectedSensorPosition());
		SmartDashboard.putNumber("right enc", Robot.hardware.rightDriveMotors.getMasterMotor().getSelectedSensorPosition());

		Robot.drive.set(leftPower, rightPower);
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
