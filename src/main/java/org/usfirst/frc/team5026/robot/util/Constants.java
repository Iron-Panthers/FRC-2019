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
        public static final int DRIVE_R2_PORT = 2; //SPX
        public static final int DRIVE_L1_PORT = 9;
        public static final int DRIVE_L2_PORT = 3; //SPX

        /** DRIVEBASE CONSTANTS */
		public static final double DRIVEBASE_WIDTH = 30; // inches
		public static final boolean IS_LEFT_INVERTED = false;
		public static final boolean IS_RIGHT_INVERTED = true;
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
}