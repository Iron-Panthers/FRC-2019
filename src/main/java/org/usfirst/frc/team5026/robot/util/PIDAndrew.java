package org.usfirst.frc.team5026.robot.util;

public class PIDAndrew {
    public double P, I, D, F;

    private double CRUISE_SPEED, MAX_ACCEL, MAX_OUTPUT;

    private double lastError, sumError, target, p0;
    private long lastErrorTime, startTime;

    private double t1, t2, t3, m1, m2, v0;


    public PIDAndrew(double P, double I, double D, double F) {
        this.P = P;
        this.I = I;
        this.D = D;
        this.F = F;
        this.MAX_OUTPUT = 1;
    }

    private boolean setTarget(double d) {
        if (CRUISE_SPEED == 0 || MAX_ACCEL == 0) {
            return false;
        }

        double aMax = MAX_ACCEL * Math.signum(d);
        double vMax = CRUISE_SPEED * Math.signum(d);

        this.m1 = aMax;
        if ((this.v0 < vMax && vMax < 0) || (this.v0 > vMax && vMax > 0)) {
            this.m1 = -aMax;
        }

        this.m2 = -aMax;
        this.t1 = (vMax - this.v0) / this.m1;
        this.t3 = -vMax / this.m2;
        this.t2 = d / vMax - 0.5 * (this.t3 + this.t1 * this.v0  / vMax + this.t1);

        if (this.t2 < 0) {
            if (Math.signum(this.m1) != Math.signum(vMax)) {
                return false;
            }

            this.t2 = 0;
            double vMaxTriangle = Math.signum(d) * Math.sqrt(aMax * d + this.v0*this.v0 / 2);
            if (Math.abs(vMaxTriangle) > CRUISE_SPEED) {
                vMaxTriangle = vMax;

                if (Math.abs(vMaxTriangle) > MAX_ACCEL) {
                    aMax = (this.v0 * this.v0 - 2 * vMax * vMax) / (2 * d);
                    if (Math.abs(aMax) > MAX_ACCEL) {
                        return false;
                    }
                } else {
                    aMax = vMaxTriangle;
                }

                this.m1 = aMax;
                this.m2 = -aMax;
            }
            this.t1 = (vMax - this.v0) / this.m1;
            this.t3 = -vMax / this.m2;
        }

        if (this.t1 < 0 || this.t2 < 0 || this.t3 < 0) {
            return false;
        }

        this.startTime = System.currentTimeMillis();
        return true;
    }

    private double getPathPosition(double t) {
        if (t <= this.t1) {
            double t0 = 0;
            return this.p0 + this.getPathSpeed(t0)*t + this.getPathAccel(t0)*t*t/2;
        } else if (t <= this.t1 + this.t2) {
            double t0 = this.t1;
            double dt = t - this.t1;
            return this.getPathPosition(t0) + this.getPathSpeed(t0)*dt + this.getPathAccel(t0)*dt*dt/2;
        } else if (t <= this.t1 + this.t2 + this.t3) {
            double t0 = this.t1 + this.t2;
            double dt = t - this.t1 - this.t2;
            return this.getPathPosition(t0) + this.getPathSpeed(t0)*dt + this.getPathAccel(t0)*dt*dt/2;
        } else {
            return this.getPathPosition(this.t1 + this.t2 + this.t3);
        }
    }

    private double getPathSpeed(double t) {
        if (t <= this.t1) {
            return this.v0 + this.getPathAccel(0) * t;
        } else if (t <= this.t1 + this.t2) {
            return getPathSpeed(this.t1);
        } else if (t <= this.t1 + this.t2 + this.t3) {
            return this.getPathSpeed(this.t1 + this.t2) + this.getPathAccel(this.t1 + this.t2) * (t - this.t1 - this.t2);
        } else {
            return this.getPathSpeed(this.t1 + this.t2 + this.t3);
        }
    }

    private double getPathAccel(double t) {
        if (t < this.t1) {
            return this.m1;
        } else if (t < this.t1 + this.t2) {
            return 0;
        } else if (t < this.t1 + this.t2 + this.t3) {
            return this.m2;
        } else {
            return 0;
        }
    }

    private double getPathTime() {
        return (System.currentTimeMillis() - this.startTime) / 1000.0;
    }

    public double getMotorPower(double currentPosition, double currentSpeed, double target) {

        double pathTime = getPathTime();
        double pathPosition = getPathPosition(pathTime);

        double error = pathPosition - currentPosition;
        sumError += error;
        double dt = (System.currentTimeMillis() - this.lastErrorTime) / 1000.0;
        double deltaError = 0;

        if (this.lastErrorTime != 0 && dt != 0) {
            deltaError = (error - lastError) / dt;
        }

        lastError = error;
        this.lastErrorTime = System.currentTimeMillis();

        this.p0 = currentPosition;
        this.v0 = currentSpeed;
        this.target = target;
        setTarget(this.target - currentPosition);

        pathTime = getPathTime();
        double pathSpeed = getPathSpeed(pathTime);

        double output = this.P * error + this.I * sumError + this.D * deltaError + this.F * pathSpeed;
        if (Math.abs(output) > MAX_OUTPUT) {
            output = Math.signum(output) * MAX_OUTPUT;
        }

        return output;
    }

    public double getMotorPower(double currentPosition, double currentSpeed) {

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
        if (cruiseSpeed != 0) {
            this.CRUISE_SPEED = Math.abs(cruiseSpeed);
        }
    }

    public void setMaxAccel(double maxAccel) {
        if (maxAccel != 0) {
            this.MAX_ACCEL = Math.abs(maxAccel);
        }
    }

    public void setMaxOutput(double maxOutput) {
        this.MAX_OUTPUT = Math.abs(maxOutput);
    }

}
