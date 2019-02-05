package org.usfirst.frc.team5026.robot.util;

import org.usfirst.frc.team5026.robot.Robot;

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
		System.out.println("Wheel power: " + wheel);
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
	 * calculates right and left powers based on the input y value and a radius
	 * calculated based on the input x value
	 * 
	 * @param tx
	 * @param ty
	 * @return double[] powers
	 */
	double[] radialDrive(double ty, double tx) {
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

		// if (Math.abs(turnPower) < Constants.Input.JOYSTICK_DEADBAND) {
		// rightPow = straightPower;
		// leftPow = straightPower;
		// }

		double[] result = { leftPow, rightPow };
		return result;
	}

	/**
	 * Calculate x, y, magnitude and adjust for circle and bowtie deadzones.
	 **/
	public void findMagnitude() {
		x = getX();
		y = getY();

		if (Math.abs(y) > Math.abs(x) * Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE) {
			x = 0;
		} else {
			x = (x - (Math.abs(y) / Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE))
					/ (1 - (Math.abs(y) / Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE));
		}
		if (Math.abs(x) > Math.abs(y) * Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE) {
			y = 0;
		} else {
			y = (y - (Math.abs(x) / Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE))
					/ (1 - (Math.abs(x) / Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE));
		}
		magnitude = Math.abs(Math.sqrt(x * x + y * y));

		double scaledMagnitude = (magnitude - Constants.Input.JOYSTICK_DEADZONE_CIRCLE)
				/ (1 - Constants.Input.JOYSTICK_DEADZONE_CIRCLE);

		if (scaledMagnitude < Constants.Input.JOYSTICK_DEADZONE_CIRCLE) {
			scaledMagnitude = 0;
		}

		x *= (scaledMagnitude / magnitude);
		y *= (scaledMagnitude / magnitude);
		//y = getZ();// TEMPORARY FOR THRUSTMASTER
	}

	public double findLeftPower() {
		findMagnitude();
		/*
		 * if (Constants.Drivebase.IS_DRIVEBASE_BACKWARDS) { return -(y - x); } return y
		 * + x;
		 */
		return radialDrive(y, x)[0];
	}

	public double findRightPower() {
		findMagnitude();
		/*
		 * if (Constants.Drivebase.IS_DRIVEBASE_BACKWARDS) { return -(y + x); } return y
		 * - x;
		 */
		return radialDrive(y, x)[1];
	}

	private double constrain(double value, double min, double max){
		return value < min ? min : value > max ? max : value;
	}
}
