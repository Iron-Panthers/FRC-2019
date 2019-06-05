package org.usfirst.frc.team5026.robot.subsystems.swerve;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5026.robot.subsystems.swerve.input.DrivebasePosition;
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
     * Returns difference between two angles. Angles must be within 360 degrees of each other.
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
        double modifier = angularDistanceFromClosestCorner * Constants.Drivebase.CORNER_DIFF_TO_TURN_MODIFIER;
        return boundBelowOne(modifier);
    }

    /**
     * get the amount turn is taken into account for outer motors 
     * based on where the swerve motors are being pointed
     * @param angle
     * @return
     */
    public static double getOuterTurnModifier(double angle) {
        double angularDistanceFromClosestCorner = Math.abs(getAngleDifference(getClosestCornerAngle(angle), angle));
        double modifier = (90 - angularDistanceFromClosestCorner) * Constants.Drivebase.CORNER_DIFF_TO_TURN_MODIFIER;
        return boundBelowOne(modifier);
    }

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
     * Reorders a set of mcs, figuring out where they are in relation to the direction the swerve
     * wheels are facing. Returns a list in format {innerRight, innerLeft, outerRight, outerLeft}.
     * @param preOrderedControllerList MUST be formatted as {frontRight, frontLeft, backRight, backLeft}.
     * @param swerveAngle
     * @return
     */
    public static TalonSRX[] organizePositions(TalonSRX[] preOrderedControllerList, double swerveAngle) {
        
        //these represent the angles of the frontRight, frontLeft, backRight, and backLeft motors
        int[] positionAngles = new int[] {45, -45, 135, -135};

        //these aren't really being set here, you can just kind of ignore this. The complier gets
        //mad at me if I don't initialize outside of the switch.
        TalonSRX innerRight = preOrderedControllerList[0];
        TalonSRX innerLeft = preOrderedControllerList[1];
        TalonSRX outerRight = preOrderedControllerList[2];
        TalonSRX outerLeft = preOrderedControllerList[3];

        //go through each controller in the input list and assign it to a new reference
        //based on its DriveBasePosition
        for(int i = 0; i < preOrderedControllerList.length; i++) {
            switch(getDrivebasePosition(swerveAngle, positionAngles[i])) {
                case INNER_RIGHT : 
                    innerRight = preOrderedControllerList[i];
                case INNER_LEFT :
                    innerLeft = preOrderedControllerList[i];
                case OUTER_RIGHT :
                    outerRight = preOrderedControllerList[i];
                case OUTER_LEFT :
                    outerLeft = preOrderedControllerList[i];
                default :
                    System.out.println("a problem occured with identifying drive motor positions");
                    SmartDashboard.putString("a problem occured with identifying drive motor positions", "");
            }
        }

        return new TalonSRX[] {innerRight, innerLeft, outerRight, outerLeft};
    }

    /**
     * bounds a number to a max of 1.
     * @param value
     * @return
     */
    public static double boundBelowOne(double value) {
        return (value <= 1) ? value : 1;
    }
}