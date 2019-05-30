package org.usfirst.frc.team5026.robot.util;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public  class SwerveMath {

    /**
     * Returns angle between two angles. Angles must be from 0 to 360 degrees.
     * If the first angle is more counter-clockwise, result will be positive, if not (you guessed it) 
     * it will be negative. Results will be between -180 to 180
     * @param angle1
     * @param angle2
     * @return
     */
    public static double getAngleDifference(double angle1, double angle2) {

        double difference = angle1 - angle2;
        if(Math.abs(difference) > 180) {
			difference = (difference < 0) ? difference + 360 : difference - 360;
        }
        return difference;

    }


    /**
     * get the amount turn is taken into account for inner motors 
     * based on where the swerve motors are being pointed
     * @param angle
     * @return
     */
    public static double getInnerTurnModifier(double angle) {
        return getAngleDifference(getClosestCornerAngle(angle), angle) * Constants.Drivebase.CORNER_DIFF_TO_TURN_MODIFIER;
    }

    /**
     * get the amount turn is taken into account for outer motors 
     * based on where the swerve motors are being pointed
     * @param angle
     * @return
     */
    public static double getOuterTurnModifier(double angle) {
        return (90 - getAngleDifference(getClosestCornerAngle(angle), angle) ) * Constants.Drivebase.CORNER_DIFF_TO_TURN_MODIFIER;
    }

    public static double getClosestCornerAngle(double angle) {

        //the four corners of the earth (or the robot)
        int[] corners = new int[] {45, 135, 225, 315};

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

        double swerveAngDiffFromPosAng = getAngleDifference(swerveAngle, motorPositionAngle);
        if(swerveAngDiffFromPosAng >= 0) {

            if(Math.abs(90 - swerveAngDiffFromPosAng) >= 45) {

                return DrivebasePosition.INNER_RIGHT;
            }

            else {

                return DrivebasePosition.OUTER_RIGHT;
            }
        }

        else {
            if(Math.abs(90 + swerveAngDiffFromPosAng) >= 45) {

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
        int[] positionAngles = new int[] {45, 135, 315, 225};

        TalonSRX innerRight = preOrderedControllerList[0];
        TalonSRX innerLeft = preOrderedControllerList[1];
        TalonSRX outerRight = preOrderedControllerList[2];
        TalonSRX outerLeft = preOrderedControllerList[3];

        //go through each controller in the input list and assign it to one of the DriveBasePositions
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


}