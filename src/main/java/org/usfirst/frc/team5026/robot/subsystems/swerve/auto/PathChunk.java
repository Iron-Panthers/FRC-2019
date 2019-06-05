package org.usfirst.frc.team5026.robot.subsystems.swerve.auto;

import org.usfirst.frc.team5026.robot.subsystems.swerve.SwerveMath;

public class PathChunk {

    double targetEncoderDelta;
    double targetAbsoluteSwerveAngle;
    double targetDeltaRobotAngle;

    public PathChunk(PathPoint start, PathPoint end) {

        targetAbsoluteSwerveAngle = start.getHeadingTo(end);
        targetEncoderDelta = start.getDistanceTo(end);
        targetDeltaRobotAngle = SwerveMath.getAngleDifference(start.theta, end.theta);

    }

    public double getForward(double encoderDelta) {



    }

    public double getTurn(double gyroDelta) {



    }

}