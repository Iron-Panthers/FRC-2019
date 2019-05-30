/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.ArcadeDrive;
import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.SwerveMC;
import org.usfirst.frc.team5026.robot.util.SwerveMath;
import org.usfirst.frc.team5026.robot.util.SwerveMotorGroup;

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
		
	}

	/**
	 * Set the drivebase to lock its swerve motors at a constant angle, 
	 * to go forward a certain amount, and to turn a certain amount
	 * 
	 * @param leftPower  the power to set for the left motor group.
	 * @param rightPower the power to set for the right motor group.
	 */
	public void set(double swerveAngle, double forward, double turn) {
		
		double innerTurnModifier = SwerveMath.getInnerTurnModifier(swerveAngle);
		double outerTurnModifier = SwerveMath.getOuterTurnModifier(swerveAngle);

		double innerRightPower = forward - innerTurnModifier * turn;
		double innerLeftPower = forward + innerTurnModifier * turn;
		double outerRightPower = forward - outerTurnModifier * turn;
		double outerLeftPower = forward + outerTurnModifier * turn;

		double[] powersList = new double[] {innerRightPower, innerLeftPower, outerRightPower, outerLeftPower};

		TalonSRX[] reorderedControllerList = SwerveMath.organizePositions(driveMotors, swerveAngle);

		for(int i = 0; i < powersList.length; i++) {
			reorderedControllerList[i].set(ControlMode.PercentOutput, powersList[i]);
		}

		//the motors will pid to the desired swerveAngle, as well as rotating at velocity proportional to
		// -turn. The latter is intended to anticipate the change in angle of the robot
		swerveMotors.set(swerveAngle, -turn * Constants.Drivebase.TURN_TO_F_RATIO);

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
		setDefaultCommand(new ArcadeDrive());
	}
}