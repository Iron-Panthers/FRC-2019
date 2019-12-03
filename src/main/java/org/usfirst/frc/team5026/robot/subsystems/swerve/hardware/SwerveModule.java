package org.usfirst.frc.team5026.robot.subsystems.swerve.hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class SwerveModule {

    public SwerveMC swerve;
    public TalonSRX drive;

    boolean isPointingForwards;
    boolean autoMode;

    double encTarget;
    double encDeadzone;
    public boolean targetReached;

    double forwardPosAtChunkStart;

    /**
     * An object containing a swerve motor and its attatched drive motor. Has the capability to set these, manage their rotational optimization, and 
     * keep track of autonomous driving targets. To be clear, this does NOT include motion profiling - that aspect of this codebase is done at the
     * PathChunk level, and the output is merely passed down here.
     * @param swerveMotor
     * @param driveMotor
     */
    
    public SwerveModule(SwerveMC swerveMotor, TalonSRX driveMotor) {

        swerve = swerveMotor;
        drive = driveMotor;

        targetReached = false;
        autoMode = false;

    }

    /**
     * Set the module to to drive forward at a given power, and to follow a swerveAngle, while anticipating the robot chassis to turn an amount.
     * @param swerveAngle
     * @param anticipatedTurn
     * @param drivePower
     */
    public void set(double swerveAngle, double anticipatedTurn, double drivePower) {

        if(autoMode) {
            setWithKnownForwardOrBackward(swerveAngle, anticipatedTurn, drivePower, this.isPointingForwards);
        }

        else {
            setWithKnownForwardOrBackward(swerveAngle, anticipatedTurn, drivePower, swerve.checkForwardsMoreEfficient(swerveAngle));
        }

    }

    private void setWithKnownForwardOrBackward(double swerveAngle, double anticipatedTurn, double drivePower, boolean isForwards) {

        if(isForwards) {
            //the module will be pointing forward

            swerve.moveToForwardAngle(swerveAngle, anticipatedTurn);
            drive.set(ControlMode.PercentOutput, drivePower);
        }
        
        else {

            //the module will be pointing backward, so set with negative drive power
            swerve.moveToBackwardAngle(swerveAngle, anticipatedTurn);
            drive.set(ControlMode.PercentOutput, -drivePower);
        }

    }

    /**
     * Tells the SwerveModule whether autononous driving is currrently happening.
     * If this is the case, the module will switch from dynamically figuring out
     * which swerve orientation is most efficient (in teleop), to keeping a constant swerve orientation
     * for the entirety of a segment (in auto).
     * @param autoModeOn
     */
    public void setAutoMode(boolean autoModeOn) {
        autoMode = autoModeOn;
    }

    /**
     * Call this when you want to reset the data about the current target, and the current swerve angle being forward or backwards.
     * @param encDelta
     * @param forwardDeadzoneSize
     */
    public void startNewSegment(double encDelta, double forwardDeadzoneSize, double swerveAngle) {

        isPointingForwards = swerve.checkForwardsMoreEfficient(swerveAngle);
        encTarget = (isPointingForwards) ? (drive.getSelectedSensorPosition() + encDelta) : (drive.getSelectedSensorPosition() - encDelta);
        encDeadzone = forwardDeadzoneSize;
        targetReached = false;
        forwardPosAtChunkStart = drive.getSelectedSensorPosition();

    }

    /**
     * gets the forward distance travelled by the module since the last chunk started
     * @return
     */
    public double getForwardDelta() {
        return (isPointingForwards) ? drive.getSelectedSensorPosition() - forwardPosAtChunkStart : forwardPosAtChunkStart - drive.getSelectedSensorPosition();
    }

    /**
     * checks if the target has been reached. Note that this will return true if the current target was reached at ANY point in the past.
     * This is done on purpose for use in auto paths. It provides a safety net in case of overshooting.
     * @return
     */
    public boolean targetReached() {

        if(Math.abs(drive.getSelectedSensorPosition() - encTarget) < encDeadzone) {
            targetReached = true;
        }
        
        return targetReached;

    }

    /**
     * Stops the module. No motors will move. 
     */
    public void stop() {

        swerve.set(ControlMode.PercentOutput, 0);
        drive.set(ControlMode.PercentOutput, 0);

    }

    /**
     * Idles the module. The drive motor will not move, and the swerve motor will position itself facing straight forward. Ignores automode being on or off.
     */
    public void idle() {

        setWithKnownForwardOrBackward(0, 0, 0, true);

    }

}