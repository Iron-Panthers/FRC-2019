package org.usfirst.frc.team5026.robot.util.swerve;
import edu.wpi.first.wpilibj.Joystick;

public class StrafeStick extends Joystick {

    double x;
    double y;

    public StrafeStick(int port) {
        super(port);
    }

    public double getSwerveAngle() {
        return SwerveMath.getAngleFromPoint(x,y);
    }

    public void update() {
        x = getX();
        y = getY();
    }

    public double getForward() {
        return Math.abs(Math.sqrt(x * x + y * y));

        //double magnitude = Math.abs(Math.sqrt(x * x + y * y));
        // // Calculate the maximum possible magnitude of the joystick at its current angle.
        // // This requires determining which edge to "reach to" to get the maximum magnitude.
        // double maxMagnitude = magnitude / Math.abs(x);
		// if (maxMagnitude > Math.sqrt(2)) {
		// 	maxMagnitude = magnitude / Math.abs(y);
        // }
        //
        // return magnitude / maxMagnitude;

        //ABOVE CURRENTLY NOT IN USE because im not sure what range of values is available for xbox joystick
    }

}