package org.usfirst.frc.team5026.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

import org.usfirst.frc.team5026.robot.subsystems.swerve.hardware.SwerveMC;
import org.usfirst.frc.team5026.robot.subsystems.swerve.hardware.SwerveModule;
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

	/* Swerve Motors*/

	public SwerveMC swerveFrontRight;
	public SwerveMC swerveFrontLeft;
	public SwerveMC swerveBackRight;
	public SwerveMC swerveBackLeft;

	/* Drive Motors*/

	public TalonSRX driveFrontRight;
	public TalonSRX driveFrontLeft;
	public TalonSRX driveBackRight;
	public TalonSRX driveBackLeft;

	/* Swerve Modules*/

	public SwerveModule frontRightModule;
	public SwerveModule frontLeftModule;
	public SwerveModule backRightModule;
	public SwerveModule backLeftModule;
	
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
		swerveFrontRight = new SwerveMC(Constants.SwerveDrive.SWERVE_FRONT_RIGHT_PORT, Constants.SwerveDrive.SWERVE_P, Constants.SwerveDrive.SWERVE_D);
		swerveFrontLeft = new SwerveMC(Constants.SwerveDrive.SWERVE_FRONT_LEFT_PORT, Constants.SwerveDrive.SWERVE_P, Constants.SwerveDrive.SWERVE_D);
		swerveBackRight = new SwerveMC(Constants.SwerveDrive.SWERVE_BACK_RIGHT_PORT, Constants.SwerveDrive.SWERVE_P, Constants.SwerveDrive.SWERVE_D);
		swerveBackLeft = new SwerveMC(Constants.SwerveDrive.SWERVE_BACK_LEFT_PORT, Constants.SwerveDrive.SWERVE_P, Constants.SwerveDrive.SWERVE_D);

		//set inverted here if you want. e.g. swerveFrontRight.setInverted(true);

		/* Drive motor instanciation and configuration */
		driveFrontRight = new TalonSRX(Constants.SwerveDrive.DRIVE_FRONT_RIGHT_PORT);
		driveFrontLeft = new TalonSRX(Constants.SwerveDrive.DRIVE_FRONT_LEFT_PORT);
		driveBackRight = new TalonSRX(Constants.SwerveDrive.DRIVE_BACK_RIGHT_PORT);
		driveBackLeft = new TalonSRX(Constants.SwerveDrive.DRIVE_BACK_LEFT_PORT);

		//set inverted, idle mode or ramp rate here if you so desire

		/*Swerve Module instanciation*/
		frontRightModule = new SwerveModule(swerveFrontRight, driveFrontRight);
		frontLeftModule = new SwerveModule(swerveFrontLeft, driveFrontLeft);
		backRightModule = new SwerveModule(swerveBackRight, driveBackRight);
		backLeftModule = new SwerveModule(swerveBackLeft, driveBackLeft);

		/* Gyro */
		gyroTestMotor = new TalonSRX(5);
		gyro = new SuperiorGyro(gyroTestMotor, Constants.DrivebaseProperties.START_HEADING);

	}
}
