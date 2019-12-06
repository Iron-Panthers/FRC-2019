package org.usfirst.frc.team5026.robot.util;

/**
 * This class is meant to store constant (and often final) variables that should
 * be accessible throughout the project. Examples: ports, conversion rates,
 * wheel circumference, etc.
 */
public class Constants {

    public class SwerveDrive {

        /** PORTS */
        public static final int SWERVE_FRONT_RIGHT_PORT = 1;
        public static final int SWERVE_FRONT_LEFT_PORT = 5;
        public static final int SWERVE_BACK_RIGHT_PORT = 9;
        public static final int SWERVE_BACK_LEFT_PORT = 3;

        public static final int DRIVE_FRONT_RIGHT_PORT = 2;
        public static final int DRIVE_FRONT_LEFT_PORT = 6;
        public static final int DRIVE_BACK_RIGHT_PORT = 7;
        public static final int DRIVE_BACK_LEFT_PORT = 8;

        /** SwerveMC constants */
        public static final double SWERVE_MOTOR_TICKS_PER_DEGREE = 2250 / 360;
        public static final double SWERVE_P = 0.001;
        public static final double SWERVE_D = 0;
        
        /** SwerveMotorGroup constants */

        /** SwerveDrive swerve constants */
        public static final double TURN_TO_F_RATIO = 1.0;

        /** SwerveMath constants */
        public static final double CORNER_DIFF_TO_TURN_MODIFIER = 1; //kind of like turn sensitivity

        /** Auto constants */
        public static final double TICKS_PER_INCH = 40; //TODO measure
        public static final double TICKS_TO_ACCELERATE = 300;//TODO measure/tune (honestly tuning might be easier)
        public static final double DEGREES_TO_ANGULARLY_ACCELERATE = 30; //TODO measure/tune (honestly tuning might be easier)
        public static final double AUTO_FORWARD_MAINTENANCE_P = .0001; //TODO tune
        public static final double AUTO_ANGLE_MAINTENANCE_P = .001; //TODO tune
        public static final double MINIMUM_AUTO_MOVEMENT = .135; // TODO tune
        public static final double AUTO_FORWARD_DEADZONE = 10; // TODO tune
        public static final double AUTO_ANGLE_DEADZONE = 10; //TODO tune


    }

    public class DrivebaseProperties {
        
        public static final double WIDTH = 30; //from left wheel to right wheel
        public static final double LENGTH = 30; //from front wheel to back wheel
        public static final double ASPECT_RATIO = LENGTH / WIDTH;
        //public static final double DRIVEBASE_REFERENCE_ANGLE = Math.atan(ASPECT_RATIO);
        public static final double DRIVEBASE_DIAGONAL_LENGTH = 50; //TODO measure
        public static final double DRIVEBASE_TURNING_CIRCUMFERENCE = DRIVEBASE_DIAGONAL_LENGTH * 2 * Math.PI;
        public static final double START_HEADING = 0; //from -180 to 180, field relative. Specify what angle the robot starts the match at.


    }

    public class Input {
        /** DEVICE PORTS */
        public static final int GAMEPAD_PORT = 0;

        /** Buttons */

        /** OTHER INPUT CONSTANTS */
        public static final boolean INVERT_GAMEPAD_SIDES = false;
       
    }

}
