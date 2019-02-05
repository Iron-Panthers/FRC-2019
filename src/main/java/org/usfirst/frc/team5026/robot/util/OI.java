/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

import org.usfirst.frc.team5026.robot.subsystems.drive.commands.FindF;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.HubertTurnLeft;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.HubertTurnRight;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.ReverseDrive;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public JoystickWrapper stick1;
	public JoystickButton button1;
	public JoystickButton button9;
	public JoystickButton button10;
	public JoystickButton button6;


	public OI() {
		stick1 = new JoystickWrapper(Constants.Input.JOYSTICK_1_PORT);
		button1 = new JoystickButton(stick1, 1);
		button6 = new JoystickButton(stick1, 6);
		button9 = new JoystickButton(stick1, 9);
		button10 = new JoystickButton(stick1, 10);
		button9.whileHeld(new HubertTurnLeft());
		button10.whileHeld(new HubertTurnRight());
		button6.whileHeld(new FindF());
		button1.whileHeld(new ReverseDrive());
	}
}
