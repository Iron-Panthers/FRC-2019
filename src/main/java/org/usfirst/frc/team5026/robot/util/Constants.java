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

	public class LineFollow {
		//LINEFOLLOW CONSTANTS
		public static final double ODS_TAPE_SEEN = .1; //TODO the light value after which the line has "been seen"
		//by the center of the robot the value needs to be calibrated based on tape, flooring, and ambient light
		public static final double BIGWALL = 10000; //TODO the current value over which the robot will 
		//stop driving because it has run into a wall
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
}