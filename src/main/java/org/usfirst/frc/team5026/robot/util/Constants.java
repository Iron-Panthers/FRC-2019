package org.usfirst.frc.team5026.robot.util;

/**
 * This class is meant to store constant (and often final) variables that should
 * be accessible throughout the project. Examples: ports, conversion rates,
 * wheel circumference, etc.
 */
public class Constants {

	public class Drivebase {
		/** DRIVEBASE PORTS */
		public static final int DRIVE_R1_PORT = 1; // SparkMax
		public static final int DRIVE_R2_PORT = 21; // See Above
		public static final int DRIVE_L1_PORT = 2; // See Above
		public static final int DRIVE_L2_PORT = 22; // See Above

		public static final int GEAR_SHIFT_PORT = 6;

		/** DRIVEBASE CONSTANTS */
		public static final double DRIVEBASE_WIDTH = 30; // inches
		public static final boolean IS_LEFT_INVERTED = true;
		public static final boolean IS_RIGHT_INVERTED = false;
		public static final boolean IS_DRIVEBASE_BACKWARDS = true; // Needed so the robot actually thinks the front is
																	// the front
		public static final double TURN_SENSITIVITY = 1;
		public static final double RADIAL_TURN_SENSITIVITY = 20;
		public static final double RAMP_RATE = 0.25; // Seconds to go from 0 to full throttle

		// Max Velocity in RPM
		// Low gear (High RPM, low robot speed) // Tested 3/20/2019 by James
		public static final double LOW_GEAR_LEFT_MAX_RPM = 5280.0;
		public static final double LOW_GEAR_RIGHT_MAX_RPM = 5070.0;
		// High gear (Low RPM, high robot speed) // Tested 3/20/2019 by James
		public static final double HIGH_GEAR_LEFT_MAX_RPM = 3630.0;
		public static final double HIGH_GEAR_RIGHT_MAX_RPM = 3502.0;

		// Motion Profiling PID (For Velocity)
		public static final double F = 0; // TODO Find max velocity
		public static final double P = 0; // TODO Tune
		public static final double I = 0;
		public static final double D = 0;

		public static final double SCALING_POWER = 2.75;
	}

	public class Input {
		/** DEVICE PORTS */
		public static final int JOYSTICK_1_PORT = 0; // Driver A
		public static final int JOYSTICK_2_PORT = 1; // Driver B
		public static final int JOYSTICK_3_PORT = 2; // Climb Joystick

		/** Buttons */
		// Driver A
		public static final int REVERSE_DRIVE_BUTTON = 1;
		public static final int SHIFT_GEAR_LOW_BUTTON = 2;
		// IntakeArm
		public static final int HUBERT_OUTTAKE_BUTTON = 7;
		public static final int HUBERT_FAST_OUTTAKE_BUTTON = 8;
		public static final int HUBERT_SLOW_OUTTAKE_BUTTON = 9;
		// Additional climb functionality
		public static final int TRAINING_WHEELS_BACKWARDS_SLOW = 12;
		public static final int LOWER_RIGHT_WINCH = 3; // This is on the left side of the joystick so that it is correct
														// when the robot is on the hab, facing the driver station
		public static final int LOWER_LEFT_WINCH = 4; // See above, but reversed
		public static final int RAISE_RIGHT_WINCH = 5;
		public static final int RAISE_LEFT_WINCH = 6;

		// Driver B
		// Manual Arm
		public static final int MANUAL_ARM_BUTTON = 1;
		// Intake
		public static final int INTAKE_BUTTON = 2;
		public static final int OUTTAKE_BUTTON = 3;
		public static final int INTAKE_HATCH_BUTTON = 6;
		public static final int OUTTAKE_HATCH_BUTTON = 5;
		// Arm Setpoints
		public static final int HATCH_HOLDING_HEIGHT_BUTTON = 4;
		public static final int ZERO_INTAKE_ARM_BUTTON = 7;
		public static final int LOWEST_HEIGHT_BUTTON = 8;
		public static final int ROCKET_LOW_HEIGHT_BUTTON = 10;
		public static final int OPP_CARGO_SHIP_HEIGHT_BUTTON = 11;
		public static final int CARGO_SHIP_HEIGHT_BUTTON = 12;

		// Climb Joystick
		// DO NOT USE TRIGGER. EVER. (on climb joystick)
		public static final int CANCEL_CLIMB_BUTTON = 3; // Used after robot on lvl 3 hab to stop elevator
		public static final int CLIMB_WITH_JOYSTICK = 4;
		public static final int CLIMB_DOWN_BUTTON = 5;
		public static final int CLIMB_UP_BUTTON = 6;
		public static final int TRAINING_WHEELS_SLOW_FORWARD_BUTTON = 7;
		public static final int TRAINING_WHEELS_FORWARD_BUTTON = 8;
		public static final int RETRACT_SUPER_STRUCTURE_PISTONS_BUTTON = 11; // MAYBE NOT
		public static final int EXTEND_SUPER_STRUCURE_PISTONS_BUTTON = 12;

		/** OTHER INPUT CONSTANTS */
		public static final double JOYSTICK_DEADBAND = 0.1;
		public static final double VERTICAL_BOWTIE_DEADZONE_SLOPE = 10;
		public static final double HORIZONTAL_BOWTIE_DEADZONE_SLOPE = 10;
		public static final double JOYSTICK_DEADZONE_CIRCLE = 0.14;
		public static final double MAX_DESIRED_TURN_RADIUS = 40;

	}

	public class IntakeArm {
		// INTAKE ARM PORTS
		public static final int INTAKE_ARM_MOTOR_PORT = 6;
		public static final int INTAKE_MOTOR_PORT = 4;

		public static final int HATCH_PISTON_SOLENOID_PORT = 2;
		public static final int HATCH_EXTENDER_PISTON_SOLENOID_PORT = 0;

		public static final boolean IS_INTAKE_INVERTED = true;

		// INTAKE JOYSTICK - TODO Tune To Driver Preference
		public static final double Y_DEADZONE = 0.1;
		public static final double SLOW_Y_DEADZONE = 0.7; // Deadzone for fine control
		public static final double SLOW_POWER_SCALE = 0.2; // Power scale for fine control
		public static final double POWER_SCALE = 0.7; // Should be greater than SLOW_POWER_SCALE

		// INTAKE ARM SETPOINTS - TODO Double Check Measurements
		public static final double TICKS_TO_DEGREES = 360.0 / (4096.0 * 4.0); // 360 / (ticks per rotation * sprocket
																				// ratio)
		public static final double BASE_ANGLE_OFFSET = -20; // degrees
		public static final double CARGO_DIAMETER = 13; // in
		public static final double ARM_LENGTH = 27.4; // in
		public static final double ARM_BASE_HEIGHT = 18.75; // in
		public static final double HATCH_HEIGHT = 16.0 - ARM_BASE_HEIGHT; // May not be used, can just use the bottom
																			// instead
		public static final double HATCH_HOLDING_HEIGHT = 18.75 - ARM_BASE_HEIGHT;// Value for lifting the intake to
																					// hold and also score the hatch.
																					// TODO: test value - may not be
																					// accurate
		public static final double CARGO_SHIP_HEIGHT = 39.0 - ARM_BASE_HEIGHT + CARGO_DIAMETER; // in
		public static final double ROCKET_LOW_HEIGHT = 34.0 - ARM_BASE_HEIGHT; // in
		public static final double CARGO_SHIP_FRONT_BACK_ADJUST = 9; // in
		public static final double DEGRESS_TO_RADIANS = Math.PI / 180;
		public static final double RADIANS_TO_DEGREES = 180 / Math.PI;

		// INTAKE ARM PID
		public static final double INTAKE_ARM_MAX_POWER = 0.35;
		public static final double INTAKE_ARM_P = 0.1; // New motor as of 9/10/19, decreased the P from 0.1 to 0.07,
														// 9/21/10 Increased to 0.1 during Bellarmine Practice.
		public static final double INTAKE_ARM_I = 0;
		public static final double INTAKE_ARM_D = 0;
		public static final double ERROR_TOLERANCE = 3; // degrees
		public static final long ERROR_TOLERANCE_TIME = 100; // ms
		public static final double STALL_TORQUE_COEFFICIENT = -0.09;

		// INTAKE
		public static final double INTAKE_POWER = 0.5; // TODO Find Best Power
		public static final double OUTTAKE_POWER = -0.7;
		public static final double FAST_OUTTAKE_POWER = -1.0; // For Level 2 Rocket
		public static final double SLOW_OUTTAKE_POWER = -0.5; // For close cargo ship
		public static final double RETRACT_HATCH_PISTON_DELAY = 0.1;
	}

	public class Climb {
		// Climb Motor Ports
		public static final int LEFT_MOTOR_1_PORT = 8;
		public static final int LEFT_MOTOR_2_PORT = 9;
		public static final int LEFT_MOTOR_3_PORT = 10;
		public static final int RIGHT_MOTOR_1_PORT = 11;
		public static final int RIGHT_MOTOR_2_PORT = 12;
		public static final int RIGHT_MOTOR_3_PORT = 13;
		public static final int WINCH_RIGHT_PORT = 5;
		public static final int WINCH_LEFT_PORT = 7;

		// Training Wheel Port
		public static final int TRAINING_WHEEL_MOTOR_PORT = 3;

		// Climb Side Inversions
		public static final boolean IS_LEFT_INVERTED = false;
		public static final boolean IS_RIGHT_INVERTED = true;

		// Winch Side Inversions
		public static final boolean IS_LEFT_WINCH_INVERTED = false;
		public static final boolean IS_RIGHT_WINCH_INVERTED = true;

		// Climb Solenoid Ports
		public static final int SUPER_STRUCTURE_SOLENOID_PORT = 4;

		// Climb Constants
		public static final double CLIMB_UP_SPEED = 0.25; // Cannot be higher without limit switches for safety, tested
															// 3/3/2019, we now use Joystick
		public static final double CLIMB_DOWN_SPEED = -0.25; // See above
		public static final double CLIMB_HOLD_POWER = 0.03025; // To hold the elevator at the same height when driving
																// around Tested 3/17/2019 by James, moves elevator up
																// slowly until it stops
		public static final double CLIMB_HOLD_POWER_INCREMENT = 0.000000000001; // Increment to find holding power
		public static final double CLIMB_ONE_HOLD_POWER = 0.02; // To hold the elevator at the same height when doing
																// single climb Tested 3/17/2019 by James, does not move
																// if climb1
		public static final double CLIMB_VELOCITY_TOLERANCE = 1; // Minimum velocity allowed for HoldElevator command
																	// TODO: Test Value
		public static final double SAFE_WINCH_OUTPUT = 1.0;

		// Tested setpoints
		public static final double TOP_ENCODER_VALUE = 129.2132568359375; // The rotaions measured at the top of the
																			// climb. used for
		// calibrating encoders. THIS IS IN ROTATIONS, NOT ENCODER
		// TICKS.
		public static final double BOTTOM_ENCODER_VALUE = 0.0; // The rotations at the bottom limit switch

		// Training Wheel Constants
		public static final double TRAINING_WHEEL_FORWARD_SPEED = 1.0; // Add OpenLoopRampRate
		public static final double TRAINING_WHEEL_SLOW_FORWARD_SPEED = 0.3;
		public static final double TRAINING_WHEEL_BACKWARD_SPEED = -0.3;
		public static final double TRAINING_WHEEL_SLOW_BACKWARD_SPEED = -0.1;
		public static final double TRAINING_WHEEL_RAMP_RATE = 0.5; // Seconds from 0 to full power.
		public static final int TRAINING_WHEEL_TIMEOUT_MS = 30; // If something goes wrong, it takes 30 ms before it can
																// move again
	}
}
