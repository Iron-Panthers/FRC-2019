package org.usfirst.frc.team5026.robot.util;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import org.usfirst.frc.team5026.robot.subsystems.swerve.SwerveMath;

public class SuperiorGyro extends PigeonIMU {

	double angleAtChunkStart;
	double angleTarget;
    double angleDeadzone;
    public boolean targetReached;
	private double[] ypr;

	/**
	 * Make sure to specify the initial heading of the robot relative to the field. If the robot starts facing straight
	 * forward, away from the driver station, then you pass in 0. If it starts facing dead right, you'd pass in 90, and
	 * -90 would signify dead left. If unsure what to do here, just use 0.
	 * @param controller
	 * @param initialFieldRelativeHeading
	 */
    public SuperiorGyro(TalonSRX controller, double initialFieldRelativeHeading) {
        super(controller);
		ypr = new double[3];
		setYaw(initialFieldRelativeHeading);
    }

	/**
	 * Updates array of yaw, pitch, and roll with the current gyro values
	 */
	public void updateGyro() {
		getYawPitchRoll(ypr);
	}

	/**
	 * Updates gyro and returns raw yaw
	 * mmm... raw yaw
	 * 
	 * @return Yaw of the gyro
	 */
	public double getYaw() {
		updateGyro();
		return ypr[0];
	}

	/**
	 * 
	 * Gets the bounded yaw (between -180 and 180, north is 0 degrees) relative to the field, based on the initial robot heading specified at initialization
	 * @return
	 */
	public double getBoundedYaw() {
		return SwerveMath.boundToHeading(getYaw());
	}

	/**
	 * Sets the gyro yaw to 0 and then updates the ypr array
	 */
	public void resetYaw() {
		setYaw(0);
		updateGyro();
	}

	 /**
     * Call this when you want to reset the data about the current target.
     * @param angleDelta
     * @param angleDeadzoneSize
     */
    public void startNewSegment(double angleDelta, double angleDeadzoneSize) {

		angleAtChunkStart = getBoundedYaw();
        angleTarget = getBoundedYaw() + angleDelta;
        angleDeadzone = angleDeadzoneSize;
        targetReached = false;

	}
	
	public double getDeltaAngle() {
		return SwerveMath.getAngleDifference(angleAtChunkStart, getBoundedYaw());
	}

	/**
     * checks if the target has been reached. Note that this will return true if the current target was reached at ANY point in the past.
     * This is done on purpose for use in auto paths. It provides a safety net in case of overshooting.
     * @return
     */
    public boolean targetReached() {

        if( Math.abs(SwerveMath.getAngleDifference(getBoundedYaw(), angleTarget) ) < angleDeadzone) {
            targetReached = true;
        }
        
        return targetReached;

    }

}