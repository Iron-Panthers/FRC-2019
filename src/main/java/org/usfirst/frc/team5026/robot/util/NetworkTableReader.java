/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Reads from a single network table on a given ip and port.
 * 
 * All the methods that get specific values from the table just exist for reasons.
 */
public class NetworkTableReader {
    private NetworkTable table;

    public NetworkTableReader(String tableName) {
        NetworkTableInstance instance = NetworkTableInstance.getDefault();
        
        table = instance.getTable(tableName);
    }

    public NetworkTableEntry getEntry(String key) {
        return table.getEntry(key);
    }

    public double getDouble(String key, double defaultValue) {
        return getEntry(key).getDouble(defaultValue);
    }

    public double[] getDoubles(String key, double[] defaultValue) {
        return getEntry(key).getDoubleArray(defaultValue);
    }

    public double[] getDoubles(String key) {
        return getDoubles(key, new double[0]);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getEntry(key).getBoolean(defaultValue);
    }

    public boolean[] getBooleans(String key, boolean[] defaultValue) {
        return getEntry(key).getBooleanArray(defaultValue);
    }

    public boolean[] getBooleans(String key) {
        return getBooleans(key, new boolean[0]);
    }
}
