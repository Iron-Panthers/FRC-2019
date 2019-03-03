package org.usfirst.frc.team5026.robot.util;

import java.util.Arrays;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Represents a group of light sensors in the front (or back) of the robot. (you might have to change some numbahs)
 * The sensors are centered in the central center of the robot.
 */
public class LightSensorGroup {
    private AnalogInput[] sensors;

    private AnalogInput[] leftSensors;
    private AnalogInput centerSensor;
    private AnalogInput[] rightSensors;

    private double sideWeight = Constants.LineFollow.LINEFOLLOW_P;
    private double centerWeight = Constants.LineFollow.LINEFOLLOW_INNER_REACTION_POWER;

    private double maxValue;

    /**
     * @param sensors all the light sensors left to right
     */
    public LightSensorGroup(double maxValue, AnalogInput...sensors) {
        this.maxValue = maxValue;
        this.sensors = sensors;

        leftSensors = Arrays.copyOfRange(sensors, 0, sensors.length / 2);
        rightSensors = Arrays.copyOfRange(sensors, sensors.length - sensors.length / 2, sensors.length);
        if(sensors.length % 2 != 0) {
            centerSensor = sensors[sensors.length / 2];
        } else {
            centerSensor = null;
        }
    }

    public void setWeights(double side, double center) {
        sideWeight = side;
        centerWeight = center;
    }

    /**
     * Gets the desired power for the left side of the robot.
     * Not necessarily in a range of 0-1 yet. (TODO)
     */
    public double getLeftPower() {
        double centerVal = 0;
        if(centerSensor != null) {
            centerVal = centerSensor.getVoltage() / maxValue;
        }
        return (sideWeight / maxValue) * (getAverageSensorReadings(rightSensors) - getAverageSensorReadings(leftSensors)) + centerWeight * (centerVal / maxValue);
    }

    /**
     * Gets the desired power for the left side of the robot.
     * Not necessarily in a range of 0-1 yet. (TODO)
     */
    public double getRightPower() {
        double centerVal = 0;
        if(centerSensor != null) {
            centerVal = centerSensor.getVoltage() / maxValue;
        }
        return (sideWeight / maxValue) * (getAverageSensorReadings(leftSensors) - getAverageSensorReadings(rightSensors)) + centerWeight * (centerVal / maxValue);
    }

    private double getAverageSensorReadings(AnalogInput[] sensors) {
        double total = 0;
        for(AnalogInput s : sensors) {
            total += s.getVoltage();
        }
        return total / sensors.length;
    }
}