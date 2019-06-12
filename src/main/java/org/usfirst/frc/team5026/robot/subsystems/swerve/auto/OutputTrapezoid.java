package org.usfirst.frc.team5026.robot.subsystems.swerve.auto;

import org.usfirst.frc.team5026.robot.subsystems.swerve.SwerveMath;
import org.usfirst.frc.team5026.robot.util.Constants;

public class OutputTrapezoid {

    double targetDelta;
    double distanceToAccel;

    public OutputTrapezoid(double targetDelta, double distanceToAccel) {

        this.targetDelta = targetDelta;
        if(targetDelta < 2 * distanceToAccel);
        
        this.distanceToAccel = distanceToAccel;
    }

    /**
     * Finds a modifier from 0 to 1, based on how far along on the trapezoid a given value is.
     */
    public double getModifier(double delta) {

        if(delta < targetDelta / 2) {
            return SwerveMath.boundBelowOne(delta / distanceToAccel);
        }

        return SwerveMath.boundBelowOne((targetDelta - delta) / distanceToAccel);
    }


}