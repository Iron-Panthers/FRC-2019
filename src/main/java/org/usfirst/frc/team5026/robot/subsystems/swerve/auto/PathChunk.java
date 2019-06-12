package org.usfirst.frc.team5026.robot.subsystems.swerve.auto;

import org.usfirst.frc.team5026.robot.subsystems.swerve.SwerveMath;
import org.usfirst.frc.team5026.robot.util.Constants;

public class PathChunk {

    double targetDeltaPositionInches;
    double targetAbsoluteSwerveAngle;
    double targetDeltaAngleInches;

    double encDelta;
    double gyroDelta;

    double turnToForwardRatio;
    double baseTurn;
    double baseForward;

    OutputTrapezoid forwardProfile;
    OutputTrapezoid angleProfile;
    
    public PathChunk(PathPoint start, PathPoint end) {

        targetAbsoluteSwerveAngle = start.getHeadingTo(end);
        targetDeltaPositionInches = start.getDistanceTo(end);
        double targetDeltaAngle = SwerveMath.getAngleDifference(start.theta, end.theta);
        targetDeltaAngleInches = (targetDeltaAngle / 360) * Constants.DrivebaseProperties.DRIVEBASE_TURNING_CIRCUMFERENCE;

        double[] turnAndForwardList = SwerveMath.ratioToOne(targetDeltaAngleInches, targetDeltaPositionInches);
        baseTurn = turnAndForwardList[0];
        baseForward = turnAndForwardList[1];

        forwardProfile = new OutputTrapezoid(targetDeltaPositionInches * Constants.Drive.TICKS_PER_INCH, Constants.Drive.TICKS_TO_ACCELERATE);
        angleProfile = new OutputTrapezoid(targetDeltaAngle, Constants.Drive.DEGREES_TO_ANGULARLY_ACCELERATE);
    }

    public void update(double encDelta, double gyroDelta) {
        this.encDelta = encDelta;
        this.gyroDelta = gyroDelta;

    }

    public double getForward() {
        return baseForward * forwardProfile.getModifier(encDelta);

    }

    public double getTurn() {
        return baseTurn * angleProfile.getModifier(gyroDelta);

    }

}