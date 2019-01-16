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
        magnitude *= scaledMagnitude;
    }

    public double findLeftPower() {
        findMagnitude();
        return y + x;
    }

    public double findRightPower() {
        findMagnitude();
        return y - x;
    }
}
