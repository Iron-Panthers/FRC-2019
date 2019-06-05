package org.usfirst.frc.team5026.robot.util;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import org.usfirst.frc.team5026.robot.subsystems.swerve.SwerveMath;

public class SuperiorGyro extends PigeonIMU {
    private double[] ypr;
    public SuperiorGyro(TalonSRX controller) {
        super(controller);
        ypr = new double[3];
    }

	/**
	 * Updates array of yaw, pitch, and roll with the current gyro values
	 */
	public void updateGyro() {
		getYawPitchRoll(ypr);
	}

	/**
	 * Updates gyro and returns yaw
	 * 
	 * @return Yaw of the gyro
	 */
	public double getYaw() {
		updateGyro();
		return ypr[0];
	}

	/**
	 * Updates gyro and returns yaw, bounded between -180 and 180 degrees.
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


}