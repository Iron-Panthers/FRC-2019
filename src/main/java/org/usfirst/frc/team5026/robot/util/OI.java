/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;



import org.usfirst.frc.team5026.robot.subsystems.swerve.input.RotateStick;
import org.usfirst.frc.team5026.robot.subsystems.swerve.input.StrafeStick;
import org.usfirst.frc.team5026.robot.subsystems.swerve.input.SwerveGamepad;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public SwerveGamepad gamepad;
	public StrafeStick sStick;
	public RotateStick rStick;

	public OI() {
		gamepad = new SwerveGamepad(Constants.Input.GAMEPAD_PORT, Constants.Input.INVERT_GAMEPAD_SIDES);
		sStick = new StrafeStick(0);
		rStick = new RotateStick(1);
	}
}