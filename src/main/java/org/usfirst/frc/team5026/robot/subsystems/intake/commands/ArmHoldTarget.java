/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.intake.commands;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class ArmHoldTarget extends Command {

  private double currentError;
  private double errorSum;
  private double errorChange;
  private double lastError;
  private double basePower;

  public ArmHoldTarget() {
    requires(Robot.intakeArm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.intakeArm.target = Robot.intakeArm.getCurrentAngle();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    currentError = Robot.intakeArm.target - Robot.intakeArm.getCurrentAngle();
    errorChange = currentError - lastError;
    errorSum += currentError;
    lastError = currentError;
    basePower = Robot.intakeArm.getBasePower();

    double power = -1 * ((Constants.IntakeArm.INTAKE_ARM_P * currentError)
        + (Constants.IntakeArm.INTAKE_ARM_I * errorSum) + (Constants.IntakeArm.INTAKE_ARM_D * errorChange));

    power *= Constants.IntakeArm.INTAKE_ARM_MAX_POWER;

    if (Math.abs(power) > Constants.IntakeArm.INTAKE_ARM_MAX_POWER) {
      power = Math.copySign(Constants.IntakeArm.INTAKE_ARM_MAX_POWER, power);
    }

    Robot.intakeArm.autoRetractHatch();
    Robot.intakeArm.moveArm(power + basePower);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
