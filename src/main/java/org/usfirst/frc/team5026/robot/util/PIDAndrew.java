package org.usfirst.frc.team5026.robot.util;

public class PIDAndrew {
    public double P, I, D, F;

    private double CRUISE_SPEED, MAX_ACCEL, MAX_OUTPUT;

    private double lastError, sumError, target;
    private long lastErrorTime, startTime;

    private double t1; // The time in seconds for the first leg of the trapezoid, the velocity begins
    // at v0
    private double t2; // The time in seconds for the middle leg of the trapezoid, the velocity remains
    // constants on this leg
    private double t3; // The time in seconds for the last leg of the trapzeoid, the velocity ends at 0
    private double m1; // The slope of the first leg of the trapezoid
    private double m2; // The slope of the last leg of the trapezoid
    private double p0; // The initial position of the sensor (this is needed because of the +C in
    // integration)
    private double v0; // The initial velocity of the sensor

    /**
     * This class creates a trapezoidal velocity path to get from the current
     * position to a target position.
     * 
     * It's worth it to call the reset() method in the initialize of the command that uses this
     */
    public PIDAndrew(double P, double I, double D, double F) {
        this.P = P;
        this.I = I;
        this.D = D;
        this.F = F;
        this.MAX_OUTPUT = 1;
    }

    /**
     * Creates a motion path for the given distance, assuming p0, v0, CRUISE_SPEED, and MAX_ACCEL
     * have already been set.
     */
    private void setTarget(double d) {
        if (CRUISE_SPEED == 0 || MAX_ACCEL == 0) { // If either of these are 0, we can't ever go anywhere
            return;    
            //throw new IllegalArgumentException();
        }

        // Save path if we need to reset to it
        double t1O = this.t1;
        double t2O = this.t2;
        double t3O = this.t3;
        double m1O = this.m1;
        double m2O = this.m2;

        double aMax = MAX_ACCEL * Math.signum(d); // The maximum acceleration is generally the same sign as the distance
        double vMax = CRUISE_SPEED * Math.signum(d); // See above

        this.m1 = aMax; // The first leg of the trapezoid generally accelerates to CRUISE_SPEED...
        boolean decel = false;
        if ((this.v0 < -CRUISE_SPEED && vMax < 0) || (this.v0 > CRUISE_SPEED && vMax > 0)) { // Except if we are already going too fast
            this.m1 = -aMax; // Then we decelerate to the CRUISE_SPEED
            decel = true;
        }

        this.m2 = -aMax; // The last leg of the trapezoid always decelerates from CRUISE_SPEED to 0
        this.t1 = (vMax - this.v0) / this.m1; // Simple algebra, delta V = A * delta T
        this.t3 = -vMax / this.m2; // See above
        this.t2 = d / vMax - 0.5 * (this.t3 + this.t1 * this.v0 / vMax + this.t1);
        // The above formula for t2 is based off the area of the trapezoid. It's not worth explaining the algebra here
        // All I'll say is that     distance traveled = t1/2 * (v0 + vM) + t2*vM + t3*vM/2
        // Each of those terms is one part of the trapezoid

        if (decel && this.t2 < 0) { // If we decelerated to vMax but we didn't have enough time
            vMax *= -1; // Then we should invert vMax (just look at graph of trapezoid and this will make sense)
            aMax *= -1; // Therefore our aMax must also be inverted (otherwise we'd accelerate the wrong way)
            this.m1 = aMax; // Now we just recompute everything like above
            this.m2 = -aMax;
            this.t1 = (vMax - this.v0) / this.m1;
            this.t3 = -vMax / this.m2;
            this.t2 = d / vMax - 0.5 * (this.t3 + this.t1 * this.v0 / vMax + this.t1);
        }

        if (this.t2 < 0) { // If we don't have enough time to do the path above, then we must be unable to reach CRUISE_SPEED

            this.t2 = 0; // Therefore we'll make a triangle instead of a trapezoid
            vMax = Math.signum(vMax) * Math.sqrt(aMax * d + this.v0 * this.v0 / 2); // And set our vMax accordingly
            this.t1 = (vMax - this.v0) / this.m1; // Recalculate times
            this.t3 = -vMax / this.m2;

            if (this.t1 < 0) { // It might not be possible to get from v0 to vMax in time
                vMax *= -1; // Therefore we'll invert vMax (like before, except this time with a triangle)
                this.m1 *= -1; // Then m1 and m2 have to invert as well
                this.m2 *= -1;
                this.t1 = (vMax - this.v0) / this.m1; // Recalculate times
                this.t3 = -vMax / this.m2;
            }

        }

        if (this.t1 < 0 || this.t2 < 0 || this.t3 < 0) {
            this.t1 = t1O; // Reset the path if new path failed to generate
            this.t2 = t2O;
            this.t3 = t3O;
            this.m1 = m1O;
            this.m2 = m2O;
            return;
            // throw new IllegalArgumentException();
        }

        this.startTime = System.currentTimeMillis(); // Make current time be t = 0
    }

    /**
     * Get the position that the path says we should be at, given the current time (if the path began at t = 0)
     */
    public double getPathPosition(double t) {
        if (t <= this.t1) { // If we are in the first leg
            double t0 = 0; // Then initial time of this leg is just 0
            return this.p0 + this.getPathSpeed(t0) * t + this.getPathAccel(t0) * t * t / 2; // Then we use this well known formula
            // In physics its usually 1/2at^2 + v_0*t + x_0
        } else if (t <= this.t1 + this.t2) { // If we are in the second leg
            double t0 = this.t1; // Then initial time of this leg is the end of the last
            double dt = t - this.t1; // And our current time is relative to the end of the last leg's
            return this.getPathPosition(t0) + this.getPathSpeed(t0) * dt + this.getPathAccel(t0) * dt * dt / 2;
            // Get the position at the end of last leg, and add onto it using the normal formula
        } else if (t <= this.t1 + this.t2 + this.t3) { // If we are in the third leg
            double t0 = this.t1 + this.t2; // Same exact deal as in the second leg
            double dt = t - this.t1 - this.t2;
            return this.getPathPosition(t0) + this.getPathSpeed(t0) * dt + this.getPathAccel(t0) * dt * dt / 2;
        } else { // If we are past the third leg
            return this.getPathPosition(this.t1 + this.t2 + this.t3);   // Just maintain the position
            // Fun trivia: Adam forgot to do this in his motion planning
            // library (can't remember if it was Scadlib or just trapezoids),
            // and it resulted in robot crashing into things LMAO
        }
    }

    /**
     * Get the current speed of the path (very similar to getPathPosition)
     */
    public double getPathSpeed(double t) {
        if (t <= this.t1) { // This method is almost the same as getPathPosition
            return this.v0 + this.getPathAccel(0) * t;
            // Only difference is we use v = a*t + v_0
            // because we want the velocity (all of these formula assume acceleration is constant, which is true for each leg)
        } else if (t <= this.t1 + this.t2) {
            return getPathSpeed(this.t1);
        } else if (t <= this.t1 + this.t2 + this.t3) {
            return this.getPathSpeed(this.t1 + this.t2) + this.getPathAccel(this.t1 + this.t2) * (t - this.t1 - this.t2);
        } else {
            return this.getPathSpeed(this.t1 + this.t2 + this.t3);
        }
    }

    /**
     * Get the current acceleration of the path
     */
    private double getPathAccel(double t) {
        if (t < this.t1) { // Self explanatory
            return this.m1;
        } else if (t < this.t1 + this.t2) {
            return 0; // Our acceleration is 0 during the cruising section
        } else if (t < this.t1 + this.t2 + this.t3) {
            return this.m2;
        } else {
            return 0;
        }
    }

    /**
     * Get the time in seconds relative to when the path began
     */
    public double getPathTime() {
        return (System.currentTimeMillis() - this.startTime) / 1000.0;
    }

    /**
     * Given the current position and speed of the sensor, and the target (where we want the sensor to be),
     * compute the motor power using PIDF and the trapezoidal velocity curve.
     *
     * This method recomputes the motion profile every time it's called, which might be better than setting it once
     * at the beginning? I'm not sure, testing is required.
     */
    public double getMotorPower(double currentPosition, double currentSpeed, double target) {

        // We want to see our error relative to the old path before we compute the new path
        double pathTime = getPathTime(); // find t
        double pathPosition = getPathPosition(pathTime); // find expected position at t

        double error = pathPosition - currentPosition; // find error
        sumError += error; // accumulate error
        double dt = (System.currentTimeMillis() - this.lastErrorTime) / 1000.0; // find change in time for deltaError
        double deltaError = 0;

        if (dt != 0) { // check for divide by 0
            deltaError = (error - lastError) / dt;  // delta error should be in units of units/time, otherwise a fluctuating
            // time step will cause delta error to change
        }

        lastError = error;
        this.lastErrorTime = System.currentTimeMillis();

        // This is the part where we compute the new motion profile
        this.p0 = currentPosition;
        this.v0 = currentSpeed;
        this.target = target;
        setTarget(this.target - currentPosition);

        pathTime = getPathTime();
        double pathSpeed = getPathSpeed(pathTime); // Get our speed in the new motion profile

        double output = this.P * error + this.I * sumError + this.D * deltaError + this.F * pathSpeed; // BIG BOI formula, BIG BOIS only
        if (Math.abs(output) > MAX_OUTPUT) { // Clamp output
            output = Math.signum(output) * MAX_OUTPUT;
        }

        return output;
    }

    /**
     * Given the current position and speed of the sensor, and the target (where we want the sensor to be),
     * compute the motor power using PIDF and the trapezoidal velocity curve.
     *
     * This method does not recompute the motion profile every time it's called, unlike the OTHER getMotorPower.
     */
    public double getMotorPower(double currentPosition, double currentSpeed) {

        // This is pretty much identitical to the other function, except missing the recalculation of the motion profile.
        // See above for code explanation
        double pathTime = getPathTime();
        double pathPosition = getPathPosition(pathTime);
        double pathSpeed = getPathSpeed(pathTime);

        double error = pathPosition - currentPosition;
        sumError += error;
        double dt = (System.currentTimeMillis() - this.lastErrorTime) / 1000.0;
        double deltaError = 0;

        if (this.lastErrorTime != 0 && dt != 0) {
            deltaError = (error - lastError) / dt;
        }

        lastError = error;
        this.lastErrorTime = System.currentTimeMillis();

        double output = this.P * error + this.I * sumError + this.D * deltaError + this.F * pathSpeed;
        if (Math.abs(output) > MAX_OUTPUT) {
            output = Math.signum(output) * MAX_OUTPUT;
        }

        return output;

    }

    public void setCruiseSpeed(double cruiseSpeed) {
        if (cruiseSpeed != 0) { // CRUISE_SPEED of 0 doesn't make sense
            this.CRUISE_SPEED = Math.abs(cruiseSpeed); // Negative CRUISE_SPEED will cause problems
        }
    }

    public void setMaxAccel(double maxAccel) {
        if (maxAccel != 0) { // MAX_ACCEL of 0 doesn't make sense
            this.MAX_ACCEL = Math.abs(maxAccel); // Negative MAX_ACCEL will cause problems
        }
    }

    public void setMaxOutput(double maxOutput) {
        this.MAX_OUTPUT = Math.abs(maxOutput); // Negative MAX_OUTPUT will cause problems
    }

    /**
     * Resets some values that would be odd to save between discrete periods of motion
     */
    public void reset() {
        this.sumError = 0;
        this.lastError = 0;
    }

}
