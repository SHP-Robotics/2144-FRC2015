package org.usfirst.frc.team2144.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arm extends Subsystem {
	Solenoid out, in;
	Compressor pneumatics;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public Arm() {
		out = new Solenoid(0);
		in = new Solenoid(1);
		pneumatics = new Compressor();
		pneumatics.clearAllPCMStickyFaults();
	}

	public void setPosition(boolean pos) {// pos == true? close arms: open arms
		if (pos == true) {
			out.set(true);
			in.set(false);
		} else {
			out.set(false);
			in.set(true);
		}
	}
}