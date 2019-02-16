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
import org.usfirst.frc.team5026.robot.subsystems.intake.commands.ArmToTarget;
import org.usfirst.frc.team5026.robot.subsystems.intake.commands.IntakeCargo;
import org.usfirst.frc.team5026.robot.subsystems.intake.commands.ManualArmMovement;
import org.usfirst.frc.team5026.robot.subsystems.intake.commands.OuttakeCargo;
import org.usfirst.frc.team5026.robot.subsystems.intake.commands.ZeroIntakeArm;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team5026.robot.subsystems.drive.commands.AlignmentSequence;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.GyroRotate;
import org.usfirst.frc.team5026.robot.util.JoystickWrapper;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public JoystickWrapper stick1;
	public JoystickWrapper stick2;
	public JoystickButton button1;
	public JoystickButton button9;
	public JoystickButton button10;
	public JoystickButton button6;

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
		// DRIVER 1
		stick1 = new JoystickWrapper(Constants.Input.JOYSTICK_1_PORT);
		stick2 = new JoystickWrapper(Constants.Input.JOYSTICK_2_PORT);
		button6 = new JoystickButton(stick1, 6);
		button9 = new JoystickButton(stick1, 9);
		button10 = new JoystickButton(stick1, 10);
		button9.whileHeld(new HubertTurnLeft());
		button10.whileHeld(new HubertTurnRight());
		button6.whileHeld(new FindF());

		// TODO Non-hardcoded ports
		// DRIVER 2
		manualArm = new JoystickButton(stick2, 1);
		intake = new JoystickButton(stick2, 2);
		outtake = new JoystickButton(stick2, 3);
		zeroIntakeAngle = new JoystickButton(stick2, 7);
		lowestHeight = new JoystickButton(stick2, 8);
		oppRocketLowHeight = new JoystickButton(stick2, 9);
		rocketLowHeight = new JoystickButton(stick2, 10);
		oppCargoShipHeight = new JoystickButton(stick2, 11);
		cargoShipHeight = new JoystickButton(stick2, 12);

		cargoShipHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.CARGO_SHIP_HEIGHT, true));
		oppCargoShipHeight.whenPressed(new ArmToTarget((Constants.IntakeArm.CARGO_SHIP_HEIGHT - Constants.IntakeArm.CARGO_DIAMETER), false));
		rocketLowHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.ROCKET_LOW_HEIGHT, true));
		oppRocketLowHeight.whenPressed(new ArmToTarget((Constants.IntakeArm.ROCKET_LOW_HEIGHT - Constants.IntakeArm.CARGO_DIAMETER), false));
		lowestHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.LOWEST_HEIGHT, true));
		intake.toggleWhenPressed(new IntakeCargo());
		outtake.toggleWhenPressed(new OuttakeCargo());
		manualArm.whileHeld(new ManualArmMovement());
		zeroIntakeAngle.whenPressed(new ZeroIntakeArm());
		button1 = new JoystickButton(stick1, 1);
		button1.whenPressed(new GyroRotate(-90));
		//AlignmentSequence alignmentSequence = new AlignmentSequence();
		//button1 = new JoystickButton(stick1, 1);
		//button1.toggleWhenPressed(alignmentSequence);
	}
}
