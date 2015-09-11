package org.usfirst.frc.team2144.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	RobotDrive brute;
	Joystick left, right;
	Talon winch;
	DigitalInput topleft, topright, bottomleft, bottomright;
	Solenoid out, in;
	Compressor pneumatics;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		brute = new RobotDrive(0, 1, 2, 3);
		left = new Joystick(0);
		right = new Joystick(1);
		winch = new Talon(4);
		topleft = new DigitalInput(0);
		topright = new DigitalInput(2);
		bottomleft = new DigitalInput(1);
		bottomright = new DigitalInput(3);
		pneumatics = new Compressor();
		out = new Solenoid(0);
		in = new Solenoid(1);

	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	public void autonomousInit() {

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	public void teleopInit() {
		
		brute.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		brute.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		if (left.getRawButton(4)) {
			brute.mecanumDrive_Polar(0.3, 90, -1 * left.getX());
		} else if (left.getRawButton(5)) {
			brute.mecanumDrive_Polar(0.3, 270, -1 * left.getX());
		} else {
			brute.arcadeDrive(left.getX() * -1, left.getY() * -1);
		}

		if (!bottomleft.get() || !bottomright.get()) {
			if (right.getY() > 0) {
				winch.set(0);
			} else {
				winch.set(right.getY());
			}
		} else if (!topleft.get() || !topright.get()) {
			if (right.getY() < 0) {
				winch.set(0);
			} else {
				winch.set(right.getY());
			}
		} else {
			winch.set(right.getY());
		}

		if (right.getRawButton(4)) {
			out.set(false);
			in.set(true);
		} else if (right.getRawButton(3)) {
			out.set(true);
			in.set(false);
		}

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

}
