/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.climb;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.CancelClimb;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.HoldElevator;
import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.SparkMaxMotorGroup;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The climb subsystem wraps the solenoids, motor controllers, and limit
 * switches required for the endgame climb action, and provides some utility
 * methods for controlling them.
 */
public class Climb extends Subsystem {
	public SparkMaxMotorGroup climbMotors;
	public TalonSRX trainingWheelMotor;
	public DoubleSolenoid superStructurePistons, trainingWheelPiston;
	public DigitalInput topLimitSwitch, bottomLimitSwitch;

	public Climb() {
		this.climbMotors = Robot.hardware.climbMotors;
		this.trainingWheelMotor = Robot.hardware.trainingWheelMotor;
		this.superStructurePistons = Robot.hardware.superStructurePistons;
		this.topLimitSwitch = Robot.hardware.forwardLimit;
		this.bottomLimitSwitch = Robot.hardware.reverseLimit;
	}

	/**
	 * Moves the robot up by a constant speed, meant for using with a button. This
	 * lowers the superstructure, while lifting the rest of the robot.
	 * <p>
	 * Stops climbing when the limit switch is triggered.
	 */
	public void climbUp() {
		// If the climb limit switch is triggered
		if (this.topLimitSwitch.get()) {
			// Stop climbing, and indicate the climb has stopped
			SmartDashboard.putString("Climb -- isClimbing", "No, limit switch triggered");
			this.stopClimb();
			this.climbMotors.getMasterMotor().setEncPosition(Constants.Climb.TOP_ENCODER_VALUE);
		} else {
			SmartDashboard.putString("Climb -- isClimbing", "Yes, climbing up");
			climbMotors.set(Constants.Climb.CLIMB_UP_SPEED);
		}
	}

	/**
	 * Sets the training wheel motor controller to a specified forward speed,
	 * defined in the Constants class.
	 */
	public void trainingWheelsForward() {
		trainingWheelMotor.set(ControlMode.PercentOutput, Constants.Climb.TRAINING_WHEEL_FORWARD_SPEED);
	}

	public void trainingWheelsForwardWithPower(double power) {
		trainingWheelMotor.set(ControlMode.PercentOutput, Math.abs(power));
	}

	/**
	 * Sets the training wheel motor controller to a specified backward speed,
	 * defined in the Constants class.
	 */
	public void trainingWheelsBackward() {
		trainingWheelMotor.set(ControlMode.PercentOutput, Constants.Climb.TRAINING_WHEEL_BACKWARD_SPEED);
	}

	public void trainingWheelsBackwardWithPower(double power) {
		trainingWheelMotor.set(ControlMode.PercentOutput, -Math.abs(power));
	}

	/**
	 * Stops the training wheel motor controller.
	 */
	public void trainingWheelsStop() {
		trainingWheelMotor.set(ControlMode.PercentOutput, 0);
	}

	/**
	 * Moves the robot down by a constant speed. This lifts the superstructure,
	 * while lowering the rest of the robot.
	 */
	public void climbDown() {
		if (this.bottomLimitSwitch.get()) {
			SmartDashboard.putString("Climb -- isClimbing", "No, limit switch triggered");
			this.stopClimb();
			Robot.climb.climbMotors.getMasterMotor().getEncoder().setPosition(Constants.Climb.BOTTOM_ENCODER_VALUE);
		} else {
			SmartDashboard.putString("Climb -- isClimbing", "Yes, climbing down");
			climbMotors.set(Constants.Climb.CLIMB_DOWN_SPEED);
		}
		SmartDashboard.putNumber("Climb -- Climb motor output", climbMotors.getAppliedOutput());
	}

	/**
	 * Stops the climb motor motor controllers.
	 */
	public void stopClimb() {
		climbMotors.set(0);
		SmartDashboard.putNumber("Climb -- Climb motor output", climbMotors.getAppliedOutput());
	}

	/**
	 * Get the lower number of rotations between the left and right side of the
	 * super structure elevator. It is NOT encoder ticks.
	 * 
	 * @return A double, which is the number of rotations
	 */
	public double getEncoderPosition() {
		return climbMotors.getMasterMotor().getEncoder().getPosition();
	}

	public double getEncoderVelocity() {
		return climbMotors.getMasterMotor().getEncoder().getVelocity();
	}

	/**
	 * Extends the superstructure pistons.
	 */
	public void extendSuperStructurePistons() {
		superStructurePistons.set(DoubleSolenoid.Value.kForward);
	}

	/**
	 * Retracts the superstructure pistons.
	 */
	public void retractSuperStructurePistons() {
		superStructurePistons.set(DoubleSolenoid.Value.kReverse);
	}

	/**
	 * Climbs up with a specified power
	 * 
	 * @param power the power which the climb subsystem will be set to.
	 */
	public void climbUpWithPower(double power) {
		// If the climb limit switch is triggered
		if (this.topLimitSwitch.get()) {
			// Stop climbing, and indicate the climb has stopped
			SmartDashboard.putString("Climb -- isClimbing", "No, limit switch triggered");
			this.stopClimb();
		} else {
			SmartDashboard.putString("Climb -- isClimbing", "Yes, climbing up");
			// Math.abs used to ensure that the robot will climb up
			climbMotors.set(Math.abs(power));
		}
	}

	/**
	 * Climbs down with a specified power
	 * 
	 * @param power The power to set the climb to
	 */
	public void climbDownWithPower(double power) {
		// If the bottom limit is triggered
		if (this.bottomLimitSwitch.get()) {
			// Stop climbing down, show climb has stopped
			SmartDashboard.putString("Climb -- isClimbing", "No, bottom limit switch triggered");
			this.stopClimb();
		} else {
			SmartDashboard.putString("Climb -- isClimbing", "Yes, climbing down");
			// -Math.abs used to ensure that the robot will climb down
			climbMotors.set(-Math.abs(power));
		}
	}

	/**
	 * Stops elevator, and sets default command to CancelClimb to stop it from
	 * moving for after climb is completed.
	 */
	public void cancelClimb() {
		stopClimb();
		setDefaultCommand(new CancelClimb());
	}

	/**
	 * Resets default command for teleopinit to hold the elevator
	 */
	public void resetDefaultCommand(){
		setDefaultCommand(new HoldElevator());
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new HoldElevator());
	}
}
