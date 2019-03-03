/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

import org.usfirst.frc.team5026.robot.subsystems.drive.commands.AlignmentSequence;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.FollowLine;
import org.usfirst.frc.team5026.robot.util.JoystickWrapper;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public JoystickWrapper stick1;
	public JoystickButton button1;

	public OI() {
		stick1 = new JoystickWrapper(Constants.Input.JOYSTICK_1_PORT);
		button1 = new JoystickButton(stick1, 1);
		button1.whileHeld(new FollowLine());
	}
}
