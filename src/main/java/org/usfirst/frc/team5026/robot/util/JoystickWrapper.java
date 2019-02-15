package org.usfirst.frc.team5026.robot.util;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.subsystems.drive.commands.ArcadeDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * JoystickWrapper is a wrapper which extends the WPILib Joystick, which itself
 * extends GenericHID. Most of the additions to Joystick exist for tele-operated
 * driving logic.
 */
public class JoystickWrapper extends Joystick {
	private double x, y, magnitude;

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
	 * Updates the x and y position of the joystick.
	 */
	private void updateAxes() {
		x = getX();
		y = getY();
	}

	/**
	 * Calculates right and left powers based on the throttle, wheel, and quickTurn
	 * values.
	 * 
	 * @param throttle
	 * @param wheel
	 * @param quickTurn whether or not to use quick-turn mode
	 * 
	 * @return double[] powers
	 */
	public double[] constantCurveDrive(double throttle, double wheel, boolean quickTurn) {
		double angularPower;
		//System.out.println("Wheel power: " + wheel);
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
	 * Calculate x, y, magnitude and adjust for circle and bowtie deadzones.
	 **/
	public void findMagnitude() {
		x = getX();
		y = getY();

		if (Math.abs(y) > Math.abs(x) * Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE) {
			x = 0;
		} else {
			if(x>0) {
				x = (x - (Math.abs(y) / Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE))
					/ (1 - (Math.abs(y) / Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE));
			}
			else {
				x = (x + (Math.abs(y) / Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE))
					/ (1 - (Math.abs(y) / Constants.Input.VERTICAL_BOWTIE_DEADZONE_SLOPE));
			}
		}
		if (Math.abs(x) > Math.abs(y) * Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE) {
			y = 0;
		} else {
			if(y>0) {
				y = (y - (Math.abs(x) / Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE))
					/ (1 - (Math.abs(x) / Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE));
			}
			else {
				y = (y + (Math.abs(x) / Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE))
					/ (1 - (Math.abs(x) / Constants.Input.HORIZONTAL_BOWTIE_DEADZONE_SLOPE));
			}
		}

		double scaledMaxMagnitude = (magnitude/Math.abs(x) < Math.sqrt(2)) ? magnitude/Math.abs(x) : magnitude/Math.abs(y);
		double scaledMagnitude = scaledMaxMagnitude * ((magnitude - Constants.Input.JOYSTICK_DEADZONE_CIRCLE)
		/ (scaledMaxMagnitude - Constants.Input.JOYSTICK_DEADZONE_CIRCLE));

		if (scaledMagnitude < 0) {
			scaledMagnitude = 0;
		}
	}

	/**
	 * Calculates the desired left power. Results are calculated using modified
	 * ArcadeDrive maths. Before calling this method, ensure you have recently called
	 * {@link #updateMagnitude()}.
	 * 
	 * The result is calculated using the values of the TURN_SENSITIVITY constant
	 * and the IS_DRIVEBASE_BACKWARDS constant. Refer to Constants.java in this
	 * package.
	 */
	public double findLeftPower() {
		/*
		 * if (Robot.drive.isReversed) { return -(y - x); } return y + x;
		 */
		// return radialDrive(y, x)[0];
		double direction = Robot.drive.isReversed ? -1 : 1;
		if (Constants.Drivebase.IS_DRIVEBASE_BACKWARDS) {
			return -1 * (y * direction + x * Math.abs(x) * Constants.Drivebase.TURN_SENSITIVITY);
		}
		return y * direction + x * Math.abs(x) * Constants.Drivebase.TURN_SENSITIVITY;
	}

	/**
	 * Calculates the desired right power. Results are calculated using modified
	 * ArcadeDrive maths. Before calling this method, ensure you have recently called
	 * {@link #updateMagnitude()}.
	 * 
	 * The result is calculated using the values of the TURN_SENSITIVITY constant
	 * and the IS_DRIVEBASE_BACKWARDS constant. Refer to Constants.java in this
	 * package.
	 */
	public double findRightPower() {
		/*
		 * if (Robot.drive.isReversed) { return -(y + x); } return y - x;
		 */

		// return radialDrive(y, x)[1];

		double direction = Robot.drive.isReversed ? -1 : 1;
		if (Constants.Drivebase.IS_DRIVEBASE_BACKWARDS) {
			return -1 * (y * direction - x * Math.abs(x) * Constants.Drivebase.TURN_SENSITIVITY);
		}
		return y * direction - x * Math.abs(x) * Constants.Drivebase.TURN_SENSITIVITY;
	}

	private double constrain(double value, double min, double max) {
		return value < min ? min : value > max ? max : value;
	}
	public double skim(double v) {
		SmartDashboard.putNumber("slider: ", getAxis(Joystick.AxisType.kThrottle));
		SmartDashboard.putNumber("x ", getX());
		SmartDashboard.putNumber("y ", getY());

		if (v > 1.0) {
			return -((v - 1.0) * getAxis(Joystick.AxisType.kThrottle)); //slider
		} else if (v < -1.0) {
			return -((v + 1.0) * getAxis(Joystick.AxisType.kThrottle));
		} return 0; 
	}
}
