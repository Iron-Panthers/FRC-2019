package org.usfirst.frc.team5026.robot.subsystems.swerve.input;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotateStick extends Joystick {

    double x;
    double y;

    public RotateStick(int port) {
        super(port);
    }

    public void update() {
        x = getX();
        y = getY();
        SmartDashboard.putNumber("rotate stick x", x);
        SmartDashboard.putNumber("rotate stick y", y);
    }
    
    public double getTurn() {
        return -x;
    }

}