package org.usfirst.frc.team5026.robot.util;

/**
 * This class is meant to store constant (and often final) variables that should
 * be accessible throughout the project. Examples: ports, conversion rates,
 * wheel circumference, etc.
 */
public class Constants {

    public class Drivebase {
        /** DRIVEBASE PORTS */
        public static final int SWERVE_FRONT_RIGHT_PORT = 0;
        public static final int SWERVE_FRONT_LEFT_PORT = 1;
        public static final int SWERVE_BACK_RIGHT_PORT = 2;
        public static final int SWERVE_BACK_LEFT_PORT = 3;

        public static final int DRIVE_FRONT_RIGHT_PORT = 5;
        public static final int DRIVE_FRONT_LEFT_PORT = 6;
        public static final int DRIVE_BACK_RIGHT_PORT = 7;
        public static final int DRIVE_BACK_LEFT_PORT = 8;

        // Swerve Constants (TODO tune)
        public static final double SWERVE_P = 0;
        public static final double SWERVE_D = 0;
        public static final double CORNER_DIFF_TO_TURN_MODIFIER = 1/45;
        public static final double TURN_TO_F_RATIO = 1.0;

        /** DRIVEBASE CONSTANTS */
        public static final double DRIVEBASE_WIDTH = 30; // inches
        
    }

    public class Input {
        /** DEVICE PORTS */
        public static final int GAMEPAD_PORT = 0;
        /** Buttons */

        /** OTHER INPUT CONSTANTS */
        public static final boolean INVERT_GAMEPAD_SIDES = false;
       
    }

    public class Swerve {

        public static final double SWERVE_MOTOR_TICKS_PER_DEGREE = 5.0;

    }

}
