package org.usfirst.frc.team5026.robot.subsystems.swerve.auto;

public class PathConfig {

    double ticksPerInch;
    double ticksToAccelerate; 
    double degreesToAccelerate; 
    double turningCircumference;
    double forwardMaintenanceP;
    double angleMaintenanceP;

    public PathConfig(double ticksPerInch, double ticksToAccelerate, double degreesToAccelerate, double turningCircumference, 
    double forwardMaintenanceP, double angleMaintenanceP) {

        this.ticksPerInch = ticksPerInch;

        this.turningCircumference = turningCircumference;

        this.ticksToAccelerate = ticksToAccelerate;
        this.degreesToAccelerate = degreesToAccelerate;

        this.angleMaintenanceP = angleMaintenanceP;
        this.forwardMaintenanceP = forwardMaintenanceP;
    }

}