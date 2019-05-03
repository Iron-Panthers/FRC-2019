/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

import org.usfirst.frc.team5026.robot.subsystems.climb.commands.CancelClimb;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.ClimbDown;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.ClimbUp;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.ClimbWithJoystick;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.ExtendSuperStructurePistons;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.RetractSuperStructurePistons;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.TrainingWheelsDriveForward;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.DriveShift;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.ReverseDrive;
import org.usfirst.frc.team5026.robot.subsystems.tShirtCannon.commands.MoveTurret;
import org.usfirst.frc.team5026.robot.subsystems.tShirtCannon.commands.ShootCannon;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	// The two Joysticks for tele-operated input
	public JoystickWrapper stick1, stick2, stick3;

	// ALl of the following buttons will belong to DRIVER 1
	JoystickButton reverseDrive;
	JoystickButton shiftGearLow;
	JoystickButton altClimbDown; // Hubert wants ClimbDown to be accessible while driving
	JoystickButton fireCannon;

	// All of the following buttons will belong to DRIVER 2
	JoystickButton moveTurret;

	// All of the following buttons will belong to the Climb Joystick
	JoystickButton climbWithJoystick;
	JoystickButton extendSuperStructurePistons, retractSuperStructurePistons;
	JoystickButton climbUp, climbDown;
	JoystickButton trainingWheelsForward, slowTrainingWheelsForward;
	JoystickButton cancelClimb;

	public OI() {
		// Create the Joysticks
		stick1 = new JoystickWrapper(Constants.Input.JOYSTICK_1_PORT);
		stick2 = new JoystickWrapper(Constants.Input.JOYSTICK_2_PORT);
		stick3 = new JoystickWrapper(Constants.Input.JOYSTICK_3_PORT);

		// Create the buttons for driver 1
		reverseDrive = new JoystickButton(stick1, Constants.Input.REVERSE_DRIVE_BUTTON);
		shiftGearLow = new JoystickButton(stick1, Constants.Input.SHIFT_GEAR_LOW_BUTTON);
		altClimbDown = new JoystickButton(stick1, Constants.Input.ALT_CLIMB_DOWN_BUTTON);
		fireCannon = new JoystickButton(stick1, Constants.Input.FIRE_CANNON_BUTTON);

		// Assign commands to each of the buttons for driver 1
		reverseDrive.whileHeld(new ReverseDrive());
		shiftGearLow.whileHeld(new DriveShift());
		altClimbDown.whileHeld(new ClimbDown());
		fireCannon.whenPressed(new ShootCannon(Constants.TShirtCannon.CANNON_FIRE_TIME));

		// Assign commands to each of the buttons for driver 2
		moveTurret = new JoystickButton(stick2, Constants.Input.MOVE_TURRET_BUTTON);
		moveTurret.whileHeld(new MoveTurret());
		//hatchHoldingHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.HATCH_HOLDING_HEIGHT, true));

		// Construct Buttons for Climb Joystick 3
		slowTrainingWheelsForward = new JoystickButton(stick3, Constants.Input.TRAINING_WHEELS_SLOW_FORWARD_BUTTON);
		climbWithJoystick = new JoystickButton(stick3, Constants.Input.CLIMB_WITH_JOYSTICK);
		extendSuperStructurePistons = new JoystickButton(stick3, Constants.Input.EXTEND_SUPER_STRUCURE_PISTONS_BUTTON);
		climbUp = new JoystickButton(stick3, Constants.Input.CLIMB_UP_BUTTON);
		climbDown = new JoystickButton(stick3, Constants.Input.CLIMB_DOWN_BUTTON);
		retractSuperStructurePistons = new JoystickButton(stick3,
				Constants.Input.RETRACT_SUPER_STRUCTURE_PISTONS_BUTTON);
		trainingWheelsForward = new JoystickButton(stick3, Constants.Input.TRAINING_WHEELS_FORWARD_BUTTON);
		cancelClimb = new JoystickButton(stick3, Constants.Input.CANCEL_CLIMB_BUTTON);

		// Assign commands to each of the button for Climb Joystick
		// Reverse Training Wheels does not need to be bound, functionality in trainingWheelsForward command
		climbWithJoystick.whileHeld(new ClimbWithJoystick());
		extendSuperStructurePistons.whenPressed(new ExtendSuperStructurePistons());
		climbUp.whileHeld(new ClimbUp());
		climbDown.whileHeld(new ClimbDown());
		retractSuperStructurePistons.whenPressed(new RetractSuperStructurePistons());
		trainingWheelsForward.whileHeld(new TrainingWheelsDriveForward(Constants.Climb.TRAINING_WHEEL_FORWARD_SPEED));
		slowTrainingWheelsForward.whileHeld(new TrainingWheelsDriveForward(Constants.Climb.TRAINING_WHEEL_SLOW_FORWARD_SPEED));
		cancelClimb.whenPressed(new CancelClimb());
	}
}
