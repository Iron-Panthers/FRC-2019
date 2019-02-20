package org.usfirst.frc.team5026.robot.util;

import org.usfirst.frc.team5026.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A wrapper which extends WPILib Joystick (which in turn extends GenericHID).
 * It consists of methods that calculate desired drive output based on
 * tele-operated controller input.
 * <p>
 * If you want to get the actual axes of a JoystickWrapper, use the inherited
 * methods getX() and getY() as usual.
 */
public class JoystickWrapper extends Joystick {
	private double x; // this does not reflect the physical joystick X-axis
	private double y; // this does not reflect the physical joystick Y-axis

	/**
	 * Creates a new JoystickWrapper. Port is the USB port of the physical device,
	 * starting at 0.
	 */
	public JoystickWrapper(int port) {
		super(port);
		x = 0;
		y = 0;
	}

	/**
	 * Updates X and Y axes. This should be done before calling
	 * {@link #findLeftPower()} or {@link #findRightPower()}.
	 * <p>
	 * To avoid redundant calls, calling this method should be periodic logic for
	 * tele-operated driving commands.
	 */
	public void update() {
		// Update X and Y to match the X and Y axes of the device
		x = getX();
		y = getY();
		SmartDashboard.putNumber("x ", getX());
		SmartDashboard.putNumber("y ", getY());

		// Our joystick has unusual behavior so we must do this
		x = -1 * x;
	}

	/**
	 * Calculates the left power. Before using this method, ensure you have recently
	 * used {@link #update()}.
	 */
	public double findLeftPower() {
		double direction = Robot.drive.isReversed ? -1 : 1;
		// A slight modification of the traditional arcade drive calculation
		// Makes X-axis nonlinear, adds sensitivity constant
		double value = (y * direction) + Math.copySign(Math.pow(Math.abs(x), Constants.Drivebase.SCALING_POWER), x)
				* Constants.Drivebase.TURN_SENSITIVITY;

		if (Constants.Drivebase.IS_DRIVEBASE_BACKWARDS) {
			return -1 * value;
		}
		return value;
	}

	/**
	 * Calculates the right power. Before using this method, ensure you have
	 * recently used {@link #updateMagnitude()}.
	 */
	public double findRightPower() {
		double direction = Robot.drive.isReversed ? -1 : 1;
		// A slight modification of the traditional arcade drive calculation
		// Makes X-axis nonlinear, adds sensitivity constant
		double value = (y * direction) - Math.copySign(Math.pow(Math.abs(x), Constants.Drivebase.SCALING_POWER), x)
				* Constants.Drivebase.TURN_SENSITIVITY;

		if (Constants.Drivebase.IS_DRIVEBASE_BACKWARDS) {
			return -1 * value;
		}
		return value;
	}

	/**
	 * Maths which is intended to improve the turning of the robot slightly,
	 * outlined here:
	 * https://www.chiefdelphi.com/t/programming-an-arcade-drive/123985/9
	 */
	public double skim(double v) {
		// Calculate the turn gain based on the Joystick slider (that thing on the
		// bottom)
		double turnGain = 1.0; // Skim setting 1 was chosen as best
		// if (this.getAxisCount() == 4) { // WE DONT USE THIS NOW CAUSE HUBERT LIKED 1
		// turnGain = getThrottle();
		// } else if (this.getAxisCount() == 5) {
		// turnGain = this.getZ();
		// }

		// SmartDashboard logging

		if (v > 1.0) {
			return ((v - 1.0) * turnGain); // slider
		} else if (v < -1.0) {
			return ((v + 1.0) * turnGain);
		}
		return 0;
	}

	/**
	 * Map a value x in the range [a1, b1] to a new value in the range [a2, b2]
	 */
	public static double map(double x, double a1, double b1, double a2, double b2) {
		return (b2 - a2) * (x - a1) / (b1 - a1) + a2;
	}
}
