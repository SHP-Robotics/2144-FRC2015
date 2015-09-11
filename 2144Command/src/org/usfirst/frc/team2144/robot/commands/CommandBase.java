package org.usfirst.frc.team2144.robot.commands;

import org.usfirst.frc.team2144.robot.OI;
import org.usfirst.frc.team2144.robot.subsystems.Arm;
import org.usfirst.frc.team2144.robot.subsystems.CameraPanTilt;
import org.usfirst.frc.team2144.robot.subsystems.Drive;
import org.usfirst.frc.team2144.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class CommandBase extends Command {
	public static Drive drive;
	public static Arm arm;
	public static Elevator elevator;
	public static CameraPanTilt cameraPanTilt;
	public static OI oi;

	public static void init() {
		drive = new Drive();
		arm = new Arm();
		elevator = new Elevator();
		oi = new OI();
	}
}