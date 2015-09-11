package org.usfirst.frc.team2144.robot.subsystems;

import org.usfirst.frc.team2144.robot.commands.JoyStickDrive;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {
	RobotDrive drive;
	Talon frontLeft;
	Talon frontRight;
	Talon rearLeft, rearRight;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new JoyStickDrive());
	}

	public Drive() {
		frontLeft = new Talon(0);
		frontRight = new Talon(2);
		rearLeft = new Talon(1);
		rearRight = new Talon(3);
		drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
	}

	public void drive(double joystickX, double joystickY, boolean isPrecise,
			boolean mecanumLeft, boolean mecanumRight) {
		double speedMultiplier = 1, mecanumMultiplier = 1; // Should never equal
															// 1 during method
															// body

		if (isPrecise) {
			speedMultiplier = 0.5;
			mecanumMultiplier = 0.4;
		} else {
			if (speedMultiplier < 1) {
				speedMultiplier += 0.05;
			}
			if (mecanumMultiplier < 1) {
				mecanumMultiplier += 0.1;
			}
		}

		if (mecanumLeft && isPrecise) {// drive code
			drive.mecanumDrive_Polar(0.3, 90, -1 * joystickX);
		} else if (mecanumRight && isPrecise) {
			drive.mecanumDrive_Polar(0.3, 270, -1 * joystickX);
		} else if (mecanumLeft) {// drive code
			drive.mecanumDrive_Polar(mecanumMultiplier, 90, -1 * joystickX);
		} else if (mecanumRight) {
			drive.mecanumDrive_Polar(mecanumMultiplier, 270, -1 * joystickX);
		}

		else {
			if (isPrecise) {
				drive.arcadeDrive(joystickX * -0.5, joystickY * -0.5);
			} else {
				drive.arcadeDrive(joystickX * -speedMultiplier, joystickY
						* -speedMultiplier);
			}
		}
	}
}