package org.usfirst.frc.team5026.robot.util;

/**
 * This class is meant to store constant (and often final) variables that should
 * be accessible throughout the project. Examples: ports, conversion rates,
 * wheel circumference, etc.
 */
public class Constants {

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

        // Max Velocity in RPM
        // Low gear (High RPM, low robot speed) // Tested 3/20/2019 by James
        public static final double LOW_GEAR_LEFT_MAX_RPM = 5280.0;
        public static final double LOW_GEAR_RIGHT_MAX_RPM = 5070.0;
        // High gear (Low RPM, high robot speed) // Tested 3/20/2019 by James
        public static final double HIGH_GEAR_LEFT_MAX_RPM = 3630.0;
        public static final double HIGH_GEAR_RIGHT_MAX_RPM = 3502.0;

        // Motion Profiling PID (For Velocity)
        public static final double F = 0; // TODO Find max velocity
        public static final double P = 0; // TODO Tune
        public static final double I = 0;
        public static final double D = 0;

        public static final double SCALING_POWER = 2.75;
    }

    public class Input {
        /** DEVICE PORTS */
        public static final int JOYSTICK_1_PORT = 0; // Driver A
        public static final int JOYSTICK_2_PORT = 1; // Driver B
        public static final int JOYSTICK_3_PORT = 2; // Climb Joystick

        /** Buttons */
        // Driver A
        public static final int REVERSE_DRIVE_BUTTON = 1;
        public static final int SHIFT_GEAR_LOW_BUTTON = 2;
        // Additional climb functionality
		public static final int ALT_CLIMB_DOWN_BUTTON = 5;
		// Turret buttons
		public static final int FIRE_CANNON_BUTTON = 3;

		// Driver B
		public static final int MOVE_TURRET_BUTTON = 1;

		// Climb Joystick
		// DO NOT USE TRIGGER. EVER. (on climb joystick)
        public static final int CANCEL_CLIMB_BUTTON = 3; // Used after robot on lvl 3 hab to stop elevator
        public static final int CLIMB_WITH_JOYSTICK = 4;
        public static final int CLIMB_DOWN_BUTTON = 5;
        public static final int CLIMB_UP_BUTTON = 6;
        public static final int TRAINING_WHEELS_SLOW_FORWARD_BUTTON = 7;
        public static final int TRAINING_WHEELS_FORWARD_BUTTON = 8;
        public static final int RETRACT_SUPER_STRUCTURE_PISTONS_BUTTON = 11; // MAYBE NOT
        public static final int EXTEND_SUPER_STRUCURE_PISTONS_BUTTON = 12; 

        /** OTHER INPUT CONSTANTS */
        public static final double JOYSTICK_DEADBAND = 0.1;
        public static final double VERTICAL_BOWTIE_DEADZONE_SLOPE = 10;
        public static final double HORIZONTAL_BOWTIE_DEADZONE_SLOPE = 10;
        public static final double JOYSTICK_DEADZONE_CIRCLE = 0.14;
        public static final double MAX_DESIRED_TURN_RADIUS = 40;
       
    }

    public class TShirtCannon {
		// INTAKE ARM PORTS
        public static final int TURRET_MOTOR_PORT = 6;
        public static final int CANNON_MOTOR_PORT = 3;

        public static final boolean IS_CANNON_INVERTED = false; //TODO: Test

        // TURRET PID
        public static final double TURRET_P = 0.025; // TODO: Tune
		
		// CANNON CONSTANTS
		public static final double CANNON_POWER = 1.0;
		public static final double CANNON_FIRE_TIME = 0.29;
    }

    public class Climb {
        // Climb Motor Ports
        public static final int LEFT_MOTOR_1_PORT = 8;
        public static final int LEFT_MOTOR_2_PORT = 9;
        public static final int LEFT_MOTOR_3_PORT = 10;
        public static final int RIGHT_MOTOR_1_PORT = 11;
        public static final int RIGHT_MOTOR_2_PORT = 12;
		public static final int RIGHT_MOTOR_3_PORT = 13;
		// Training Wheel Port
		public static final int TRAINING_WHEEL_MOTOR_PORT = 3;

        // Climb Side Inversions
        public static final boolean IS_LEFT_INVERTED = false;
        public static final boolean IS_RIGHT_INVERTED = true;

        // Climb Solenoid Ports
        public static final int SUPER_STRUCTURE_SOLENOID_PORT_1 = 0;
        public static final int SUPER_STRUCTURE_SOLENOID_PORT_2 = 1;

        public static final int HATCH_PISTON_SOLENOID_PORT_1 = 2;
        public static final int HATCH_PISTON_SOLENOID_PORT_2 = 3;

        // Climb Constants
        public static final double CLIMB_UP_SPEED = 0.25; // Cannot be higher without limit switches for safety, tested 3/3/2019, we now use Joystick
		public static final double CLIMB_DOWN_SPEED = -0.25; // See above
        public static final double CLIMB_HOLD_POWER = 0.03025; // To hold the elevator at the same height when driving around Tested 3/17/2019 by James, moves elevator up slowly until it stops
        public static final double CLIMB_HOLD_POWER_INCREMENT = 0.000000000001; //Increment to find holding power
		public static final double CLIMB_ONE_HOLD_POWER = 0.02; // To hold the elevator at the same height when doing single climb Tested 3/17/2019 by James, does not move if climb1
        public static final double CLIMB_VELOCITY_TOLERANCE = 1; // Minimum velocity allowed for HoldElevator command TODO: Test Value

        // Tested setpoints
		public static final double TOP_ENCODER_VALUE = 129.2132568359375; // The rotaions measured at the top of the climb. used for
                                                             // calibrating encoders. THIS IS IN ROTATIONS, NOT ENCODER
															 // TICKS.
		public static final double BOTTOM_ENCODER_VALUE = 0.0; // The rotations at the bottom limit switch
		
		// Training Wheel Constants
        public static final double TRAINING_WHEEL_FORWARD_SPEED = 1.0; // Add OpenLoopRampRate
        public static final double TRAINING_WHEEL_SLOW_FORWARD_SPEED = 0.3;
		public static final double TRAINING_WHEEL_BACKWARD_SPEED = -0.3;
		public static final double TRAINING_WHEEL_RAMP_RATE = 0.5; // Seconds from 0 to full power.
		public static final int TRAINING_WHEEL_TIMEOUT_MS = 30; // If something goes wrong, it takes 30 ms before it can move again
    }
}
