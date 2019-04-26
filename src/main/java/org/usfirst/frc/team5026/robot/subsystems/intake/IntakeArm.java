/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.subsystems.intake.commands.ArmHoldTarget;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem which controls the Intake Arm. Contains utility methods to
 * configure the encoder, calculate the angle, height, and basePower.
 */
public class IntakeArm extends Subsystem {
	public TalonSRX armMotor;
	public double currentHeight;
	public double currentAngle;
	public double currentTorque;
	public double basePower;

	public double target;

	public IntakeArm() {
		armMotor = Robot.hardware.armMotor;
	}

	public void resetEncoder() {
		armMotor.setSelectedSensorPosition(0);
	}

	public double getCurrentAngle() {
		currentAngle = (Constants.IntakeArm.TICKS_TO_DEGREES * (double) armMotor.getSelectedSensorPosition()) + Constants.IntakeArm.BASE_ANGLE_OFFSET;
		return currentAngle;
	}

	public double getCurrentHeight() {
		currentHeight = (Math.sin((getCurrentAngle()) * Constants.IntakeArm.DEGRESS_TO_RADIANS)
				* Constants.IntakeArm.ARM_LENGTH) + Constants.IntakeArm.ARM_BASE_HEIGHT;
		return currentHeight;
	}

	public double getBasePower() {
		basePower = Constants.IntakeArm.STALL_TORQUE_COEFFICIENT
				* Math.cos(getCurrentAngle() * Constants.IntakeArm.DEGRESS_TO_RADIANS);
		return basePower;
	}

	public void moveArm(double power) {
		armMotor.set(ControlMode.PercentOutput, power);
	}

	public void autoRetractHatch() {
		if (currentAngle > 30) {
			Robot.intake.hatchIntake();
		}
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new ArmHoldTarget());
	}
}