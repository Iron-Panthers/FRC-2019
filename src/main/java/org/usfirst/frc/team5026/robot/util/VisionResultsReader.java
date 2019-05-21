/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

import java.util.ArrayList;

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
    public double hatchEyeAngle;
	public double hatchDistance;
	public double hatchWallAngle;
	public double hatchX;
	public double hatchY;
	public boolean cargoValid;
	public double cargoAngle;
	public double cargoDistance;

	public double hatchAngle = 0;

	public ArrayList<Long> timeLog = new ArrayList<>();
	public ArrayList<Double> xLog = new ArrayList<>();
	public ArrayList<Double> yLog = new ArrayList<>();

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
        hatchEyeAngle = Double.parseDouble(splitData[2]);
		hatchDistance = Double.parseDouble(splitData[3]);
		hatchWallAngle = Double.parseDouble(splitData[4]);
		hatchX = Double.parseDouble(splitData[5]);
		hatchY = Double.parseDouble(splitData[6]);
        cargoValid = Integer.parseInt(splitData[7]) == 0 ? false : true;
		cargoAngle = Double.parseDouble(splitData[8]);
		cargoDistance = Double.parseDouble(splitData[9]);

		timeLog.add(System.currentTimeMillis());
		xLog.add(hatchX);
		yLog.add(hatchY);
    }

    /**
     * Puts the vision information to the dashboard. Does not update the results.
     */
    public void putToDashboard() {
        SmartDashboard.putString("Vision Results", dataString);
		SmartDashboard.putNumber("Vision Timestamp", timeStamp);
		SmartDashboard.putNumber("Hatch Angle", hatchEyeAngle);
		SmartDashboard.putNumber("Hatch Distance", hatchDistance);
		SmartDashboard.putNumber("Hatch Wall Angle", hatchWallAngle);
		SmartDashboard.putNumber("Hatch X", hatchX);
		SmartDashboard.putNumber("Hatch Y", hatchY);
		SmartDashboard.putBoolean("Cargo Valid", cargoValid);
        SmartDashboard.putNumber("Cargo Angle", cargoAngle);
        SmartDashboard.putNumber("Cargo Distance", cargoDistance);
    }
}
