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

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public TalonSRX intakeMotor;
	public Solenoid hatchPiston, hatchExtenderPistons;

	public Intake() {
		intakeMotor = Robot.hardware.armIntakeMotor;
		hatchPiston = Robot.hardware.hatchPiston;
		hatchExtenderPistons = Robot.hardware.hatchExtenderPistons;
	}

	public void setIntakePower(double power) {
		intakeMotor.set(ControlMode.PercentOutput, power);
	}

	public void brakeIntake() {
		intakeMotor.set(ControlMode.PercentOutput, 0);
	}

	/**
	 * Extends hatch piston to hold onto the hatch
	 */
	public void hatchIntake() { // TODO: Test
		hatchPiston.set(false);

		// Only functional with DoubleSolenoid:
		// hatchPiston.set(DoubleSolenoid.Value.kForward);
	}

	/**
	 * Retracts hatch piston to let go of hatch/prepare to grab one
	 */
	public void hatchOuttake() { // TODO: Test
		hatchPiston.set(true);

		// Only functional with DoubleSolenoid:
		// hatchPiston.set(DoubleSolenoid.Value.kReverse);
	}

	public void extendHatchPistons() {
		hatchExtenderPistons.set(true);
	}

	public void retractHatchPistons() {
		hatchExtenderPistons.set(false);
	}

	public double getCurrent() {
		return intakeMotor.getOutputCurrent();
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
