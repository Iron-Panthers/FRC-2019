package org.usfirst.frc.team5026.robot.subsystems.swerve.input;
import edu.wpi.first.wpilibj.Joystick;

public class RotateStick extends Joystick {

    double x;
    double y;

    public RotateStick(int port) {
        super(port);
    }

    public void update() {
        x = getX();
        y = getY();
    }
    
    public double getTurn() {
        return -x;
    }

}