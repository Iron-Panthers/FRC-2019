package org.usfirst.frc.team5026.robot.util;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.ArcadeDrive;

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
	private double magnitude; // the magnitude of the vector created by the x and y axes

	/**
	 * Construct an instance of a JoystickWrapper, where the joystick index is the
	 * USB port on the driver station.
	 * 
	 * @param port is the USB port on the Driver Station that the joystick is
	 *             plugged into.
	 */
	public JoystickWrapper(int port) {
		super(port);
		x = 0;
		y = 0;
		magnitude = 0;
	}

	/**
	 * Calculate x, y, magnitude and adjust for circle and bowtie deadzones. This
	 * should be done before calling {@link #findLeftPower()} or
	 * {@link #findRightPower()}.
	 * <p>
	 * To avoid redundant calls, calling this method should be periodic logic for
	 * tele-operated driving commands.
	 */
	public void update() {
		// Update X and Y to match the X and Y axes of the device
		x = getX();
		y = getY();

		//Our joystick has unusual behavior so we must do this.
		x = -1 * x;
	/**
	 * Updates the x and y position of the joystick.
	 */
	}
	private void updateAxes() {
		x = getX();
		y = getY();
	}

	/**
	 * Calculates the left power. Before using this method, ensure you have
	 * recently used {@link #update()}.
	 */
	public double findLeftPower() {
		double direction = Robot.drive.isReversed ? -1 : 1;
		// A slight modification of the traditional arcade drive calculation
		// Makes X-axis nonlinear, adds sensitivity constant
		double value = radialDrive((y * direction), Math.copySign(Math.pow(Math.abs(x),1), x))[0];

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
		double value = radialDrive((y * direction), Math.copySign(Math.pow(Math.abs(x),1), x))[1];

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
		double turnGain = getThrottle();

		// SmartDashboard logging
		SmartDashboard.putNumber("Slider value (turn gain): ", turnGain);
		SmartDashboard.putNumber("x ", getX());
		SmartDashboard.putNumber("y ", getY());

		if (v > 1.0) {
			return ((v - 1.0) * turnGain); // slider
		} else if (v < -1.0) {
			return ((v + 1.0) * turnGain);
		}
		return 0;
	}

	/**
	 * The bowtie zone is not a deadzone, it is a zone which assumes the driver to
	 * want to be driving straight, and adjusts the values so that the outputs match
	 * with this expectation.
	 */
	private void applyBowtieZone() {
		if (Math.abs(y) > Math.abs(x) * Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE) {
			x = 0;
		} else {
			if (x > 0) {
				x = (x - (Math.abs(y) / Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE))
						/ (1 - (Math.abs(y) / Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE));
			} else {
				x = (x + (Math.abs(y) / Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE))
						/ (1 - (Math.abs(y) / Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE));
			}
		}
		if (Math.abs(x) > Math.abs(y) * Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE) {
			y = 0;
		} else {
			if (y > 0) {
				y = (y - (Math.abs(x) / Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE))
						/ (1 - (Math.abs(x) / Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE));
			} else {
				y = (y + (Math.abs(x) / Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE))
						/ (1 - (Math.abs(x) / Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE));
			}
		}
	}

	/**
	 * Applies the idea of a "bowtie deadzone", given a vertical slope and
	 * horizontal slope for the triangles. Mutates the x and y values within the
	 * JoystickWrapper.
	 * 
	 * @param verticalSlope
	 * @param horizontalSlope
	 */
	private void applyBowtieDeadzone(double verticalSlope, double horizontalSlope) {
		if (Math.abs(y) > Math.abs(x) * verticalSlope) {
			x = 0;
		} else {
			x = (x - (Math.abs(y) / verticalSlope)) / (1 - (Math.abs(y) / verticalSlope));
		}

		if (Math.abs(x) > Math.abs(y) * horizontalSlope) {
			y = 0;
		} else {
			y = (y - (Math.abs(x) / horizontalSlope)) / (1 - (Math.abs(x) / horizontalSlope));
		}
	}

	/**
	 * Calculates right and left powers based on the input y-value, and a radius
	 * calculated based on the input x-value.
	 * 
	 * @param tx
	 * @param ty
	 * @return double[] powers
	 */
	public double[] radialDrive(double ty, double tx) {
		double turnPower = tx;
		double straightPower = ty;
		double rightPow = 0;
		double leftPow = 0;

		// turnRadius = Constants.Input.MAX_DESIRED_TURN_RADIUS * (1 -
		// Math.abs(turnPower));
		double turnRadius = (-Constants.Drivebase.RADIAL_TURN_SENSITIVITY * Math.log(Math.abs(turnPower / 2))) - 6;
		double innerPower = straightPower * (turnRadius - Constants.Drivebase.DRIVEBASE_WIDTH / 2)
				/ (turnRadius + Constants.Drivebase.DRIVEBASE_WIDTH / 2);

		if (turnPower > 0) {
			rightPow = innerPower;
			leftPow = straightPower;
		} else if (turnPower < 0) {
			rightPow = straightPower;
			leftPow = innerPower;
		}

		/*
		 * The segment commented out applies the "forward-driving" zone, which allows
		 * the driver to set equal power even if they are slightly off-center.
		 * 
		 * if (Math.abs(turnPower) < Constants.Input.JOYSTICK_DEADBAND) { rightPow =
		 * straightPower; leftPow = straightPower; }
		 */

		double[] result = { leftPow, rightPow };
		return result;
	}

	/**
     * Map a value x in the range [a1, b1] to a new value in the range [a2, b2]
     */
    public static double map(double x, double a1, double b1, double a2, double b2) {
        return (b2 - a2) * (x - a1) / (b1 - a1) + a2;
    }
}