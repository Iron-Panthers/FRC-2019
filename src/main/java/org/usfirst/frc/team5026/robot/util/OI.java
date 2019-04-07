/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

import org.usfirst.frc.team5026.robot.subsystems.climb.commands.*;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.*;
import org.usfirst.frc.team5026.robot.subsystems.intake.commands.*;

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
	JoystickButton hubertOuttake;
	JoystickButton hubertFastOuttake;
	JoystickButton hubertSlowOuttake;
	JoystickButton altClimbDown; // Hubert wants ClimbDown to be accessible while driving

	// All of the following buttons will belong to DRIVER 2
	JoystickButton lowestHeight;
	JoystickButton hatchHeight, hatchHoldingHeight;
	JoystickButton cargoShipHeight, oppCargoShipHeight;
	JoystickButton rocketLowHeight;
	JoystickButton intake, outtake;
	JoystickButton hatchIntake, hatchOuttake;
	JoystickButton manualArm;
	JoystickButton zeroIntakeAngle;

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
		hubertOuttake = new JoystickButton(stick1, Constants.Input.HUBERT_OUTTAKE_BUTTON);
		hubertFastOuttake = new JoystickButton(stick1, Constants.Input.HUBERT_FAST_OUTTAKE_BUTTON);
		hubertSlowOuttake = new JoystickButton(stick1, Constants.Input.HUBERT_SLOW_OUTTAKE_BUTTON);
		altClimbDown = new JoystickButton(stick1, Constants.Input.ALT_CLIMB_DOWN_BUTTON);

		// Assign commands to each of the buttons for driver 1
		reverseDrive.whileHeld(new ReverseDrive());
		shiftGearLow.whileHeld(new DriveShift());
		hubertOuttake.whileHeld(new OuttakeCargo(Constants.IntakeArm.OUTTAKE_POWER));
		hubertFastOuttake.whileHeld(new OuttakeCargo(Constants.IntakeArm.FAST_OUTTAKE_POWER));
		hubertSlowOuttake.whileHeld(new OuttakeCargo(Constants.IntakeArm.SLOW_OUTTAKE_POWER));
		altClimbDown.whileHeld(new ClimbDown());

		// Create the buttons for driver 2
		manualArm = new JoystickButton(stick2, Constants.Input.MANUAL_ARM_BUTTON);
		intake = new JoystickButton(stick2, Constants.Input.INTAKE_BUTTON);
		outtake = new JoystickButton(stick2, Constants.Input.OUTTAKE_BUTTON);
		hatchIntake = new JoystickButton(stick2, Constants.Input.INTAKE_HATCH_BUTTON);
		hatchOuttake = new JoystickButton(stick2, Constants.Input.OUTTAKE_HATCH_BUTTON);
		zeroIntakeAngle = new JoystickButton(stick2, Constants.Input.ZERO_INTAKE_ARM_BUTTON);
		lowestHeight = new JoystickButton(stick2, Constants.Input.LOWEST_HEIGHT_BUTTON);
		hatchHoldingHeight = new JoystickButton(stick2, Constants.Input.HATCH_HOLDING_HEIGHT_BUTTON);
		rocketLowHeight = new JoystickButton(stick2, Constants.Input.ROCKET_LOW_HEIGHT_BUTTON);
		oppCargoShipHeight = new JoystickButton(stick2, Constants.Input.OPP_CARGO_SHIP_HEIGHT_BUTTOM);
		cargoShipHeight = new JoystickButton(stick2, Constants.Input.CARGO_SHIP_HEIGHT_BUTTON);

		// Assign commands to each of the buttons for driver 2
		hatchHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.HATCH_HEIGHT, true));
		hatchHoldingHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.HATCH_HOLDING_HEIGHT, true));
		cargoShipHeight.whenPressed(new ArmToTarget(
				Constants.IntakeArm.CARGO_SHIP_HEIGHT - Constants.IntakeArm.CARGO_SHIP_FRONT_BACK_ADJUST, true));
		oppCargoShipHeight.whenPressed(
				new ArmToTarget((Constants.IntakeArm.CARGO_SHIP_HEIGHT - Constants.IntakeArm.CARGO_DIAMETER), false));
		rocketLowHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.ROCKET_LOW_HEIGHT, true));
		lowestHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.BASE_ANGLE_OFFSET));
		intake.toggleWhenPressed(new IntakeCargo());
		outtake.whileHeld(new OuttakeCargo(Constants.IntakeArm.OUTTAKE_POWER));
		hatchIntake.whenPressed(new IntakeHatch());
		hatchOuttake.whenPressed(new OuttakeHatch());
		manualArm.whileHeld(new ManualArmMovement());
		zeroIntakeAngle.whenPressed(new ZeroIntakeArm());

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
