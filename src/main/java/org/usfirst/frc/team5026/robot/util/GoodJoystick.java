package org.usfirst.frc.team5026.robot.util;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5026.robot.util.Vector;

public class GoodJoystick extends Joystick {
	public JoystickButton driveStickTrigger; 
	public JoystickButton driveStickTopButton;
	public GoodJoystick(int port){
		super(port);
		driveStickTrigger = new JoystickButton(this, 1);
		driveStickTopButton = new JoystickButton(this, 5);
	}
	public void seeAxis() {
		SmartDashboard.putNumber("Raw X", getX());
		SmartDashboard.putNumber("Raw Y", getY());
	}
	//Robot.drive.useArcadeDrive(getX()*Constants.X_AXIS_MODIFIER, getY());

	public Vector findXY() {
		// Reverses drive when triggered
		Vector v = driveStickTrigger.get() ? new Vector(getX(), getY()) : new Vector(getX(), -getY());
		double magnitude = v.getMagnitude();
		double scaledMagnitude = (magnitude-Constants.Drivebase.CIRCLE_DEADZONE)/(1-Constants.Drivebase.CIRCLE_DEADZONE);
		v.norm();
		v.mult(scaledMagnitude);
		
		//TODO
		v.mult(1.05);
					
		//if(Math.abs(y) < Constants.YDEADZONE_SIZE*Math.abs(getX()) || magnitude < Constants.CIRCLE_DEADZONE) {
		if ( magnitude < Constants.Drivebase.CIRCLE_DEADZONE ) {
			v.zero();
		}
		
		SmartDashboard.putNumber("deadzone corrected X", v.getX());
		SmartDashboard.putNumber("deadzone corrected Y", v.getY());
		return v;
	}

	//k = getY()/getX();
	public double findRightPower(double x,double y) {
		double rightPower = y-x;
		if (driveStickTopButton.get()) {
			return rightPower*Constants.Drivebase.DRIVE_SLOW_SCALAR;
		}
			return rightPower;
	}
	public double findLeftPower(double x,double y) {
		double leftPower = y+x;
		if (driveStickTopButton.get()) {
			return leftPower*Constants.Drivebase.DRIVE_SLOW_SCALAR;
		}
	        return leftPower;
	}
	//Robot.drive.setLeftMotor(getY() + getX());
	//Robot.drive.setRightMotor(getY() - getX());
}


