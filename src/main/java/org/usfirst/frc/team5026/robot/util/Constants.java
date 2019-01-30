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
        public static final int INTAKE_MOTOR_PORT = 0;


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

        public static final double INTAKE_POWER = 1;
        public static final double INTAKE_OUTPUT_CURRENT_LIMIT = 1;
        public static final double INTAKE_BRAKE_POWER = 1;
    }
}