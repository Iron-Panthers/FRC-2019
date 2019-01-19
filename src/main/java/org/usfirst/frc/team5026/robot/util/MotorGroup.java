package org.usfirst.frc.team5026.robot.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotorGroup {
    private String motorGroupName;
    private TalonSRX masterMotor;
    private IMotorController[] motors;

    /**
     * MotorGroup is a flexible-size grouping of TalonSRX
     * 
     * @param motorGroupName the name of the MotorGroup, (displayed in
     *                       SmartDashboard)
     * @param motors         the motors to include in the MotorGroup. First motor is
     *                       assumed to be the "leader motor"
     */
    public MotorGroup(String motorGroupName, TalonSRX masterMotor, IMotorController... motors) {
        this.masterMotor = masterMotor;
        this.motors = motors;
        this.motorGroupName = motorGroupName;
        followMaster();
    }

    /**
     * Sets all the non-master motors to follow the masterMotor
     */
    void followMaster() {
        for (IMotorController motor : this.motors) {
            motor.follow(this.masterMotor);
        }
    }

    /**
     * Does configuration for PID constants + feed forward constant. This is applied
     * to only the master motor
     * 
     * @param kP the proportional term
     * @param kI the integral term
     * @param kD the derivative term
     * @param kF the feed forward term
     */
    public void configPID(double kP, double kI, double kD, double kF) {
        masterMotor.config_kP(0, kP);
        masterMotor.config_kI(0, kI);
        masterMotor.config_kD(0, kD);
        masterMotor.config_kF(0, kF);
    }

    /**
     * Sets the PercentOutput power of the master motor
     * 
     * @param power (should be between -1.0 and 1.0)
     */
    public void set(double power) {
        masterMotor.set(ControlMode.PercentOutput, power);
        for (IMotorController motor : this.motors) {
            SmartDashboard.putNumber(motorGroupName + " ID: " + motor.getDeviceID(), motor.getMotorOutputPercent());
        }
    }

    /**
     * Sets the power of the master motor to be 0
     */
    public void stop() {
        set(0);
    }

    /**
     * Sets a MotionMagic target for the master motor of a MotorGroup
     * 
     * @param target the target for MotionMagic mode
     */
    public void setTarget(double target) {
        masterMotor.set(ControlMode.MotionMagic, target);
        for (IMotorController motor : this.motors) {
            SmartDashboard.putNumber(motorGroupName + " ID: " + motor.getDeviceID(), motor.getMotorOutputPercent());
        }
    }

    /**
     * Sets all TalonSRX in a MotorGroup to a specified neutral mode.
     * 
     * @param neutralMode desired neutral mode (brake/coast)
     */
    public void setNeutralMode(NeutralMode neutralMode) {
        for (IMotorController motor : this.motors) {
            motor.setNeutralMode(neutralMode);
        }
	}
	/**
	 * Sets all TalonSRX in a MotorGroup to inverted or not.
	 * @param isInverted boolean isInverted (true/false)
	 */
	public void setInverted(boolean isInverted) {
		masterMotor.setInverted(isInverted);
		for (IMotorController motor : this.motors) {
			motor.setInverted(isInverted);
		}
	}

    /**
     * This can be put in the respective subsystem for the MotorGroup. Then, the
     * subsystem reset() method can be called in Robot class during initialization
     * for auton/disabled/etc.
     */
    public void reset() {
        stop();
    }
}