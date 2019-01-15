/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Add your docs here.
 */
public class Hardware {
    public AnalogInput frontLightSensorLeft;
    public AnalogInput frontLightSensorRight;
    public AnalogInput backLightSensorLeft;
    public AnalogInput backLightSensorRight;
    public AnalogInput centerLightSensor;
    public TalonSRX leftM;
    public TalonSRX rightM;

    public Hardware() {
        leftM = new TalonSRX(1);
        rightM = new TalonSRX(9);
        rightM.setInverted(true);

        frontLightSensorLeft = new AnalogInput(1);
        frontLightSensorRight = new AnalogInput(0);
        backLightSensorLeft = new AnalogInput(2);
        backLightSensorRight = new AnalogInput(3);
        centerLightSensor = new AnalogInput(4);
    }
}
