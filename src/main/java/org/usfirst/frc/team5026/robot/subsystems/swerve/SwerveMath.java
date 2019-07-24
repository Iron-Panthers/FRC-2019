package org.usfirst.frc.team5026.robot.subsystems.swerve;

import org.usfirst.frc.team5026.robot.subsystems.swerve.hardware.SwerveModule;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public  class SwerveMath {


    /**
     * bounds angle between -180 and 180. Useful for measuring headings from 0 being facing forward.
     * @param angle
     * @return
     */
    public static double boundToHeading(double angle) {
        double boundedAngle = ((angle % 360) + 360) % 360;
		if(boundedAngle > 180) {
			boundedAngle -= 360;
        }
        return boundedAngle;
    }

    /**
     * Returns difference between two angles.
     * If the second angle is larger than the first, result will be positive, if not 
     * (you guessed it) it will be negative. Results will be between -180 to 180
     * @param angle1
     * @param angle2
     * @return
     */
    public static double getAngleDifference(double startAngle, double endAngle) {

        double difference = endAngle - startAngle;
        return boundToHeading(difference);

    }

    /**
     * calculates the heading of a vector going from 0,0 to a point x,y. Returns between -180 and 180
     * @param x
     * @param y
     * @return
     */
    public static double getHeadingFromPoint(double x, double y) {
        double referenceAngle = 90 - (360/Math.PI) * Math.atan( Math.abs(y/x) );
		if(x >= 0 && y > 0) {
			return referenceAngle;
		}
		else if(x < 0 && y > 0) {
			return -referenceAngle;
		}
		else if(x < 0 && y <= 0) {
			return referenceAngle - 180;
		}
		else if(x >= 0 && y <= 0) {
			return 180 - referenceAngle;
		}
		return 0.0;
	}


    /**
     * get the amount turn is taken into account for inner motors 
     * based on where the swerve motors are being pointed
     * @param angle
     * @return
     */
    public static double getInnerTurnModifier(double angle) {
        double angularDistanceFromClosestCorner = Math.abs(getAngleDifference(getClosestCornerAngle(angle), angle));
        double rawModifier = angularDistanceFromClosestCorner * Constants.SwerveDrive.CORNER_DIFF_TO_TURN_MODIFIER;

        double modifier = (Math.sin(Math.PI * (rawModifier - 1/2) ) + 1 ) / 2;
        //this function smooths out the effect of the modifier, hopefully providing provide better protection against 
        //wheel slip, and also better utilization of the inner wheel. See a graph of it at https://www.desmos.com/calculator/bihvxiww5a
        return boundBelowOne(modifier);
    }
    
    //NOT CURRENTLY WANTED OR NEEDED. UNCOMMENT IF WE DECIDE WE THINK THIS IS GONNA MAKE TURNING SMOOTHER
    // /**
    //  * get the amount turn is taken into account for outer motors 
    //  * based on where the swerve motors are being pointed
    //  * @param angle
    //  * @return
    //  */
    // public static double getOuterTurnModifier(double angle) {
    //     double angularDistanceFromClosestCorner = Math.abs(getAngleDifference(getClosestCornerAngle(angle), angle));
    //     double modifier = (90 - angularDistanceFromClosestCorner) * Constants.SwerveDrive.CORNER_DIFF_TO_TURN_MODIFIER;
    //     return boundBelowOne(modifier);
    // }

    public static double getClosestCornerAngle(double angle) {

        //the four corners of the earth (or the robot)
        int[] corners = new int[] {-135, -45, 45, 135};

        for(int i : corners) {
            if(Math.abs( getAngleDifference(i, angle) ) <= 45) {
                return i;
            }
        }

        System.out.println("your power level is only 5");
        SmartDashboard.putString("your power level as a human being:", "5");

        return 0.0;

    }

    public static DrivebasePosition getDrivebasePosition(double swerveAngle, double motorPositionAngle) {

        double swerveAngDiffFromPosAng = getAngleDifference(motorPositionAngle, swerveAngle);
        if(swerveAngDiffFromPosAng <= 0) {

            if(Math.abs(swerveAngDiffFromPosAng + 90) >= 45) {

                return DrivebasePosition.INNER_RIGHT;
            }

            else {

                return DrivebasePosition.OUTER_RIGHT;
            }
        }

        else {
            if(Math.abs(swerveAngDiffFromPosAng - 90) >= 45) {

                return DrivebasePosition.INNER_LEFT;
            }

            else {

                return DrivebasePosition.OUTER_LEFT;
            }

        }
    }

    /**
     * Reorders a set of modules, figuring out where they are in relation to the direction the swerve
     * wheels are facing. Returns a list in format {innerRight, innerLeft, outerRight, outerLeft}.
     * @param preOrderedModuleList MUST be formatted as {frontRight, frontLeft, backRight, backLeft}.
     * @param swerveAngle
     * @return
     */
    public static SwerveModule[] organizePositions(SwerveModule[] preOrderedModuleList, double swerveAngle) {
        
        //these represent the angles of the frontRight, frontLeft, backRight, and backLeft motors
        int[] positionAngles = new int[] {45, -45, 135, -135};

        //these aren't really being set here, you can just kind of ignore this. The complier gets
        //mad at me if I don't initialize outside of the switch.
        SwerveModule innerRight = preOrderedModuleList[0];
        SwerveModule innerLeft = preOrderedModuleList[1];
        SwerveModule outerRight = preOrderedModuleList[2];
        SwerveModule outerLeft = preOrderedModuleList[3];

        //go through each controller in the input list and assign it to a new reference
        //based on its DriveBasePosition
        for(int i = 0; i < preOrderedModuleList.length; i++) {
            switch(getDrivebasePosition(swerveAngle, positionAngles[i])) {
                case INNER_RIGHT : 
                    innerRight = preOrderedModuleList[i];
                case INNER_LEFT :
                    innerLeft = preOrderedModuleList[i];
                case OUTER_RIGHT :
                    outerRight = preOrderedModuleList[i];
                case OUTER_LEFT :
                    outerLeft = preOrderedModuleList[i];
                default :
                    System.out.println("a problem occured with identifying drive motor positions");
                    SmartDashboard.putString("a problem occured with identifying drive motor positions", "");
            }
        }

        return new SwerveModule[] {innerRight, innerLeft, outerRight, outerLeft};
    }

    /**
     * bounds a number to a max of 1.0; 2 returns 1.0; 0.5 returns 0.5
     * @param value
     * @return
     */
    public static double boundBelowOne(double value) {
        return (value <= 1) ? value : 1;
    }

    /**
     * maintains a constant ratio between two numbers, while changing the larger number to 1. For example,
     * 2 and 1 will return 1 and 0.5; 0.2 and 0.3 would return .667 and 1
     * @return
     */
    public static double[] ratioToOne(double arg0, double arg1) {

        double reducedArg0;
        double reducedArg1;

        if(arg0 > arg1) {
            reducedArg0 = 1;
            reducedArg1 = arg1/arg0;
        }
        else {
            reducedArg0 = arg0/arg1;
            reducedArg1 = 1;
        }

        return new double[] {reducedArg0, reducedArg1};

    }
}