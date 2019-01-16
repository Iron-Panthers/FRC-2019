package org.usfirst.frc.team5026.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.MotorGroup;

/**
 * This class is meant to store raw hardware instances. Examples: Motor
 * controllers, sensors, etc. Meant to contain hardware declarations that would
 * otherwise be in Robot class.
 */
public class Hardware {
    /** Drivebase TalonSRX */
    public TalonSRX driveRight1;
    public TalonSRX driveRight2;
    public TalonSRX driveLeft1;
    public TalonSRX driveLeft2;

    /** Drivebase MotorGroups */
    public MotorGroup rightDriveMotors;
    public MotorGroup leftDriveMotors;

    /** Motors/sensors for other subsystems will go down here */

    public Hardware() {
        driveRight1 = new TalonSRX(Constants.Drivebase.DRIVE_R1_PORT);
        driveRight2 = new TalonSRX(Constants.Drivebase.DRIVE_R2_PORT);
        driveLeft1 = new TalonSRX(Constants.Drivebase.DRIVE_L1_PORT);
        driveLeft2 = new TalonSRX(Constants.Drivebase.DRIVE_L2_PORT);
        rightDriveMotors = new MotorGroup("Drive (right) motor group", driveRight1, driveRight2);
        leftDriveMotors = new MotorGroup("Drive (left) motor group", driveLeft1, driveLeft2);

        rightDriveMotors.setNeutralMode(NeutralMode.Brake);
        leftDriveMotors.setNeutralMode(NeutralMode.Brake);
    }
}