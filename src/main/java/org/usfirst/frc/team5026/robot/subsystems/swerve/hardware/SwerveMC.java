package org.usfirst.frc.team5026.robot.subsystems.swerve.hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
     * set the motor controller to PDF (no I as of now) towards a desired angle. "forward angle" means that
     * the swerve motor will point the drive motor in the forward direction toward desiredAngle
     * The angle is going to be relative to encoder absolute 0 so make sure to zero encoder when you can.
     * @param desiredAngle
     * @param f
     */
    public void moveToForwardAngle(double desiredAngle, double f) {

        double currentError = SwerveMath.getAngleDifference(getAngle(), desiredAngle);
        double power = f + currentError * p + getDeltaError(currentError) * d;
        set(ControlMode.PercentOutput, power);

    }

     /**
     * set the motor controller to PDF (no I as of now) towards a desired angle. "backward angle" means that
     * the swerve motor will point the drive motor in the backward direction toward desiredAngle
     * The angle is going to be relative to encoder absolute 0 so make sure to zero encoder when you can.
     * @param desiredAngle
     * @param f
     */
    public void moveToBackwardAngle(double desiredAngle, double f) {

        double currentError = SwerveMath.getAngleDifference(getAngle() - 180, desiredAngle);
        double power = f + currentError * p + getDeltaError(currentError) * d;
        set(ControlMode.PercentOutput, power);

    }

    /**
     * checks, for a given angle, whether it would be more efficient to turn the swerve motor to face the 
     * drive motor forwards, or backwards
     * @return
     */
    public boolean checkForwardsMoreEfficient(double desiredAngle) {

        //getAngle() returns forward heading. Thus, if the difference were greater than 90,
        // it would be more efficient to go backwards, as the angle would have to be closer to 180 away from the forward heading
        return SwerveMath.getAngleDifference(getAngle(), desiredAngle) <= 90;
    }

    /**
     * get the forward heading of the motor from -180 to 180
     * @return
     */
    public double getAngle() {

        double overflowAngle = getSelectedSensorPosition() / Constants.SwerveDrive.SWERVE_MOTOR_TICKS_PER_DEGREE;

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