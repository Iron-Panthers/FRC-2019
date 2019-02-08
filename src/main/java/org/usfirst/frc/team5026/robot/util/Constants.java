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
    public class IntakeArm { //TODO Calibrate/Set Ports and Numbers
        //INTAKE ARM PORTS
        public static final int INTAKE_ARM_MOTOR_PORT = 9;
        public static final int INTAKE_MOTOR_PORT = 6;
 
        public static final double TICKS_TO_DEGREES = 360 / (1024 * 4); //360 / ticks per rotation * sprocket ratio
        public static final double CARGO_DIAMETER = 13; //in
        public static final double ARM_LENGTH = 27.4; //in
        public static final double ARM_BASE_HEIGHT = 18.75; //in
        public static final double CARGO_SHIP_HEIGHT = 31.5 - ARM_BASE_HEIGHT + CARGO_DIAMETER; //in
        public static final double ROCKET_LOW_HEIGHT = 35.5 - ARM_BASE_HEIGHT; //in
        public static final double LOWEST_HEIGHT = 12 - ARM_BASE_HEIGHT; //in

        //INTAKE ARM PID
        public static final double INTAKE_ARM_P = 1;
        public static final double INTAKE_ARM_I = 0; 
        public static final double INTAKE_ARM_D = 0;
        public static final double ERROR_TOLERANCE = 1; //degrees
        public static final long ERROR_TOLERANCE_TIME = 500; //ms

        //INTAKE ARM TORQUE
        public static final double INTAKE_MASS = 8; //lbs - estimate
        public static final double INTAKE_DISTANCE = 10.7 + 8.25; //Distance to COM of Intake (in)
        public static final double INTAKE_ARM_MASS = 5; //lbs - estimate
        public static final double INTAKE_ARM_DISTANCE = 10.7 / 2; //Distance to COM of Intake Arm (in)
        public static final double GRAVITY_ACCELERATION = 386.1; //in/s^2
        public static final double INTAKE_ARM_MOTOR_MAX_TORQUE = 6.284 * 40 * 4; //in/lbs * gear ratio * sprocket ratio

        //INTAKE
        public static final double INTAKE_POWER = 0.5;
        public static final double OUTTAKE_POWER = -1;
        public static final double INTAKE_OUTPUT_CURRENT_LIMIT = 0; //Replace later
    }
}