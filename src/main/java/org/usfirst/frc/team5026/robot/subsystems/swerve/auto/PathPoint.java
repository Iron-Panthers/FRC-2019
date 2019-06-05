package org.usfirst.frc.team5026.robot.subsystems.swerve.auto;

import org.usfirst.frc.team5026.robot.subsystems.swerve.SwerveMath;

public class PathPoint {

    double x;
    double y;
    double theta;

    /**
     * point object for swerve auto paths. x and y are in inches, and theta is in degrees.
     */
    public PathPoint(double x, double y, double theta) {
        this.x = x;
        this.y = y;
        this. theta = theta;

    }

    public double getHeadingTo(PathPoint otherPoint) {

        return SwerveMath.getHeadingFromPoint(otherPoint.x - x, otherPoint.y - y);

    }

    public double getDistanceTo(PathPoint otherPoint) {
        double deltaX = x - otherPoint.x;
        double deltaY = y - otherPoint.y;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);

    }

}