package org.usfirst.frc.team5026.robot.util.swerve;

public class SwerveGamepad {

    RotateStick rotate;
    StrafeStick strafe;

    /**
     * Class representing xbox controller. For people who are used to playing xbox video games,
     * strafePort should be for the right stick, and rotatePort for the left stick.
     * @param rotatePort
     * @param strafePort
     */
    public SwerveGamepad(int rotatePort, int strafePort) {
        rotate = new RotateStick(rotatePort);
        strafe = new StrafeStick(strafePort);

    }

    public void update() {
        rotate.update();
        strafe.update();
    }

    public double getSwerveAngle() {
        return strafe.getSwerveAngle();
    }

    public double getForward() {
        return strafe.getForward();
    }

    public double getTurn() {
        return rotate.getTurn();
    }


}