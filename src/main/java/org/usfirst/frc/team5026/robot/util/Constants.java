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
		public static final int DRIVE_R1_PORT = 2;
		public static final int DRIVE_R2_PORT = 2; // SPX
		public static final int DRIVE_L1_PORT = 1;
		public static final int DRIVE_L2_PORT = 1; // SPX

		// DRIVEBASE CONSTANTS
		public static final double DRIVEBASE_WIDTH = 30; // inches
		public static final boolean IS_LEFT_INVERTED = true;
		public static final boolean IS_RIGHT_INVERTED = false;
		public static final boolean IS_DRIVEBASE_BACKWARDS = true; // Needed so the robot actually thinks the front is
																	// the front
	}

	public class LineFollow {
		// LINEFOLLOW Ports
		public static final int FRONT_LEFT_SENSOR_PORT = 1;
		public static final int FRONT_RIGHT_SENSOR_PORT = 0;
		// public static final int BACK_LEFT_SENSOR_PORT = 2;
		// public static final int BACK_RIGHT_SENSOR_PORT = 3;
		public static final int CENTER_SENSOR_PORT = 2;

		// LINEFOLLOW CONSTANTS
		public static final double ODS_TAPE_SEEN = .1; // TODO the light value after which the line has "been seen"
		// by the center of the robot the value needs to be calibrated based on tape,
		// flooring, and ambient light
		public static final double WALL_OUTPUT_CURRENT = 19; // TODO the current value over which the robot will
		// stop driving because it has run into a wall
		public static final double DRIVE_TO_LINE_SPD = .3; // the speed that the robot drives at when the
		//linefollow cmd is called but before it gets to the line
		public static final double LINEFOLLOW_BASE_POWER = .15; //the base power that the robot drives forward
		//during LineFollow, regardless of the line's position
		public static final double LINEFOLLOW_REACTION_POWER = 7.5; // the weight that the robot gives to
		//driving right or left based on the  outer sensor's values
        public static final double ACCEPTABLE_STRAIGHTNESS = .25; //not currently in use
        
        public static final double LINEFOLLOW_INNER_POWER = 5; // the weight that the robot gives to
		//driving straight based on the center sensor's value
	}

	// TODO tune input constants
	public class Input {
		/** DEVICE PORTS */
		public static final int JOYSTICK_1_PORT = 0;

		/** OTHER INPUT CONSTANTS */
		public static final double JOYSTICK_DEADBAND = 0.1;
		public static final double JOYSTICK_DEADZONE_CIRCLE = 0.14;
		public static final double MAX_DESIRED_TURN_RADIUS = 40;
	}

	public class DriveStraight{
		public static final double ENCODER_ERROR_TOLERANCE = 1;
		public static final int GYRO_PORT = 0;
		public static final double GYRO_ERROR_TOLERANCE = 1;
		public static final double ROTATE_POWER = 0.5;
		public static final double ROTATE_P = 1;
		public static final double ROTATE_I = 0;
		public static final double ROTATE_D = 0;
        public static final double WHEEL_DIAMETER = 5.9;
        public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        public static final double REV_TO_TICKS = 5500;
		public static final double TICKS_TO_INCHES = REV_TO_TICKS/WHEEL_CIRCUMFERENCE;
		public static final double MAX_ANGULAR_VELOCITY = 0.20668863475263174 * 2;//Degrees per millisecond
	}
}