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
        public static final int DRIVE_R1_PORT = 1;	// SparkMax
        public static final int DRIVE_R2_PORT = 21; // See Above
        public static final int DRIVE_L1_PORT = 2;	// See Above
        public static final int DRIVE_L2_PORT = 22; // See Above

        public static final int GEAR_SHIFT_PORT_1 = 6;
        public static final int GEAR_SHIFT_PORT_2 = 7;

        /** DRIVEBASE CONSTANTS */
        public static final double DRIVEBASE_WIDTH = 30; // inches
        public static final boolean IS_LEFT_INVERTED = true;
        public static final boolean IS_RIGHT_INVERTED = false;
        public static final boolean IS_DRIVEBASE_BACKWARDS = true; // Needed so the robot actually thinks the front is
                                                                   // the front
        public static final double TURN_SENSITIVITY = 1;
		public static final double RADIAL_TURN_SENSITIVITY = 20;
		public static final double RAMP_RATE = 0.25; // Seconds to go from 0 to full throttle

        // Motion Profiling PID (For Velocity)
        public static final double F = 0; // TODO Find max velocity
        public static final double P = 0; // TODO Tune
        public static final double I = 0;
        public static final double D = 0;

        public static final double SCALING_POWER = 2.75;
    }

    // TODO tune input constants
    public class Input {
        /** DEVICE PORTS */
        public static final int JOYSTICK_1_PORT = 0;
        public static final int JOYSTICK_2_PORT = 1;

        /** Buttons */
        // Driver A
        public static final int REVERSE_DRIVE_BUTTON = 1;
        public static final int SHIFT_GEAR_LOW_BUTTON = 2;

        // Climb
        public static final int CLIMB_DOWN_BUTTON = 5;
        public static final int CLIMB_UP_BUTTON = 6;
        public static final int TRAINING_WHEELS_BACKWARD_BUTTON = 7; // MAYBE NOT COMP
        public static final int TRAINING_WHEELS_FORWARD_BUTTON = 8;
        public static final int RETRACT_TRAINING_WHEELS_BUTTON = 9;
        public static final int DEPLOY_TRAINING_WHEEL_BUTTON = 10;
        public static final int RETRACT_SUPER_STRUCTURE_PISTONS_BUTTON = 11; // MAYBE NOT
        public static final int EXTEND_SUPER_STRUCURE_PISTONS_BUTTON = 12;
       

        // Driver B
        // Manual Arm
        public static final int MANUAL_ARM_BUTTON = 1;
        // Intake
        public static final int INTAKE_BUTTON = 2;
        public static final int OUTTAKE_BUTTON = 3;
        // Arm Setpoints
        public static final int ZERO_INTAKE_ARM_BUTTON = 7;
        public static final int LOWEST_HEIGHT_BUTTON = 8;
        public static final int OPP_ROCKET_LOW_HEIGHT_BUTTON = 9; // DO NOT USE
        public static final int ROCKET_LOW_HEIGHT_BUTTON = 10;
        public static final int OPP_CARGO_SHIP_HEIGHT_BUTTOM = 11;
        public static final int CARGO_SHIP_HEIGHT_BUTTON = 12;
        public static final int CLIMB_SETUP_BUTTON = 4;
        public static final int CLIMB_FINISH_BUTTON = 5;
        public static final int CANCEL_CLIMB_BUTTON = 6;

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

    public class IntakeArm {
        // INTAKE ARM PORTS
        public static final int INTAKE_ARM_MOTOR_PORT = 6;
        public static final int INTAKE_MOTOR_PORT = 4;

        public static final boolean IS_INTAKE_INVERTED = true;

        // INTAKE JOYSTICK - TODO Tune To Driver Preference
        public static final double Y_DEADZONE = 0.1;
        public static final double POWER_SCALE = 0.7;

        // INTAKE ARM SETPOINTS - TODO Double Check Measurements
        public static final double TICKS_TO_DEGREES = 360.0 / (1024.0 * 16.0); // 360 / (ticks per rotation * sprocket
                                                                               // ratio)
        public static final double CARGO_DIAMETER = 13; // in
        public static final double ARM_LENGTH = 27.4; // in
        public static final double ARM_BASE_HEIGHT = 18.75; // in
        public static final double CARGO_SHIP_HEIGHT = 39.0 - ARM_BASE_HEIGHT + CARGO_DIAMETER; // in
        public static final double ROCKET_LOW_HEIGHT = 35.0 - ARM_BASE_HEIGHT; // in
        public static final double LOWEST_HEIGHT = 12.0 - ARM_BASE_HEIGHT; // in - estimate
        public static final double CARGO_SHIP_FRONT_BACK_ADJUST = 7.5; // in
        public static final double DEGRESS_TO_RADIANS = Math.PI / 180;

        // INTAKE ARM PID - TODO Tune PID
        public static final double INTAKE_ARM_P = 0.25;
        public static final double INTAKE_ARM_I = 0;
        public static final double INTAKE_ARM_D = 0;
        public static final double ERROR_TOLERANCE = 3; // degrees
        public static final long ERROR_TOLERANCE_TIME = 100; // ms
        public static final double STALL_TORQUE_COEFFICIENT = -0.09;

        // INTAKE
        public static final double INTAKE_POWER = 0.5; // TODO Find Best Power
        public static final double OUTTAKE_POWER = -0.7;
    }

    public class Climb {
        // Climb Motor Ports
        public static final int LEFT_MOTOR_1_PORT = 8;
        public static final int LEFT_MOTOR_2_PORT = 9;
        public static final int LEFT_MOTOR_3_PORT = 10;
        public static final int RIGHT_MOTOR_1_PORT = 11;
        public static final int RIGHT_MOTOR_2_PORT = 12;
        public static final int RIGHT_MOTOR_3_PORT = 13;

        // Climb Side Inversions
        public static final boolean IS_LEFT_INVERTED = false;
        public static final boolean IS_RIGHT_INVERTED = true;

        public static final int TRAINING_WHEEL_MOTOR_PORT = 3; // To be changed

        // Climb Solenoid Ports
        public static final int SUPER_STRUCTURE_SOLENOID_PORT_1 = 0;
        public static final int SUPER_STRUCTURE_SOLENOID_PORT_2 = 1;

        public static final int TRAINING_WHEEL_PISTON_SOLENOID_PORT_1 = 2;
        public static final int TRAINING_WHEEL_PISTON_SOLENOID_PORT_2 = 3;

        // Climb Constants
        public static final double CLIMB_UP_SPEED = 0.25; // Cannot be higher without limit switches for safety
        public static final double CLIMB_DOWN_SPEED = -0.25; // See above
        public static final double TRAINING_WHEEL_FORWARD_SPEED = 0.3;
        public static final double TRAINING_WHEEL_BACKWARD_SPEED = -0.3;
        public static final double CLIMB_FIRST_TARGET = 3.0; // The target of rotations needed before we can deploy the
                                                             // training wheels. THIS IS IN ROTATIONS, NOT ENCODER
                                                             // TICKS. TODO: Test value
        public static final double CLIMB_SECOND_TARGET = 6.0; // The target of rotations needed where the ramps fall to
                                                              // the ground. THIS IS IN ROTATIONS, NOT ENCODER TICKS.
                                                              // TODO: Test Value
        public static final double CLIMB_THIRD_TARGET = 12.0; // The target of roataions needed to get to the soft
                                                              // limit. THIS IS IN ROTATIONS, NOT ENCODER
                                                              // TICKS. TODO: Test Value
        public static final double CLIMB_FINAL_TARGET = 4.0; // The target of rotations needed to get the super
                                                             // structure off the ground when the robot is on the
                                                             // third level platform. THIS IS IN ROTATIONS, NOT
                                                             // ENCODER TICKS. TODO: Test Value
        public static final double TRAINING_WHEEL_DRIVE_TIME = 3.0; // The number of seconds that the robot training
                                                                    // wheels drive forward to move onto the platform.
                                                                    // TODO: Test Value
        public static final double END_TOP_CLIMB_POWER_SCALAR = 3.03; // The scalar to be divided by the velocity at the end
                                                                  // at the end of the climb, used to determine climbing
                                                                  // power
        public static final double END_BOTTOM_CLIMB_POWER_SCALAR = 3.03; // The scalar to be divided by the velocity at the end
                                                                  // at the end of the climb, used to determine climbing
                                                                  // power                                                          
        public static final double TOP_ENCODER_VALUE = 13.0; // The rotaions measured at the top of the climb. used for
                                                             // calibrating encoders. THIS IS IN ROTATIONS, NOT ENCODER
                                                             // TICKS. TODO: Find Value
        public static final double BOTTOM_ENCODER_VALUE = 0.0; // The rotations at the bottom limit switch

    }
}
