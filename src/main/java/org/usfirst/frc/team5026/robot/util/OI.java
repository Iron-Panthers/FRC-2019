/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

import org.usfirst.frc.team5026.robot.subsystems.climb.commands.ClimbDown;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.ClimbUp;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.DeployTrainingWheels;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.ExtendSuperStructurePistons;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.RetractSuperStructurePistons;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.RetractTrainingWheels;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.TrainingWheelsBackward;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.TrainingWheelsDriveForward;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.DriveShift;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.ReverseDrive;
import org.usfirst.frc.team5026.robot.subsystems.intake.commands.ArmToTarget;
import org.usfirst.frc.team5026.robot.subsystems.intake.commands.IntakeCargo;
import org.usfirst.frc.team5026.robot.subsystems.intake.commands.ManualArmMovement;
import org.usfirst.frc.team5026.robot.subsystems.intake.commands.OuttakeCargo;
import org.usfirst.frc.team5026.robot.subsystems.intake.commands.ZeroIntakeArm;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public JoystickWrapper stick1;
    public JoystickWrapper stick2;
    public JoystickButton reverseDrive;
    public JoystickButton shiftGearLow;
    // public JoystickButton turnLeft;
    // public JoystickButton turnRight;
    // public JoystickButton findF;
    public JoystickButton extendSuperStructurePistons;
    public JoystickButton climbUp;
    public JoystickButton deployTrainingWheels;
    public JoystickButton climbDown;
    public JoystickButton retractSuperStructurePistons;
    public JoystickButton retractTrainingWheels;
    public JoystickButton trainingWheelsForward;
    public JoystickButton trainingWheelsBackward;

    JoystickButton cargoShipHeight;
    JoystickButton oppCargoShipHeight;
    JoystickButton rocketLowHeight;
    // JoystickButton oppRocketLowHeight;
    JoystickButton lowestHeight;
    JoystickButton intake;
    JoystickButton outtake;
    JoystickButton manualArm;
    JoystickButton zeroIntakeAngle;

    public OI() {
        // DRIVER 1
        stick1 = new JoystickWrapper(Constants.Input.JOYSTICK_1_PORT);
        stick2 = new JoystickWrapper(Constants.Input.JOYSTICK_2_PORT);
        reverseDrive = new JoystickButton(stick1, Constants.Input.REVERSE_DRIVE_BUTTON);
        shiftGearLow = new JoystickButton(stick1, Constants.Input.SHIFT_GEAR_LOW_BUTTON);
        // findF = new JoystickButton(stick1, Constants.Input.FIND_F_BUTTON);
        // turnLeft = new JoystickButton(stick1, Constants.Input.TURN_LEFT_BUTTON);
        // turnRight = new JoystickButton(stick1, Constants.Input.TURN_RIGHT_BUTTON);
        extendSuperStructurePistons = new JoystickButton(stick1, Constants.Input.EXTEND_SUPER_STRUCURE_PISTONS_BUTTON);
        climbUp = new JoystickButton(stick1, Constants.Input.CLIMB_UP_BUTTON);
        deployTrainingWheels = new JoystickButton(stick1, Constants.Input.DEPLOY_TRAINING_WHEEL_BUTTON);
        climbDown = new JoystickButton(stick1, Constants.Input.CLIMB_DOWN_BUTTON);
        retractSuperStructurePistons = new JoystickButton(stick1,
                Constants.Input.RETRACT_SUPER_STRUCTURE_PISTONS_BUTTON);
        retractTrainingWheels = new JoystickButton(stick1, Constants.Input.RETRACT_TRAINING_WHEELS_BUTTON);
        trainingWheelsForward = new JoystickButton(stick1, Constants.Input.TRAINING_WHEELS_FORWARD_BUTTON);
        trainingWheelsBackward = new JoystickButton(stick1, Constants.Input.TRAINING_WHEELS_BACKWARD_BUTTON);

        trainingWheelsForward.whileHeld(new TrainingWheelsDriveForward());
        trainingWheelsBackward.whileHeld(new TrainingWheelsBackward());
        // findF.whileHeld(new FindF());
        reverseDrive.whileHeld(new ReverseDrive());
        shiftGearLow.whileHeld(new DriveShift());
        extendSuperStructurePistons.whenPressed(new ExtendSuperStructurePistons());
        climbUp.whileHeld(new ClimbUp());
        deployTrainingWheels.whenPressed(new DeployTrainingWheels());
        climbDown.whileHeld(new ClimbDown());
        retractSuperStructurePistons.whenPressed(new RetractSuperStructurePistons());
        retractTrainingWheels.whenPressed(new RetractTrainingWheels());

        // DRIVER 2
        manualArm = new JoystickButton(stick2, Constants.Input.MANUAL_ARM_BUTTON);
        intake = new JoystickButton(stick2, Constants.Input.INTAKE_BUTTON);
        outtake = new JoystickButton(stick2, Constants.Input.OUTTAKE_BUTTON);
        zeroIntakeAngle = new JoystickButton(stick2, Constants.Input.ZERO_INTAKE_ARM_BUTTON);
        lowestHeight = new JoystickButton(stick2, Constants.Input.LOWEST_HEIGHT_BUTTON);
        // oppRocketLowHeight = new JoystickButton(stick2, Constants.Input.OPP_ROCKET_LOW_HEIGHT_BUTTON);
        rocketLowHeight = new JoystickButton(stick2, Constants.Input.ROCKET_LOW_HEIGHT_BUTTON);
        oppCargoShipHeight = new JoystickButton(stick2, Constants.Input.OPP_CARGO_SHIP_HEIGHT_BUTTOM);
        cargoShipHeight = new JoystickButton(stick2, Constants.Input.CARGO_SHIP_HEIGHT_BUTTON);

        cargoShipHeight.whenPressed(new ArmToTarget(
                Constants.IntakeArm.CARGO_SHIP_HEIGHT - Constants.IntakeArm.CARGO_SHIP_FRONT_BACK_ADJUST, true));
        oppCargoShipHeight.whenPressed(
                new ArmToTarget((Constants.IntakeArm.CARGO_SHIP_HEIGHT - Constants.IntakeArm.CARGO_DIAMETER), false));
        rocketLowHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.ROCKET_LOW_HEIGHT, true));
        // oppRocketLowHeight.whenPressed(
                // new ArmToTarget((Constants.IntakeArm.ROCKET_LOW_HEIGHT - Constants.IntakeArm.CARGO_DIAMETER), false));
        lowestHeight.whenPressed(new ArmToTarget(Constants.IntakeArm.LOWEST_HEIGHT, true));
        intake.toggleWhenPressed(new IntakeCargo());
        outtake.toggleWhenPressed(new OuttakeCargo());
        manualArm.whileHeld(new ManualArmMovement());
        zeroIntakeAngle.whenPressed(new ZeroIntakeArm());
    }
}
