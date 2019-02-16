/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

/**
 * Add your docs here.
 */
public class PathSeeker {
    private static double initialAngleToTurn;
    private static double distance;
    private static double angleToTurn;
    public static void seekPath(double x1, double y1, double angle){

        double ym = Constants.DriveStraight.MINIMUM_DISTANCE;
        double a = angle / 180 * Math.PI;
        double p = Math.PI/2;
        if (x1 != 0){
            p = Math.atan(y1 / x1);
        }
        double t1 = x1 / Math.cos(a+p);

        double dy = t1 * Math.sin(a+p);
        double d1 = Math.sqrt(x1*x1 + dy*dy);
        double d2 = Math.sqrt(x1*x1 + (ym-y1) * (ym-y1));

        double a3 = -Math.signum(p) * Math.acos((x1*x1 - dy * (ym-y1)) / (d1 * Math.sqrt(x1*x1 + (ym-y1)*(ym-y1))));
        double a2 = Math.signum(p) * (Math.abs(a+p) - Math.PI/2);
        double a1 = a2 + a3;

        if (y1 - dy < ym) {
            initialAngleToTurn = a3;
            distance = d2;
            angleToTurn = a1;
        } else {
            initialAngleToTurn = 0;
            distance = d1;
            angleToTurn = a2;
        }
    }
    public static double getAngle() {
        return angleToTurn;
    }
    public static double getDistance() {
        return distance;
    }
    public static double getInitialAngle() {
        return initialAngleToTurn;
    }
}
