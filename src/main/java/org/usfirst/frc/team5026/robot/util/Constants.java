package org.usfirst.frc.team5026.robot.util;

/**
 * This class is meant to store constant (and often final) variables that should
 * be accessible throughout the project. Examples: ports, conversion rates,
 * wheel circumference, etc.
 */
public class Constants {

    public class Drive {
        /** PORTS */
        public static final int DRIVE_FRONT_RIGHT_PORT = 5;
        public static final int DRIVE_FRONT_LEFT_PORT = 6;
        public static final int DRIVE_BACK_RIGHT_PORT = 7;
        public static final int DRIVE_BACK_LEFT_PORT = 8;

        public static final double TICKS_PER_INCH = 40; //TODO measure
        public static final double TICKS_TO_ACCELERATE = 300;//TODO measure
        public static final double DEGREES_TO_ANGULARLY_ACCELERATE = 30;//TODO measure

    }

    public class Swerve {

        /** PORTS */
        public static final int SWERVE_FRONT_RIGHT_PORT = 0;
        public static final int SWERVE_FRONT_LEFT_PORT = 1;
        public static final int SWERVE_BACK_RIGHT_PORT = 2;
        public static final int SWERVE_BACK_LEFT_PORT = 3;

        /** SwerveMC constants */
        public static final double SWERVE_MOTOR_TICKS_PER_DEGREE = 5.0;
        public static final double SWERVE_P = 0;
        public static final double SWERVE_D = 0;
        
        /** SwerveMotorGroup constants */

        /** SwerveDrive swerve constants */
        public static final double TURN_TO_F_RATIO = 1.0;

        /** SwerveMath constants */
        public static final double CORNER_DIFF_TO_TURN_MODIFIER = 1/45;

    }

    public class DrivebaseProperties {
        
        public static final double DRIVEBASE_WIDTH = 30; //I don't think this is currently needed but i'll leave it in just in case
        public static final double DRIVEBASE_DIAGONAL_LENGTH = 50; //TODO measure
        public static final double DRIVEBASE_TURNING_CIRCUMFERENCE = DRIVEBASE_DIAGONAL_LENGTH * 2 * Math.PI;
        
    }

    public class Input {
        /** DEVICE PORTS */
        public static final int GAMEPAD_PORT = 0;
        /** Buttons */

        /** OTHER INPUT CONSTANTS */
        public static final boolean INVERT_GAMEPAD_SIDES = false;
       
    }

}
