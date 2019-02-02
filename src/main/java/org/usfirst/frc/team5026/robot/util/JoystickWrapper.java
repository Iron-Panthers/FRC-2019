package org.usfirst.frc.team5026.robot.util;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickWrapper extends Joystick {
	private double x, y, magnitude;

	public JoystickWrapper(int port) {
		super(port);
		x = 0;
		y = 0;
		magnitude = 0;
	}

	double[] constantCurveDrive(double throttle, double wheel, boolean quickTurn) {
		double angularPower;
		System.out.println("Wheel power: "+wheel);
		if (quickTurn) {
			angularPower = wheel;
		} else {
			angularPower = throttle * wheel;
		}
		angularPower = constrain(angularPower * Constants.Drivebase.TURN_SENSITIVITY, -1, 1);
		double left = throttle + angularPower;
		double right = throttle - angularPower;
		left /= Math.abs(left) > 1 ? Math.abs(left) : 1;
		right /= Math.abs(right) > 1 ? Math.abs(right) : 1;
		double[] result = { left, right };
		return result;
	}

	/**
	 * Calculate x, y, magnitude.
	 */
	public void findMagnitude() {
		x = getX();
		y = getY();
		magnitude = Math.abs(Math.sqrt(x * x + y * y));

		double scaledMagnitude = (magnitude - Constants.Input.JOYSTICK_DEADZONE_CIRCLE)
				/ (1 - Constants.Input.JOYSTICK_DEADZONE_CIRCLE);

		if (scaledMagnitude < Constants.Input.JOYSTICK_DEADZONE_CIRCLE) {
			scaledMagnitude = 0;
		}

		x *= scaledMagnitude;
		y *= scaledMagnitude;
		y = getZ();// TEMPORARY FOR THRUSTMASTER
		magnitude *= scaledMagnitude;
	}

	public double findLeftPower() {
		findMagnitude();
		/*
		 * if (Constants.Drivebase.IS_DRIVEBASE_BACKWARDS) { return -(y - x); } return y
		 * + x;
		 */
		return constantCurveDrive(y, x, false)[0];
	}

	public double findRightPower() {
		findMagnitude();
		/*
		 * if (Constants.Drivebase.IS_DRIVEBASE_BACKWARDS) { return -(y + x); } return y
		 * - x;
		 */
		return constantCurveDrive(y, x, false)[1];
	}

	private double constrain(double value, double min, double max){
		return value < min ? min : value > max ? max : value;
	}
}
