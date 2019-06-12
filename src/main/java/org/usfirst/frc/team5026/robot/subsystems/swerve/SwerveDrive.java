/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.subsystems.swerve.commands.SwerveArcadeDrive;
import org.usfirst.frc.team5026.robot.subsystems.swerve.hardware.SwerveMotorGroup;
import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.SuperiorGyro;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The swerve drive subsystem. This contains a SwerveMotorGroup and 4 drive motors.
 */
public class SwerveDrive extends Subsystem {

	SwerveMotorGroup swerveMotors;
	TalonSRX frontRight;
	TalonSRX frontLeft;
	TalonSRX backRight;
	TalonSRX backLeft;
	TalonSRX[] driveMotors;

	SuperiorGyro gyro;

	/**
	 * Create the Swerve drive subsystem.
	 */
	public SwerveDrive() {
		swerveMotors = Robot.hardware.swerveMotors;

		frontRight = Robot.hardware.driveFrontRight;
		frontLeft = Robot.hardware.driveFrontLeft;
		backRight = Robot.hardware.driveBackRight;
		backLeft = Robot.hardware.driveBackLeft;
		driveMotors = new TalonSRX[] {frontRight, frontLeft, backRight, backLeft};

		gyro = Robot.hardware.gyro;
		
	}

	/**
	 * Set the drivebase to lock its swerve motors at a constant angle, 
	 * to go forward a certain amount, and to turn a certain amount.
	 * A positive turn value will result in turning right.
	 * 
	 * @param absoluteSwerveAngle should be between -180 and 180
	 * @param forward the amount to drive in the direction of absoluteSwerveAngle
	 * @param turn the amount to turn
	 * 
	 */
	public void set(double absoluteSwerveAngle, double forward, double turn) {
		//calculate desired swerve angle relative to the robot, from gyro heading and desired absolute
		//swerve angle (which is relative to the field)
		double relativeSwerveAngle = SwerveMath.getAngleDifference(gyro.getBoundedYaw(), absoluteSwerveAngle);
		
		//Find the weight "inner" and "outer" wheels should give to turning.
		//When tuned correctly, should reduce wheel slip due to running motors 
		//against each other, while still turning at high speed.
		double innerTurnModifier = SwerveMath.getInnerTurnModifier(relativeSwerveAngle);
		double outerTurnModifier = SwerveMath.getOuterTurnModifier(relativeSwerveAngle);

		//calculate powers for each drive motor. These are not absolute, they 
		//are relative to swerveAngle
		double innerRightPower = SwerveMath.boundBelowOne(forward - innerTurnModifier * turn);
		double innerLeftPower = SwerveMath.boundBelowOne(forward + innerTurnModifier * turn);
		double outerRightPower = SwerveMath.boundBelowOne(forward - outerTurnModifier * turn);
		double outerLeftPower = SwerveMath.boundBelowOne(forward + outerRightPower * turn);

		double[] powersList = new double[] {innerRightPower, innerLeftPower, outerRightPower, outerLeftPower};

		//assign each drive motor to a specific spot in the list based on its position
		//(inner or outer right or left) relative to swerveAngle
		TalonSRX[] reorderedControllerList = SwerveMath.organizePositions(driveMotors, relativeSwerveAngle);

		//set drive motors
		for(int i = 0; i < 4; i++) {
			reorderedControllerList[i].set(ControlMode.PercentOutput, powersList[i]);
		}

		//the swerve motors will PID to the desired swerveAngle, as well as rotating at velocity proportional to
		// -turn. The latter is intended to anticipate the change in angle of the robot
		swerveMotors.set(relativeSwerveAngle, -turn * Constants.Swerve.TURN_TO_F_RATIO);

	}

	/**
	 * Stop driving.
	 */
	public void stop() {
		swerveMotors.stop();
		frontRight.set(ControlMode.PercentOutput, 0);
		frontLeft.set(ControlMode.PercentOutput, 0);
		backRight.set(ControlMode.PercentOutput, 0);
		backLeft.set(ControlMode.PercentOutput, 0);

	}

	@Override
	public void initDefaultCommand() {
		// Pick one of the drive mode commands.
		setDefaultCommand(new SwerveArcadeDrive());
	}
}