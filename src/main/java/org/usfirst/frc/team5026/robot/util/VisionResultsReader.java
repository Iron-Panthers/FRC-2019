/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Reads from a given table, expecting a comma-separated list of vision data.
 */
public class VisionResultsReader {

    private NetworkTable table;
    public String dataString;

    // Vision data
    public long timeStamp;
    public boolean hatchValid;
    public double hatchAngle;
    public double hatchDisplacement;
	public boolean cargoValid;
	public double cargoAngle;
	public double cargoDistance;

    /**
     * Creates a new VisionResultsReader for the given table.
     */
    public VisionResultsReader(String tableName) {
        table = NetworkTableInstance.getDefault().getTable(tableName);
    }

    /**
     * Updates vision information with the data string receieved from the Jetson.
     * The data should have exactly five data points.
     */
    public void updateResults() {
        // Get the entry at the Camera key. If null, value defaults to "NOPE"
        dataString = table.getEntry(Constants.Camera.VISION_RESULTS_KEY).getString("none");

        // Split the data at all commas
        String[] splitData = dataString.split(",");

        // If we do not have all the required data, no need to continue
        if (splitData.length != 7) {
            return;
        }

        timeStamp = Long.parseLong(splitData[0]);
        hatchValid = Integer.parseInt(splitData[1]) == 0 ? false : true;
        hatchAngle = Double.parseDouble(splitData[2]);
        hatchDisplacement = Double.parseDouble(splitData[3]);
        cargoValid = Integer.parseInt(splitData[4]) == 0 ? false : true;
		cargoAngle = Double.parseDouble(splitData[5]);
		cargoDistance = Double.parseDouble(splitData[6]);
    }

    /**
     * Puts the vision information to the dashboard. Does not update the results.
     */
    public void putToDashboard() {
        SmartDashboard.putString("Vision Results", dataString);
        SmartDashboard.putNumber("Vision Timestamp", timeStamp);
        SmartDashboard.putBoolean("Cargo Valid", cargoValid);
        SmartDashboard.putNumber("Cargo Angle", cargoAngle);
        SmartDashboard.putNumber("Cargo Distance", cargoDistance);
    }
}
