package org.usfirst.frc.team5026.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import org.usfirst.frc.team5026.robot.util.Constants;
import org.usfirst.frc.team5026.robot.util.MotorGroup;
import org.usfirst.frc.team5026.robot.util.SparkMaxMotorGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * This class is meant to store raw hardware instances. Examples: Motor
 * controllers, sensors, etc. Meant to contain hardware declarations that would
 * otherwise be in Robot class.
 */
public class Hardware {
	/* Drivebase motor controllers */
	public TalonSRX driveRight1;
	public VictorSPX driveRight2;
	public TalonSRX driveLeft1;
	public VictorSPX driveLeft2;

	public TalonSRX gyroTestMotor;
	public PigeonIMU gyro;

	/* Drivebase MotorGroups */
	public MotorGroup rightDriveMotors;
	public MotorGroup leftDriveMotors;

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

	public TalonSRX trainingWheelMotor;

	public SparkMaxMotorGroup climbMotors;

	public DoubleSolenoid superStructurePistons;
	public DoubleSolenoid trainingWheelPiston;

	/** Motors/sensors for other subsystems will go down here */

	public Hardware() {
		/* Drivebase motor controller creation */
		driveRight1 = new TalonSRX(Constants.Drivebase.DRIVE_R1_PORT);
		driveRight2 = new VictorSPX(Constants.Drivebase.DRIVE_R2_PORT);
		driveLeft1 = new TalonSRX(Constants.Drivebase.DRIVE_L1_PORT);
		driveLeft2 = new VictorSPX(Constants.Drivebase.DRIVE_L2_PORT);

		/* Drivebase configuration */
		driveRight1.setInverted(Constants.Drivebase.IS_RIGHT_INVERTED);
		driveLeft1.setInverted(Constants.Drivebase.IS_LEFT_INVERTED);

		rightDriveMotors = new MotorGroup("Drive (right) motor group", driveRight1, driveRight2);
		leftDriveMotors = new MotorGroup("Drive (left) motor group", driveLeft1, driveLeft2);

		rightDriveMotors.configPID(Constants.Drivebase.P, Constants.Drivebase.I, Constants.Drivebase.D,
				Constants.Drivebase.F);
		leftDriveMotors.configPID(Constants.Drivebase.P, Constants.Drivebase.I, Constants.Drivebase.D,
				Constants.Drivebase.F);

		rightDriveMotors.setNeutralMode(NeutralMode.Brake);
		leftDriveMotors.setNeutralMode(NeutralMode.Brake);

		/* Gyro */
		gyroTestMotor = new TalonSRX(5);
		gyro = new PigeonIMU(gyroTestMotor);

		/* IntakeArm motor controller creation */
		armMotor = new TalonSRX(Constants.IntakeArm.INTAKE_ARM_MOTOR_PORT);
		armIntakeMotor = new TalonSRX(Constants.IntakeArm.INTAKE_MOTOR_PORT);
		armMotor.setNeutralMode(NeutralMode.Brake);

		/* Climb Subsystem creation */
		leftMotor1 = new CANSparkMax(Constants.Climb.LEFT_MOTOR_1_PORT, MotorType.kBrushless);
		leftMotor2 = new CANSparkMax(Constants.Climb.LEFT_MOTOR_2_PORT, MotorType.kBrushless);
		leftMotor3 = new CANSparkMax(Constants.Climb.LEFT_MOTOR_3_PORT, MotorType.kBrushless);
		rightMotor1 = new CANSparkMax(Constants.Climb.RIGHT_MOTOR_1_PORT, MotorType.kBrushless);
		rightMotor2 = new CANSparkMax(Constants.Climb.RIGHT_MOTOR_2_PORT, MotorType.kBrushless);
		rightMotor3 = new CANSparkMax(Constants.Climb.RIGHT_MOTOR_3_PORT, MotorType.kBrushless);

		// Motor Group
		// All are on the same motor group to reduce required limit switches
		climbMotors = new SparkMaxMotorGroup(leftMotor1, leftMotor2, leftMotor3, rightMotor1, rightMotor2, rightMotor3);
		leftMotor1.setInverted(Constants.Climb.IS_LEFT_INVERTED);
		leftMotor2.setInverted(Constants.Climb.IS_LEFT_INVERTED);
		leftMotor3.setInverted(Constants.Climb.IS_LEFT_INVERTED);
		rightMotor1.setInverted(Constants.Climb.IS_RIGHT_INVERTED);
		rightMotor2.setInverted(Constants.Climb.IS_RIGHT_INVERTED);
		rightMotor3.setInverted(Constants.Climb.IS_RIGHT_INVERTED);

		superStructurePistons = new DoubleSolenoid(Constants.Climb.SUPER_STRUCTURE_SOLENOID_PORT_1,
				Constants.Climb.SUPER_STRUCTURE_SOLENOID_PORT_2);
		trainingWheelPiston = new DoubleSolenoid(Constants.Climb.TRAINING_WHEEL_PISTON_SOLENOID_PORT_1,
				Constants.Climb.TRAINING_WHEEL_PISTON_SOLENOID_PORT_2);
	}
}
