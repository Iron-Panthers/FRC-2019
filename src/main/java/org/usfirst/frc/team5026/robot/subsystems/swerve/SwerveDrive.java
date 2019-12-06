/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.subsystems.swerve.commands.SwerveArcadeDrive;
import org.usfirst.frc.team5026.robot.subsystems.swerve.hardware.SwerveModule;
import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.SuperiorGyro;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

		zeroEncoders();
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
		//double relativeSwerveAngle = SwerveMath.getAngleDifference(gyro.getBoundedYaw(), absoluteSwerveAngle);
		double relativeSwerveAngle = absoluteSwerveAngle; //TODO eventually should be adjusted to robot angle as above

		//converts out of heading and to radians in order to find vector components with atan()
		double relativeSwerveAngleUnitCircleRelative = SwerveMath.headingToPositiveX(relativeSwerveAngle); 
		double relativeSwerveAngleRadians = Math.toRadians(relativeSwerveAngleUnitCircleRelative);

		double strafeX = Math.cos(relativeSwerveAngleRadians) * forward; 
		double strafeY = Math.sin(relativeSwerveAngleRadians) * forward;

		double turnVectorAngleRadians = Math.PI/4 - Math.atan(Constants.DrivebaseProperties.ASPECT_RATIO);

		double frontRightX = strafeX + turn * Math.cos(turnVectorAngleRadians);
		double frontRightY = strafeY - turn * Math.sin(turnVectorAngleRadians);
		double frontLeftX = strafeX + turn * Math.cos(turnVectorAngleRadians);
		double frontLeftY = strafeY + turn * Math.sin(turnVectorAngleRadians);
		double backRightX = strafeX - turn * Math.cos(turnVectorAngleRadians);
		double backRightY = strafeY - turn * Math.sin(turnVectorAngleRadians);
		double backLeftX = strafeX - turn * Math.cos(turnVectorAngleRadians);
		double backLeftY = strafeY + turn * Math.sin(turnVectorAngleRadians);

		double frontRightAngle = SwerveMath.getHeadingFromPoint(frontRightX, frontRightY);
		double frontRightPower = SwerveMath.boundBelowOne(Math.sqrt(frontRightY * frontRightY + frontRightX * frontRightX));

		double frontLeftAngle = SwerveMath.getHeadingFromPoint(frontLeftX, frontLeftY);
		double frontLeftPower = SwerveMath.boundBelowOne(Math.sqrt(frontLeftY * frontLeftY + frontLeftX * frontLeftX));

		double backRightAngle = SwerveMath.getHeadingFromPoint(backRightX, backRightY);
		double backRightPower = SwerveMath.boundBelowOne(Math.sqrt(backRightY * backRightY + backRightX * backRightX));

		double backLeftAngle = SwerveMath.getHeadingFromPoint(backLeftX, backLeftY);
		double backLeftPower = SwerveMath.boundBelowOne(Math.sqrt(backLeftY * backLeftY + backLeftX * backLeftX));

		//for now, no anticipated turn is being fed to the modules. I don't think its necessary for standard swerve control but its available if we need it
		frontRight.set(frontRightAngle, 0, frontRightPower);
		frontLeft.set(frontLeftAngle, 0, frontLeftPower);
		backRight.set(backRightAngle, 0, backRightPower);
		backLeft.set(backLeftAngle, 0, backLeftPower);

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

	public void putData() {

		SmartDashboard.putNumber("frs encoder value", modules[0].swerve.getSelectedSensorPosition());
		SmartDashboard.putNumber("fls encoder value", modules[1].swerve.getSelectedSensorPosition());
		SmartDashboard.putNumber("brs encoder value", modules[2].swerve.getSelectedSensorPosition());
		SmartDashboard.putNumber("bls encoder value", modules[3].swerve.getSelectedSensorPosition());

		SmartDashboard.putNumber("frs angle", modules[0].swerve.getAngle());
		SmartDashboard.putNumber("fls angle", modules[1].swerve.getAngle());
		SmartDashboard.putNumber("brs angle", modules[2].swerve.getAngle());
		SmartDashboard.putNumber("bls angle", modules[3].swerve.getAngle());

		SmartDashboard.putNumber("frs error", modules[0].swerve.getError());
		SmartDashboard.putNumber("fls error", modules[1].swerve.getError());
		SmartDashboard.putNumber("brs error", modules[2].swerve.getError());
		SmartDashboard.putNumber("bls error", modules[3].swerve.getError());

		SmartDashboard.putNumber("frs delta error", modules[0].swerve.getDeltaError());
		SmartDashboard.putNumber("fls delta error", modules[1].swerve.getDeltaError());
		SmartDashboard.putNumber("brs delta error", modules[2].swerve.getDeltaError());
		SmartDashboard.putNumber("bls delta error", modules[3].swerve.getDeltaError());

		SmartDashboard.putNumber("frs output power", modules[0].swerve.getMotorOutputPercent());
		SmartDashboard.putNumber("fls output power", modules[1].swerve.getMotorOutputPercent());
		SmartDashboard.putNumber("brs output power", modules[2].swerve.getMotorOutputPercent());
		SmartDashboard.putNumber("bls output power", modules[3].swerve.getMotorOutputPercent());
		
		SmartDashboard.putNumber("frs output power", modules[0].drive.getMotorOutputPercent());
		SmartDashboard.putNumber("fls output power", modules[1].drive.getMotorOutputPercent());
		SmartDashboard.putNumber("brs output power", modules[2].drive.getMotorOutputPercent());
		SmartDashboard.putNumber("bls output power", modules[3].drive.getMotorOutputPercent());
	}

	public void zeroSwerveEncoders() {
		for(SwerveModule module : modules) {
			module.swerve.setSelectedSensorPosition(0);
		}
	}

	public void zeroDriveEncoders() {
		for(SwerveModule module : modules) {
			module.drive.setSelectedSensorPosition(0);
		}
	}

	public void zeroEncoders() {
		zeroSwerveEncoders();
		zeroDriveEncoders();
	}

}