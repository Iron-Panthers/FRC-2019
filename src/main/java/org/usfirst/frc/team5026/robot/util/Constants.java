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
		public static final int DRIVE_R1_PORT = 1;
		public static final int DRIVE_R2_PORT = 1; // SPX
		public static final int DRIVE_L1_PORT = 2;
		public static final int DRIVE_L2_PORT = 2; // SPX

		/** DRIVEBASE CONSTANTS */
		public static final double DRIVEBASE_WIDTH = 30; // inches
		public static final boolean IS_LEFT_INVERTED = true;
		public static final boolean IS_RIGHT_INVERTED = false;
		public static final boolean IS_DRIVEBASE_BACKWARDS = true; // Needed so the robot actually thinks the front is the front
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

		/** OTHER INPUT CONSTANTS */
		public static final double JOYSTICK_DEADBAND = 0.1;
		public static final double VERTICAL_BOWTIE_DEADZONE_SLOPE = 10;
		public static final double HORIZONTAL_BOWTIE_DEADZONE_SLOPE = 10;
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
}