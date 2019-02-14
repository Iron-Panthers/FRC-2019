/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.ArcadeDrive;
import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.MotorGroup;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The drive subsystem. This contains MotorGroups for the left and right
 * drivebase motors.
 */
public class Drive extends Subsystem {
	private MotorGroup left = Robot.hardware.leftDriveMotors;
	private MotorGroup right = Robot.hardware.rightDriveMotors;
	public boolean isReversed;


	public Drive() {
		left.setInverted(Constants.Drivebase.IS_LEFT_INVERTED);
		right.setInverted(Constants.Drivebase.IS_RIGHT_INVERTED);
		isReversed = false;
	}

	/**
	 * My powers have doubled since we last met. Set the power of MotorGroups in the
	 * drivebase.
	 * 
	 * @param leftPower  the power to set for the left motor group.
	 * @param rightPower the power to set for the right motor group.
	 */
	public void set(double leftPower, double rightPower) {
		left.set(leftPower);
		right.set(rightPower);
		SmartDashboard.putNumber("left: ", leftPower);
		SmartDashboard.putNumber("right: ", rightPower);

	}

	/**
	 * Sets the power of both sides of the robot to the same side
	 * 
	 * @param power The power to set for the left and right motor group
	 */
	public void set(double power) {
		left.set(power);
		right.set(power);
	}

	/**
	 * Things the subsystem should do at init of new phases.
	 */
	public void reset() {
		left.stop();
		right.stop();
	}

	public int getLeftEncoderTicks() {
		return left.getMasterMotor().getSelectedSensorPosition();
	}

	public int getRightEncoderTicks() {
		return right.getMasterMotor().getSelectedSensorPosition();
	}

	@Override
	public void initDefaultCommand() {
		// Pick one of the drive mode commands.
		setDefaultCommand(new ArcadeDrive());
	}
}
