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
import org.usfirst.frc.team5026.robot.util.GearState;
import org.usfirst.frc.team5026.robot.util.SparkMaxMotorGroup;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The drive subsystem. This contains MotorGroups for the left and right
 * drivebase motors.
 */
public class Drive extends Subsystem {
	private SparkMaxMotorGroup left = Robot.hardware.leftDriveMotors;
	private SparkMaxMotorGroup right = Robot.hardware.rightDriveMotors;
	public Solenoid gearShift = Robot.hardware.gearShift;
	public GearState state;
	public boolean isReversed;

	/**
	 * Create the drivebase subsystem. This sets the inversion status of the left
	 * and right drive motorgroups to values specified in constants.
	 */
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
		SmartDashboard.putNumber("Drive -- Set left power: ", leftPower);
		SmartDashboard.putNumber("Drive -- Set right power: ", rightPower);
	}

	/**
	 * Sets the power of both sides of the robot to the same side
	 * 
	 * @param power The power to set for the left and right motor group
	 */
	public void set(double power) {
		left.set(power);
		right.set(power);
		SmartDashboard.putNumber("Drive -- Set left power: ", power);
		SmartDashboard.putNumber("Drive -- Set right power: ", power);
	}

	/**
	 * Things the subsystem should do at init of new phases.
	 */
	public void reset() {
		left.stop();
		right.stop();
	}

	/**
	 * @return The velocity of the motor in RPM
	 */
	public double getLeftVelocity(){
		return left.getMasterMotor().getEncoder().getVelocity();
	}

	/**
	 * @return The velocity of the motor in RPM
	 */
	public double getRightVelocity(){
		return right.getMasterMotor().getEncoder().getVelocity();
	}
	/**
	 * @return the encoder position of the left encoder, in encoder revolutions.
	 */
	public double getLeftEncoderRevolutions() {
		return left.getEncoderPosition();
	}

	/**
	 * @return the encoder position of the right encoder, in encoder revolutions.
	 */
	public double getRightEncoderRevolutions() {
		return right.getEncoderPosition();
	}

	/**
	 * Shift the drivebase to low gear.
	 */
	public void shiftLow() {
		state = GearState.HIGH; // Tested 2/19/2019
		gearShift.set(true);
	}

	/**
	 * Shift the drivebase to high gear.
	 */
	public void shiftHigh() {
		state = GearState.LOW; // Tested 2/19/2019
		gearShift.set(false);
	}

	@Override
	public void initDefaultCommand() {
		// Pick one of the drive mode commands.
		setDefaultCommand(new ArcadeDrive());
	}
}