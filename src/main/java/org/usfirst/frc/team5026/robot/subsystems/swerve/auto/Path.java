package org.usfirst.frc.team5026.robot.subsystems.swerve.auto;

import java.util.ArrayList;
import java.util.Collections;

public class Path {

    PathPoint[] points;
    PathConfig config;
    ArrayList<PathChunk> chunks;

    Path(PathPoint[] points, PathConfig config) {

        this.points = points;
        this.config = config;

        for (int i = 0; i < points.length - 1; i++) {
            
            chunks.add(new PathChunk(points[i], points[i+1], config.ticksPerInch, config.ticksToAccelerate, config.degreesToAccelerate, 
            config.turningCircumference, config.forwardMaintenanceP, config.angleMaintenanceP) );

        }
    }

}