package org.usfirst.frc.team5026.robot.util;

/**
 * This class is meant to store constant (and often final) variables that should
 * be accessible throughout the project. Examples: ports, conversion rates,
 * wheel circumference, etc.
 */
public class Constants {

    public class Drivebase {
        // DRIVEBASE PORTS

        // DRIVEBASE CONSTANTS
    }
    public class IntakeArm { //TODO Calibrate/Set Ports
        public static final int INTAKE_ARM_MOTOR_PORT = 0;

        public static final double TICKS_TO_DEGREES = 360 / 1; //Change the 1 to ticks per rotation
        public static final double ARM_LENGTH = 1; //Not used
        public static final double CARGO_SHIP_HEIGHT = 1;
        public static final double ROCKET_LOW_HEIGHT = 1;

        public static final double INTAKE_ARM_P = 1;
        public static final double INTAKE_ARM_I = 0; 
        public static final double INTAKE_ARM_D = 0;
        public static final double INTAKE_ARM_F = 1;

        public static final double ERROR_TOLERANCE = 1;
        public static final long ERROR_TOLERANCE_TIME = 500;
    }

    public class VP {
        //the real height of the outer contour of the tape in inches = 5.5 * Math.cos(14.5)
        public static final double TAPE_SIDE_HEIGHT = 5.324812;
        //the real width between the upper points of the tape contours in inches = 4 * Math.cos(14.5) + 8
        public static final double TAPE_WIDTH_BETWEEN_UPPER_POINTS = 11.872590;
        //the ratio of the default height of the contour in pixels to the elevation angle at 33.5 in. away = 525 / Math.atan(TAPE_SIDE_HEIGHT/(2*33.5))
        public static final double VERTICAL_ANGLE_TO_DEFAULT_HEIGHT = 115.536463;
        //the ratio of the width between the upper points of the tape contours in pixels to the elevation angle at 33.5 in. away = 1176 / Math.atan(TAPE_WIDTH_BETWEEN_UPPER_POINTS/(2*33.5))
        public static final double HORIZONTAL_ANGLE_TO_DEFAULT_WIDTH = 117.030497;
    }
}