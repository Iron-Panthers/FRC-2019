package org.usfirst.frc.team5026.robot.subsystems.swerve.input;

import org.usfirst.frc.team5026.robot.subsystems.swerve.SwerveMath;

import edu.wpi.first.wpilibj.XboxController;

public class SwerveGamepad extends XboxController {

    double rotateX;
    //double rotateY;
    double strafeX;
    double strafeY;
    final boolean invertSides;

    /**
     * Xbox controller for swerve. invertSides switches what the left and 
     * right sides of the controller do.
     *@param port
     *@param invertSides 
     */
    public SwerveGamepad(int port, boolean invertSides) {
        super(port);

        this.invertSides = invertSides;
    }

    /**
     * Updates joystick values. Call this before getting any joystick-related values to
     * ensure they are accurate.
     */
    public void update() {

        //currently the rotate stick Y isn't used for anything but its here in comment 
        //in case we want it later
        rotateX = getX(Hand.kRight);
        //rotateY = getY(Hand.kRight);
        strafeX = getX(Hand.kLeft);
        strafeY = getY(Hand.kLeft);

        if(invertSides) {
            rotateX = getX(Hand.kLeft);
            //rotateY = getY(Hand.kLeft);
            strafeX = getX(Hand.kRight);
            strafeY = getY(Hand.kRight);
        }
    }

    public double getSwerveAngle() {
        return SwerveMath.getHeadingFromPoint(strafeX, strafeY);
    }

    public double getTurn() {
        return rotateX;
    }

    public double getForward() {
        return Math.abs(Math.sqrt(strafeX * strafeX + strafeY * strafeY));

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