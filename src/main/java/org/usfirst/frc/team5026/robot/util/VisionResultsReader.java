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
public class VisionResultsReader {

    private NetworkTable table;
    public String dataString;

    // Vision data
    public int timeStamp;
    public boolean valid;
    public int x;
    public int y;
    public int theta;

    public VisionResultsReader(String tableName) {
        table = NetworkTableInstance.getDefault().getTable(tableName);
    }

    /**
     * Updates vision information with the data string receieved from the Jetson.
     * The data should have exactly five data points.
     */
    public void updateResults() {
        dataString = table.getEntry(Constants.Camera.VISION_RESULTS_KEY).getString("NOPE");
        String[] splitData = dataString.split(",");
        timeStamp = Integer.parseInt(splitData[0]);
        valid = Integer.parseInt(splitData[1]) == 0 ? false : true;
        x = Integer.parseInt(splitData[2]);
        y = Integer.parseInt(splitData[3]);
        theta = Integer.parseInt(splitData[4]);
    }

    public void putToDashboard() {
        SmartDashboard.putString("VisionResults", dataString);
        SmartDashboard.putNumber("VisionTimestamp", timeStamp);
        SmartDashboard.putBoolean("VisionValid", valid);
        SmartDashboard.putNumber("VisionX", x);
        SmartDashboard.putNumber("VisionY", y);
        SmartDashboard.putNumber("VisionTheta", theta);
    }
}
