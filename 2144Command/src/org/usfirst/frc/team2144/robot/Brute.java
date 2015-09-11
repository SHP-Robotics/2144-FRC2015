package org.usfirst.frc.team2144.robot;

import org.usfirst.frc.team2144.robot.commands.CameraFeed;
import org.usfirst.frc.team2144.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

//This is a random comment
/**
 * This is a new command based robot, derived from example code provided by team 2144.
 * 
 * @author Andrew Zhu
 * @author Patrick Kao
 */
public class Brute extends IterativeRobot {

	public static OI oi;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		CommandBase.init();

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		CameraFeed.getInstance().start();// might need to go somewhere else b/c
											// might throw exceptions
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}