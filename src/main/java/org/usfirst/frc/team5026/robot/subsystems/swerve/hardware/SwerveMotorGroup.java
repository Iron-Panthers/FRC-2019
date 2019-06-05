/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.swerve.hardware;

import org.usfirst.frc.team5026.robot.util.MotorGroup;

public class SwerveMotorGroup extends MotorGroup {

	SwerveMC frontRight;
	SwerveMC frontLeft; 
	SwerveMC backRight; 
	SwerveMC backLeft;
	SwerveMC[] swerveControllers;
	
	public SwerveMotorGroup(SwerveMC frontRight, SwerveMC frontLeft, SwerveMC backRight, SwerveMC backLeft) {
		super(frontRight, frontLeft, backRight, backLeft);

		this.frontRight = frontRight;
		this.frontLeft = frontLeft;
		this.backRight = backRight;
		this.backLeft = backLeft;

		swerveControllers = new SwerveMC[] {frontRight, frontLeft, backRight, backLeft};

	}

	public void configPD(double p, double d) {
		for(SwerveMC controller : swerveControllers) {
			controller.configPD(p,d);
		}
	}

	/** 
	 * Sets swerve motors to pidf to a desired angle.
	*/
	public void set(double desiredAngle, double f) { 

		frontRight.set(desiredAngle, f);
		frontLeft.set(desiredAngle, f);
		backRight.set(desiredAngle, f);
		backLeft.set(desiredAngle, f);

	}

}