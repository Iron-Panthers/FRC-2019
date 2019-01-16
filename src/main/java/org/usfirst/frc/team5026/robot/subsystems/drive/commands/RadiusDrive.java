package org.usfirst.frc.team5026.robot.subsystems.drive.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.JoystickWrapper;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RadiusDrive extends Command {

	private double turnPower, straightPower, innerPower, turnRadius;
	private JoystickWrapper stick = Robot.oi.stick1;

	public RadiusDrive() {
		requires(Robot.drive);
	}

	protected void initialize() {
		SmartDashboard.putString("Drive mode", "Radius drive");
	}

	protected void execute() {
		turnPower = stick.getX();
		straightPower = stick.getY();
		if (Math.abs(turnPower) < Constants.Input.JOYSTICK_DEADBAND) {
			Robot.drive.set(straightPower, straightPower);
		}
		turnRadius = Constants.Input.MAX_DESIRED_TURN_RADIUS * (1 - Math.abs(turnPower));
		innerPower = straightPower * (turnRadius - Constants.Drivebase.DRIVEBASE_WIDTH / 2)
				/ (turnRadius + Constants.Drivebase.DRIVEBASE_WIDTH / 2);
		if (turnPower > 0) {
			Robot.drive.set(straightPower, innerPower);
		} else if (turnPower < 0) {
			Robot.drive.set(innerPower, straightPower);
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}