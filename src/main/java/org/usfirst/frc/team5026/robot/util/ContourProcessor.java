/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot.util;

/**
 * Add your docs here.
 */
public class ContourProcessor {

    public static double[] findAngleAndDistance(double[] leftContourXValues, double[] leftContourYValues, 
    double[] rightContourXValues, double[] rightContourYValues) {
        double[] leftContourHighPoint = calcExtremePointInDirection(leftContourYValues, leftContourXValues, true);
        double[] leftContourSidePoint = calcExtremePointInDirection(leftContourXValues, leftContourYValues, false);
        double[] rightContourHighPoint = calcExtremePointInDirection(rightContourYValues, rightContourXValues, true);
        double[] rightContourSidePoint = calcExtremePointInDirection(rightContourXValues, rightContourYValues, true);

        double leftSideHeight = Math.abs(leftContourHighPoint[0] - leftContourSidePoint[1]);
        double rightSideHeight = Math.abs(rightContourHighPoint[0] - rightContourSidePoint[1]);
        double avgSideHeight = (leftSideHeight + rightSideHeight)/2;
        double widthBetweenHighPoints = Math.abs(leftContourHighPoint[1] - rightContourHighPoint[1]);

        double distance = Constants.VP.TAPE_SIDE_HEIGHT / (2 * Math.tan(avgSideHeight/Constants.VP.VERTICAL_ANGLE_TO_DEFAULT_HEIGHT));
        double expectedWidth = Constants.VP.HORIZONTAL_ANGLE_TO_DEFAULT_WIDTH * Math.atan(Constants.VP.TAPE_WIDTH_BETWEEN_UPPER_POINTS * (2/distance) );
        double angle = Math.acos(widthBetweenHighPoints/expectedWidth);
        double[] returnList = {distance, angle};
        return returnList;
    }
    /**
     * calculates the furthest point in either the x or y direction given two lists of x and y 
     * coordinates which form ordered pairs.
     * @param comparativeAxisList
     * @param otherAxisList
     * @param largest determines whether the point will be the greatest or least point in a given direction
     * @return double[] = {pointx, pointy}
     */
    private static double[] calcExtremePointInDirection(double[] comparativeAxisList, double[] otherAxisList, boolean largest) {
        int Index = 0; 
        double relevantDirCoordinate = 0; //the coordinate in the comparative axis direction
        double otherDirCoordinate = 0; //the coordinate in the other axis direction
        for(int i = 0; i < comparativeAxisList.length; i++) {
            if(relevantDirCoordinate < comparativeAxisList[i] && largest) {
                Index = i;
                otherDirCoordinate = otherAxisList[i];
                relevantDirCoordinate = comparativeAxisList[i];
            }
            else if(relevantDirCoordinate > comparativeAxisList[i] && largest == false) {
                Index = i;
                otherDirCoordinate = otherAxisList[i];
                relevantDirCoordinate = comparativeAxisList[i];
            }
        }
        double[] returnCoordinates = {relevantDirCoordinate, otherDirCoordinate};
        return returnCoordinates;
    }
}