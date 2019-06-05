package org.usfirst.frc.team5026.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;

import org.usfirst.frc.team5026.robot.subsystems.swerve.hardware.SwerveMC;
import org.usfirst.frc.team5026.robot.subsystems.swerve.hardware.SwerveMotorGroup;
import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.SuperiorGyro;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * This class is meant to store raw hardware instances. Examples: Motor
 * controllers, sensors, etc. Meant to contain hardware declarations that would
 * otherwise be in Robot class.
 */
public class Hardware {

	/* Swerve Stuff*/

	public SwerveMC swerveFrontRight;
	public SwerveMC swerveFrontLeft;
	public SwerveMC swerveBackRight;
	public SwerveMC swerveBackLeft;

	public SwerveMotorGroup swerveMotors;

	/* Drive Motors*/

	public TalonSRX driveFrontRight;
	public TalonSRX driveFrontLeft;
	public TalonSRX driveBackRight;
	public TalonSRX driveBackLeft;
	
	/* Gyro */
	public TalonSRX gyroTestMotor;
	public SuperiorGyro gyro;

	/* IntakeArm motor controllers */
	public TalonSRX armMotor;
	public TalonSRX armIntakeMotor;

	/* Climb motor controllers and pneumatics */
	public CANSparkMax leftMotor1;
	public CANSparkMax leftMotor2;
	public CANSparkMax leftMotor3;
	public CANSparkMax rightMotor1;
	public CANSparkMax rightMotor2;
	public CANSparkMax rightMotor3;

	public DigitalInput forwardLimit, reverseLimit;

	public TalonSRX trainingWheelMotor;

	public DoubleSolenoid superStructurePistons;
	public DoubleSolenoid hatchPiston;
	public DoubleSolenoid gearShift;

	/** Motors/sensors for other subsystems will go down here */

	public Hardware() {
		
		/* Swerve motor and motor group instanciation and configuration */
		swerveFrontRight = new SwerveMC(Constants.Drivebase.SWERVE_FRONT_RIGHT_PORT, Constants.Drivebase.SWERVE_P, Constants.Drivebase.SWERVE_D);
		swerveFrontLeft = new SwerveMC(Constants.Drivebase.SWERVE_FRONT_LEFT_PORT, Constants.Drivebase.SWERVE_P, Constants.Drivebase.SWERVE_D);
		swerveBackRight = new SwerveMC(Constants.Drivebase.SWERVE_BACK_RIGHT_PORT, Constants.Drivebase.SWERVE_P, Constants.Drivebase.SWERVE_D);
		swerveBackLeft = new SwerveMC(Constants.Drivebase.SWERVE_BACK_LEFT_PORT, Constants.Drivebase.SWERVE_P, Constants.Drivebase.SWERVE_D);

		//set inverted here if you want. e.g. swerveFrontRight.setInverted(true);

		swerveMotors = new SwerveMotorGroup(swerveFrontRight, swerveFrontLeft, swerveBackRight, swerveBackLeft);

		/* Drive motor instanciation and configuration */
		driveFrontRight = new TalonSRX(Constants.Drivebase.DRIVE_FRONT_RIGHT_PORT);
		driveFrontLeft = new TalonSRX(Constants.Drivebase.DRIVE_FRONT_LEFT_PORT);
		driveBackRight = new TalonSRX(Constants.Drivebase.DRIVE_BACK_RIGHT_PORT);
		driveBackLeft = new TalonSRX(Constants.Drivebase.DRIVE_BACK_LEFT_PORT);

		//set inverted, idle mode or ramp rate here if you so desire

		/* Gyro */
		gyroTestMotor = new TalonSRX(5);
		gyro = new SuperiorGyro(gyroTestMotor);

	}
}
