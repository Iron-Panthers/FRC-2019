/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class JetsonReader {
    private NetworkTable table;

    public JetsonReader(int port) {
        NetworkTableInstance instance = NetworkTableInstance.getDefault();
        instance.startClientTeam(5026, port);
        table = instance.getTable("jetson");
    }
}
}
