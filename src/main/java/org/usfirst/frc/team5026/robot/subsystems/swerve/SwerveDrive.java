/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.swerve;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.subsystems.swerve.commands.SwerveArcadeDrive;
import org.usfirst.frc.team5026.robot.subsystems.swerve.hardware.SwerveModule;
import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.SuperiorGyro;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The swerve drive subsystem. This contains a SwerveMotorGroup and 4 drive motors. To see a big explanation of how all this stuff works, go to 
 * https://docs.google.com/document/d/11wd6t2Wp50TKZTyo7Uo5H39PWN1EThgJpfGrHoVanvI/view
 */
public class SwerveDrive extends Subsystem {

	SwerveModule frontRight;
	SwerveModule frontLeft;
	SwerveModule backRight;
	SwerveModule backLeft;
	SwerveModule[] modules;

	SuperiorGyro gyro;

	/**
	 * Create the Swerve drive subsystem.
	 */
	public SwerveDrive() {

		gyro = Robot.hardware.gyro;

		frontRight = Robot.hardware.frontRightModule;
		frontLeft = Robot.hardware.frontLeftModule;
		backRight = Robot.hardware.backRightModule;
		backLeft = Robot.hardware.backLeftModule;

		modules = new SwerveModule[] {frontRight, frontLeft, backRight, backLeft};

	}

	/**
	 * Set the drivebase to lock its swerve motors at a constant angle, 
	 * to go forward a certain amount, and to turn a certain amount.
	 * A positive turn value will result in turning right.
	 * 
	 * @param absoluteSwerveAngle should be between -180 and 180
	 * @param forward the amount to drive in the direction of absoluteSwerveAngle
	 * @param turn the amount to turn the chassis
	 * 
	 */
	public void set(double absoluteSwerveAngle, double forward, double turn) {

		//calculate desired swerve angle relative to the robot, from gyro heading and desired absolute
		//swerve angle (which is relative to the field)
		double relativeSwerveAngle = SwerveMath.getAngleDifference(gyro.getBoundedYaw(), absoluteSwerveAngle);
		
		//Find the weight "inner" wheels should give to turning.
		//When tuned correctly, should reduce wheel slip due to running motors 
		//against each other, while still turning at high speed.
		double innerTurnModifier = SwerveMath.getInnerTurnModifier(relativeSwerveAngle);

		//calculate powers for each drive motor. 
		double innerRightPower = SwerveMath.boundBelowOne(forward - innerTurnModifier * turn);
		double innerLeftPower = SwerveMath.boundBelowOne(forward + innerTurnModifier * turn);
		double outerRightPower = SwerveMath.boundBelowOne(forward - turn);
		double outerLeftPower = SwerveMath.boundBelowOne(forward + turn);

		double[] powersList = new double[] {innerRightPower, innerLeftPower, outerRightPower, outerLeftPower};

		//assign each module to a specific spot in the list based on its position
		//(inner or outer right or left) relative to swerveAngle
		SwerveModule[] reorderedModuleList = SwerveMath.organizePositions(modules, relativeSwerveAngle);

		//set modules
		for(int i = 0; i < 4; i++) {
			
			//the swerve motor for each module will PID to the desired swerveAngle, as well as rotating at velocity proportional to
			// -turn. The latter is intended to anticipate the change in angle of the robot. The drive motor is of course set to the
			//power determined above
			reorderedModuleList[i].set(relativeSwerveAngle, -turn * Constants.SwerveDrive.TURN_TO_F_RATIO, powersList[i]);
		}

	}

	/**
	 * tells all the modules whether autononous driving is currrently happening.
     * If this is the case, they will switch from dynamically figuring out
     * which swerve orientation is most efficient (in teleop), to keeping a constant swerve orientation
     * for the entirety of a segment (in auto).
	 * @param autoModeOn
	 */
	public void setAutoMode(boolean autoModeOn) {
		for(SwerveModule module : modules) {
			module.setAutoMode(autoModeOn);
		}
	}

	/**
	 * Stop driving.
	 */
	public void stop() {
		for(SwerveModule module : modules) {
			module.stop();
		}
	}

	/**
	 * stop driving the drive motors, but leave the swerve motors facing 0 degrees (forward)
	 */
	public void idle() {
		for(SwerveModule module : modules) {
			module.idle();
		}
	}

	@Override
	public void initDefaultCommand() {
		// Pick one of the drive mode commands.
		setDefaultCommand(new SwerveArcadeDrive());
	}

	/**
	 * Sets up all the modules for the next chunk. Specify the encoder distance to travel, the deadzone tolerance for chunk completion, and the swerve angle at which to travel
	 */
	public void startNewAutoChunk(double encDelta, double forwardDeadzoneSize, double swerveAngle) {
		for(SwerveModule module : modules) {
			module.startNewSegment(encDelta, forwardDeadzoneSize, swerveAngle);
		}
	}

	/**
	 * gets the average distance travelled by each drive motor since the last 
	 * @return
	 */
	public double getAvgEncDelta() {
		double deltaSum = 0;
		for(SwerveModule module : modules) {
			deltaSum += module.getForwardDelta();
		}
		return deltaSum / 4;

	}

	/**
	 * check whether every module has reached its target
	 * @return
	 */
	public boolean allTargetsReached() {

		return frontRight.targetReached() && frontLeft.targetReached() && backRight.targetReached() && backLeft.targetReached();

	}

	/**
	 * reset the status of the auto targets for all of the modules. Call this instead of startNewAutoChunk if your
	 * path is over or for some reason you want to reset the targets (to the "not reached" state) without starting 
	 * a new chunk. No need to call them in conjunction, as startNewAutoChunk already resets all the targets
	 */
	public void resetTargetsReached() {
		for(SwerveModule module : modules) {
			module.targetReached = false;
		}
	}

}