/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.subsystems.swerve.auto;

import org.usfirst.frc.team5026.robot.Robot;
import org.usfirst.frc.team5026.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class FollowPath extends Command {

  Path path;
  
  int currentChunkIndex;
  int totalChunks;
  PathChunk currentChunk;

  double angleDeadzoneSize;
  double forwardDeadzoneSize;

  public FollowPath(Path path, double forwardDeadzoneSize, double angleDeadzoneSize) {

    requires(Robot.drive);
    this.path = path;

    this.angleDeadzoneSize = angleDeadzoneSize;
    this.forwardDeadzoneSize = forwardDeadzoneSize;

    totalChunks = path.chunks.size();
    currentChunkIndex = 0;
    currentChunk = path.chunks.get(0);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    Robot.drive.setAutoMode(true);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    //if all the targets have been reached, and the gyro is in the deadzone
    if(Robot.drive.allTargetsReached() && Robot.hardware.gyro.targetReached()) {

      currentChunkIndex ++;

      if( !(currentChunkIndex == totalChunks) ) {

        currentChunk = path.chunks.get(currentChunkIndex);
        Robot.drive.startNewAutoChunk(currentChunk.targetDeltaPositionInches * currentChunk.ticksPerInch, Constants.SwerveDrive.AUTO_FORWARD_DEADZONE, currentChunk.targetAbsoluteSwerveAngle);
        Robot.hardware.gyro.startNewSegment(currentChunk.targetDeltaAngle, Constants.SwerveDrive.AUTO_ANGLE_DEADZONE);
      }
    }
    
    currentChunk.update(Robot.drive.getAvgEncDelta(), Robot.hardware.gyro.getDeltaAngle());

    Robot.drive.set(currentChunk.targetAbsoluteSwerveAngle, currentChunk.getForward(), currentChunk.getTurn());

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    return currentChunkIndex == totalChunks;

  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

    Robot.drive.setAutoMode(false);

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {

    Robot.drive.setAutoMode(false);

  }

}
