/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5026.robot.util.Constants;

/**
 * Add your docs here.
 */
public class Hardware {
    public TalonSRX armMotor;

    public Hardware(){
        armMotor = new TalonSRX(Constants.IntakeArm.INTAKE_ARM_MOTOR_PORT);
    }
}
