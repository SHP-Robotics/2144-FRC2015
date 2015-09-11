package org.usfirst.frc.team2144.robot.subsystems;

import org.usfirst.frc.team2144.robot.commands.JoystickElevator;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
	private Talon winch;
	private DigitalInput winchtopL, winchbottomL, winchtopR, winchbottomR;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Elevator() {
		winch = new Talon(4);
		winchtopL = new DigitalInput(0);
		winchtopR = new DigitalInput(2);
		winchbottomL = new DigitalInput(1);
		winchbottomR = new DigitalInput(3);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickElevator());
	}

	public void setSpeed(double speed) {
		winch.set(speed);
	}

	public boolean getTopLeftLimit() {
		return winchtopL.get();
	}

	public boolean getTopRightLimit() {
		return winchtopR.get();
	}

	public boolean getBottomLeftLimit() {
		return winchbottomL.get();
	}

	public boolean getBottomRightLimit() {
		return winchbottomR.get();
	}
}