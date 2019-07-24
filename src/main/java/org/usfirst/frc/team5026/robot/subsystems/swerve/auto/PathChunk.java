package org.usfirst.frc.team5026.robot.subsystems.swerve.auto;

import org.usfirst.frc.team5026.robot.subsystems.swerve.SwerveMath;
import org.usfirst.frc.team5026.robot.util.Constants;

public class PathChunk {

    double targetDeltaPositionInches;
    double targetAbsoluteSwerveAngle;
    double targetDeltaAngle;
    double targetDeltaAngleInches;

    double encDelta;
    double gyroDelta;

    double turnToForwardRatio;
    double baseTurn;
    double baseForward;

    OutputTrapezoid forwardProfile;
    OutputTrapezoid angleProfile;

    double ticksPerInch;

    double forwardMaintenanceP;
    double angleMaintenanceP;
    
    public PathChunk(PathPoint start, PathPoint end, double ticksPerInch, double ticksToAccelerate, 
    double degreesToAccelerate, double turningCircumference, double forwardMaintenanceP, double angleMaintenanceP) {

        this.ticksPerInch = ticksPerInch;
        this.forwardMaintenanceP = forwardMaintenanceP;
        this.angleMaintenanceP = angleMaintenanceP;

        targetAbsoluteSwerveAngle = start.getHeadingTo(end);
        targetDeltaPositionInches = start.getDistanceTo(end);
        targetDeltaAngle = SwerveMath.getAngleDifference(start.theta, end.theta);
        targetDeltaAngleInches = (targetDeltaAngle / 360) * turningCircumference;

        double[] turnAndForwardList = SwerveMath.ratioToOne(targetDeltaAngleInches, targetDeltaPositionInches);
        baseTurn = turnAndForwardList[0];
        baseForward = turnAndForwardList[1];

        forwardProfile = new OutputTrapezoid(targetDeltaPositionInches * ticksPerInch, ticksToAccelerate);
        angleProfile = new OutputTrapezoid(targetDeltaAngle, degreesToAccelerate);

    }

    public void update(double encDelta, double gyroDelta) {
        this.encDelta = encDelta;
        this.gyroDelta = gyroDelta;

    }

    public double getForward() {
        double encBasedForwardOutput = baseForward * forwardProfile.getModifier(encDelta);
        double desiredAngleBasedForwardProgress = (gyroDelta / targetDeltaAngle) * targetDeltaPositionInches;
        double angleBasedForwardError = (encDelta / ticksPerInch) - desiredAngleBasedForwardProgress;
        double trapazoidalPower = encBasedForwardOutput - forwardMaintenanceP * angleBasedForwardError;

        //this term makes sure that the robot is actually strafing at all times - it's sort of like a baseline feedforward term
        double minimumForwardMovement = baseForward * Constants.SwerveDrive.MINIMUM_AUTO_MOVEMENT;
        return minimumForwardMovement + (1 - minimumForwardMovement) * trapazoidalPower;

    }

    public double getTurn() {
        double angleBasedTurnOutput = baseTurn * angleProfile.getModifier(gyroDelta);
        double desiredForwardBasedAngleProgress = ( (encDelta / ticksPerInch) / targetDeltaPositionInches) * targetDeltaAngle;
        double forwardBasedAngleError =  gyroDelta - desiredForwardBasedAngleProgress;
        double trapazoidalTurn = angleBasedTurnOutput - angleMaintenanceP * forwardBasedAngleError;

        //see above explanation, but this time it's for angle, so for turning.
        double minimumAngleMovement = baseTurn * Constants.SwerveDrive.MINIMUM_AUTO_MOVEMENT;
        return minimumAngleMovement + (1 - minimumAngleMovement) * trapazoidalTurn;

    }

}