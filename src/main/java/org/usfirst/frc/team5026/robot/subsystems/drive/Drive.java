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

/**
 * The drive subsystem. This contains MotorGroups for the left and right
 * drivebase motors.
 */
public class Drive extends Subsystem {
	private MotorGroup left = Robot.hardware.leftDriveMotors;
	private MotorGroup right = Robot.hardware.rightDriveMotors;

	public Drive() {
		left.setInverted(Constants.Drivebase.IS_LEFT_INVERTED);
		right.setInverted(Constants.Drivebase.IS_RIGHT_INVERTED);
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
	}

	/**
	 * Sets both sides to the same power
	 * 
	 * @param power Power to set both sides to
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

	@Override
	public void initDefaultCommand() {
		// Pick one of the drive mode commands.
		setDefaultCommand(new ArcadeDrive());
	}

	// Line Follow Methods
	public boolean isLine() {
		return Robot.hardware.frontLightSensorLeft.getVoltage() > Constants.LineFollow.ODS_TAPE_SEEN
				|| Robot.hardware.frontLightSensorRight.getVoltage() > Constants.LineFollow.ODS_TAPE_SEEN
				|| Robot.hardware.centerLightSensor.getVoltage() > Constants.LineFollow.ODS_TAPE_SEEN;
	}

	public boolean hasHitWall() {
		return (right.getOutputCurrent() > Constants.LineFollow.BIGWALL)
				|| (left.getOutputCurrent() > Constants.LineFollow.BIGWALL);
	}
}