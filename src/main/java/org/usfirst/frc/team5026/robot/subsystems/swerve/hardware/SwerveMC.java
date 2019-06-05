package org.usfirst.frc.team5026.robot.subsystems.swerve.hardware;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5026.robot.subsystems.swerve.SwerveMath;
import org.usfirst.frc.team5026.robot.util.Constants;

public class SwerveMC extends TalonSRX {

    private double error;
    double p;
    double d;

    public SwerveMC(int port, double p, double d) {
        super(port);
        
        error = Double.MAX_VALUE;
        this.p = p;
        this.d = d;

    }

    public void configPD(double p, double d) {
        this.p = p;
        this.d = d;
    }

    /**
     * set the motor controller to PDF (no I as of now) towards a desired angle. 
     * The angle is going to be relative to encoder absolute 0 so make sure to zero encoder when you can.
     * @param desiredAngle
     * @param f
     */
    public void set(double desiredAngle, double f) {
        double currentError = SwerveMath.getAngleDifference(getAngle(), desiredAngle);
        double power = f + currentError * p + getDeltaError(currentError) * d;
    }

    /**
     * get the angle of the motor from -180 to 180
     * @return
     */
    public double getAngle() {

        double overflowAngle = getSelectedSensorPosition() / Constants.Swerve.SWERVE_MOTOR_TICKS_PER_DEGREE;

        return SwerveMath.boundToHeading(overflowAngle);
    }

    /** 
     * get change in error since last measurement. Also updates stored error.
    */
    private double getDeltaError(double newError) {
        if(error == Double.MAX_VALUE) {
            error = newError;
            return 0.0;
        }
        double deltaError = newError - error;
        error = newError;
        return deltaError;
    }

}