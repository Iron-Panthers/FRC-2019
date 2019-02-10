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
import org.usfirst.frc.team5026.robot.commands.ArmToTarget;
import org.usfirst.frc.team5026.robot.commands.IntakeCargo;
import org.usfirst.frc.team5026.robot.commands.ManualArmMovement;
import org.usfirst.frc.team5026.robot.commands.OuttakeCargo;
import org.usfirst.frc.team5026.robot.commands.ZeroIntakeArm;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);
  public JoystickWrapper stick1;
	public JoystickButton button1;
	public JoystickButton button9;
	public JoystickButton button10;
	public JoystickButton button6;
  
	public Joystick joystick;
	JoystickButton cargoShipHeight;
	JoystickButton oppCargoShipHeight;
	JoystickButton rocketLowHeight;
	JoystickButton oppRocketLowHeight;
	JoystickButton lowestHeight;
	JoystickButton intake;
	JoystickButton outtake;
	JoystickButton manualArm;
	JoystickButton zeroIntakeAngle;
	
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
    
		joystick = new Joystick(1);

		//TODO Ports
		cargoShipHeight = new JoystickButton(joystick, 6);
		oppCargoShipHeight = new JoystickButton(joystick, 7);
		rocketLowHeight = new JoystickButton(joystick, 8);
		oppRocketLowHeight = new JoystickButton(joystick, 9);
		lowestHeight = new JoystickButton(joystick, 3);
		intake = new JoystickButton(joystick, 5);
		outtake = new JoystickButton(joystick, 4);
		manualArm = new JoystickButton(joystick, 1);
		zeroIntakeAngle = new JoystickButton(joystick, 2);


		cargoShipHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.CARGO_SHIP_HEIGHT));
		oppCargoShipHeight.whenPressed(new ArmToTarget(-(Constants.IntakeArm.CARGO_SHIP_HEIGHT - Constants.IntakeArm.CARGO_DIAMETER)));
		rocketLowHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.ROCKET_LOW_HEIGHT));
		oppRocketLowHeight.whenPressed(new ArmToTarget(-(Constants.IntakeArm.ROCKET_LOW_HEIGHT - Constants.IntakeArm.CARGO_DIAMETER)));
		lowestHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.LOWEST_HEIGHT));
		intake.toggleWhenPressed(new IntakeCargo());
		outtake.toggleWhenPressed(new OuttakeCargo());
		manualArm.whileHeld(new ManualArmMovement());
		zeroIntakeAngle.whenPressed(new ZeroIntakeArm());
	}

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

}
