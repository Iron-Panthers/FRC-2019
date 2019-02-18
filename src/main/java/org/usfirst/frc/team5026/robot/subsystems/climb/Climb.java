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
import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.SparkMaxMotorGroup;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Climb extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public SparkMaxMotorGroup leftClimbMotors;
	public SparkMaxMotorGroup rightClimbMotors;

	public TalonSRX trainingWheelMotor;

	public DoubleSolenoid superStructurePistons;
	public DoubleSolenoid trainingWheelPiston;

	public Climb() {
		this.leftClimbMotors = Robot.hardware.leftClimbMotors;
		this.rightClimbMotors = Robot.hardware.rightClimbMotors;

		this.trainingWheelMotor = Robot.hardware.trainingWheelMotor;

		this.superStructurePistons = Robot.hardware.superStructurePistons;
		this.trainingWheelPiston = Robot.hardware.trainingWheelPiston;
	}

	/**
	 * Moves the robot up by a constant speed, meant for using with a button. This
	 * means that it LOWERS the super structure, LIFTS the robot.
	 * 
	 */
	public void climbUp() {
		leftClimbMotors.set(Constants.Climb.CLIMB_UP_SPEED);
		rightClimbMotors.set(Constants.Climb.CLIMB_UP_SPEED);
	}

	public void trainingWheelsForward() {
		trainingWheelMotor.set(ControlMode.PercentOutput, Constants.Climb.TRAINING_WHEEL_FORWARD_SPEED);
	}

	public void trainingWheelsBackward() {
		trainingWheelMotor.set(ControlMode.PercentOutput, Constants.Climb.TRAINING_WHEEL_BACKWARD_SPEED);
	}

	public void trainingWheelsStop() {
		trainingWheelMotor.set(ControlMode.PercentOutput, 0);
	}

	/**
	 * Moves the robot down by a constant speed. This means that it LIFTS the super
	 * structure, and LOWERS the robot.
	 */
	public void climbDown() {
		leftClimbMotors.set(Constants.Climb.CLIMB_DOWN_SPEED);
		rightClimbMotors.set(Constants.Climb.CLIMB_DOWN_SPEED);
	}

	public void stopClimb() {
		leftClimbMotors.set(0);
		rightClimbMotors.set(0);
	}

	/**
	 * Gets the lower number of rotations between the left and right side of the
	 * super structure elevator. It is NOT encoder ticks.
	 * 
	 * @return A double, which is the number of rotations
	 */
	public double getEncoderPosition() {
		return Math.min(leftClimbMotors.getMasterMotor().getEncoder().getPosition(),
				rightClimbMotors.getMasterMotor().getEncoder().getPosition());
	}

	public void extendSuperStructurePistons() { // TODO: Test if forward extends
		superStructurePistons.set(DoubleSolenoid.Value.kForward);
	}

	public void retractSuperStructurePistons() {
		superStructurePistons.set(DoubleSolenoid.Value.kReverse);
	}

	public void extendTrainingWheels() { // TODO: Test if forward extends
		trainingWheelPiston.set(DoubleSolenoid.Value.kForward);
	}

	public void retractTrainingWheels() {
		trainingWheelPiston.set(DoubleSolenoid.Value.kReverse);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}