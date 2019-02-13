package org.usfirst.frc.team5026.robot.util;

public class Vector {
	
	private double x;
	private double y;
	private double magnitude; // The magnitude of the vector
	
	/**
	 * Constructs a 2D vector
	 * 
	 * @param x coordinate of the vector
	 * @param y coordinate of the vector
	 */
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
		calcMagnitude();
	}
	
	/**
	 * Calculates the magnitude of the vector
	 */
	
	public void calcMagnitude() {
		this.magnitude = Math.sqrt(x*x + y*y);
	}
	
	/**
	 * Returns the magnitude of the vector
	 * 
	 * @return {@link Vector#magnitude}
	 */
	public double getMagnitude() {
		return this.magnitude;
	}
	
	/**
	 * Returns the x component of the vector 
	 * 
	 * @return {@link Vector#x}
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y component of the vector 
	 * 
	 * @return {@link Vector#y}
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Normalizes the vector
	 */
	public void norm() {
		if (this.magnitude < 0.0001) {
			return;
		}
		x /= this.magnitude;
		y /= this.magnitude;
		this.magnitude = 1;
	}
	
	/**
	 * Multiplies the vector components by some number m
	 */
	public void mult(double m) {
		x *= m;
		y *= m;
		this.magnitude *= m;
	}
	
	/**
	 * Zero's this vector
	 */
	public void zero() {
		this.x = 0;
		this.y = 0;
		this.magnitude = 0;
	}
}
