/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class ManualArmMovement extends Command {

  private double armTorque;
  private double basePower;
  private double power;

  public ManualArmMovement() {
    requires(Robot.intakeArm);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    armTorque = ((Constants.IntakeArm.INTAKE_MASS * Constants.IntakeArm.INTAKE_DISTANCE)
              + (Constants.IntakeArm.INTAKE_ARM_MASS * Constants.IntakeArm.INTAKE_ARM_DISTANCE))
              * Constants.IntakeArm.GRAVITY_ACCELERATION * Math.asin(Robot.intakeArm.getCurrentAngle());
    basePower = (armTorque / Constants.IntakeArm.INTAKE_ARM_MOTOR_MAX_TORQUE);
    power = basePower + Robot.oi.joystick.getY();

    if(Robot.intakeArm.getCurrentAngle() < 2 && power < 0) {
      power = 0;
    } else if (Robot.intakeArm.getCurrentAngle() > 180 && power > 0) {
      power = basePower;
    }

    Robot.intakeArm.moveArm(power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.intakeArm.moveArm(basePower);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
