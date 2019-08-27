/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.tShirtCannon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.subsystems.tShirtCannon.commands.HoldTurretPosition;
import org.usfirst.frc.team5026.robot.subsystems.tShirtCannon.commands.SetTurretWithJoystick;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Turret extends Subsystem {
	private TalonSRX turretMotor;
	private PigeonIMU gyro;
	private double[] ypr;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Turret(){
		turretMotor = Robot.hardware.turretMotor;
		gyro = Robot.hardware.gyro;
		ypr = new double[3];
		getYaw();
	}

	/**
	 * Returns the yaw of the gyro
	 * @return Yaw of the gyro
	 */
	public double getYaw(){
		gyro.getYawPitchRoll(ypr);
		return ypr[0]; // Return yaw
	}

	/**
	 * Set the power of the turret
	 * @param power Power of the motor, from -1 to 1
	 */
	public void setTurret(double power){
		turretMotor.set(ControlMode.PercentOutput, power);
	}

	/**
	 * Stops the turretMotor
	 */
	public void reset(){
		turretMotor.set(ControlMode.PercentOutput, 0);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new SetTurretWithJoystick());
	}
}
