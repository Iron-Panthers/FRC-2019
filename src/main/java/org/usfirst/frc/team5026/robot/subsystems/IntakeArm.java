/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class IntakeArm extends Subsystem {
  public TalonSRX armMotor;
  public double currentHeight;
  public double currentAngle;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public IntakeArm(){
    armMotor = Robot.hardware.armMotor;
  }

  public void resetEncoder(){
    armMotor.setSelectedSensorPosition(0);
  }

  public double getCurrentAngle(){
    currentAngle = Constants.IntakeArm.TICKS_TO_DEGREES * armMotor.getSelectedSensorPosition();
    return currentAngle;
  }

  public void moveArm(double power) {
    armMotor.set(ControlMode.PercentOutput, power);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand())
  }
}