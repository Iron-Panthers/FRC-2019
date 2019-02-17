package org.usfirst.frc.team5026.robot.util;

/**
 * This class is meant to store constant (and often final) variables that should
 * be accessible throughout the project. Examples: ports, conversion rates,
 * wheel circumference, etc.
 */
public class Constants {

	// TODO adjust drivebase constants
	public class Drivebase {
		/** DRIVEBASE PORTS */
		public static final int DRIVE_R1_PORT = 9;
		public static final int DRIVE_R2_PORT = 1; // SPX
		public static final int DRIVE_L1_PORT = 1;
		public static final int DRIVE_L2_PORT = 2; // SPX

		/** DRIVEBASE CONSTANTS */
		public static final double DRIVEBASE_WIDTH = 30; // inches
		public static final boolean IS_LEFT_INVERTED = true;
		public static final boolean IS_RIGHT_INVERTED = false;
		public static final boolean IS_DRIVEBASE_BACKWARDS = true; // Needed so the robot actually thinks the front is
																	// the front
		public static final double TURN_SENSITIVITY = 1;
		public static final double RADIAL_TURN_SENSITIVITY = 20;

		// Motion Profiling PID (For Velocity)
		public static final double F = 0; // TODO Find max velocity
		public static final double P = 0; // TODO Tune
		public static final double I = 0;
		public static final double D = 0;
	}

	// TODO tune input constants
	public class Input {
		/** DEVICE PORTS */
		public static final int JOYSTICK_1_PORT = 0;
		public static final int JOYSTICK_2_PORT = 1;

		/** OTHER INPUT CONSTANTS */
		public static final double JOYSTICK_DEADBAND = 0.1;
		public static final double VERTICAL_BOWTIE_DEADZONE_SLOPE = 5;
		public static final double HORIZONTAL_BOWTIE_DEADZONE_SLOPE = 5;
		public static final double JOYSTICK_DEADZONE_CIRCLE = 0.14;
		public static final double MAX_DESIRED_TURN_RADIUS = 40;
	}

	public class Camera {
		// Camera Ports
		public static final int CAMERA_PORT_1 = 0;
		public static final int CAMERA_PORT_2 = 0;

		// Camera Constants
		public static final int CAMERA_HEIGHT = 144;
		public static final int CAMERA_WIDTH = 256;
		public static final int FRAME_RATE = 15;
	}

	public class IntakeArm {
		// INTAKE ARM PORTS
		public static final int INTAKE_ARM_MOTOR_PORT = 6;
		public static final int INTAKE_MOTOR_PORT = 7;

		// INTAKE JOYSTICK - TODO Tune To Driver Preference
		public static final double Y_DEADZONE = 0.1;
		public static final double POWER_SCALE = 0.7;

		// INTAKE ARM SETPOINTS - TODO Double Check Measurements
		public static final double TICKS_TO_DEGREES = 360.0 / (1024.0 * 16.0); // 360 / (ticks per rotation * sprocket ratio)
		public static final double CARGO_DIAMETER = 13; // in
		public static final double ARM_LENGTH = 27.4; // in
		public static final double ARM_BASE_HEIGHT = 18.75; // in
		public static final double CARGO_SHIP_HEIGHT = 31.5 - ARM_BASE_HEIGHT + CARGO_DIAMETER; // in
		public static final double ROCKET_LOW_HEIGHT = 35.5 - ARM_BASE_HEIGHT; // in
		public static final double LOWEST_HEIGHT = 12 - ARM_BASE_HEIGHT; // in - estimate
		public static final double DEGRESS_TO_RADIANS = Math.PI / 180;

		// INTAKE ARM PID - TODO Tune PID
		public static final double INTAKE_ARM_P = 0.1;
		public static final double INTAKE_ARM_I = 0.003;
		public static final double INTAKE_ARM_D = 0;
		public static final double ERROR_TOLERANCE = 1; // degrees
		public static final long ERROR_TOLERANCE_TIME = 100; // ms
		public static final double STALL_TORQUE_COEFFICIENT = -0.09;

		// INTAKE ARM TORQUE
		public static final double INTAKE_MASS = 8; // lbs - estimate
		public static final double INTAKE_DISTANCE = 10.7 + 8.25; // Distance to COM of Intake (in)
		public static final double INTAKE_ARM_MASS = 5; // lbs - estimate
		public static final double INTAKE_ARM_DISTANCE = 10.7 / 2; // Distance to COM of Intake Arm (in)
		public static final double GRAVITY_ACCELERATION = 386.1; // in/s^2
		public static final double INTAKE_ARM_MOTOR_MAX_TORQUE = 6.284 * 40 * 4; // in/lbs * gear ratio * sprocket ratio

		// INTAKE
		public static final double INTAKE_POWER = 0.5; // TODO Find Best Power
		public static final double OUTTAKE_POWER = -1;
	}
}