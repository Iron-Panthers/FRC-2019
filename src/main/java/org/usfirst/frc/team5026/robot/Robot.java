/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5026.robot;

import org.usfirst.frc.team5026.robot.subsystems.climb.Climb;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.Climb1ElevatorHold;
import org.usfirst.frc.team5026.robot.subsystems.climb.commands.HoldElevator;
import org.usfirst.frc.team5026.robot.subsystems.drive.Drive;
import org.usfirst.frc.team5026.robot.subsystems.intake.Intake;
import org.usfirst.frc.team5026.robot.subsystems.intake.IntakeArm;
import org.usfirst.frc.team5026.robot.util.OI;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static Drive drive;
	public static OI oi;
	public static Hardware hardware;
	public static IntakeArm intakeArm;
	public static Intake intake;
	public static Climb climb;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		hardware = new Hardware();
		intakeArm = new IntakeArm();
		intake = new Intake();
		drive = new Drive();
		climb = new Climb();
		/** Instance of OI must be created after all subsystems */
		oi = new OI();
		// m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
		// chooser.addOption("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Robot -- Auton mode", m_chooser);
	}

	/**
	 * This function is called every robot packet, no matter the mode. Use this for
	 * items like diagnostics that you want ran during disabled, autonomous,
	 * teleoperated and test.
	 *
	 * <p>
	 * This runs after the mode specific periodic functions, but before LiveWindow
	 * and SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		SmartDashboard.putNumber("pos", hardware.driveRight1.getEncoder().getPosition());
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		drive.shiftHigh();
		climb.retractSuperStructurePistons();
		intake.hatchIntake();
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		drive.shiftHigh();
		climb.retractSuperStructurePistons();
		intake.hatchIntake();
		climb.resetDefaultCommand();
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("conversion", hardware.driveRight1.getEncoder().getVelocityConversionFactor());
		if (oi.stick1.findRightPower() > 0) {
			SmartDashboard.putNumber("speed", hardware.driveRight1.getEncoder().getVelocity() / oi.stick1.findRightPower());
		}
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
