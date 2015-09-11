package org.usfirst.frc.team2144.robot.commands;

/**
 *
 */
public class AdjustArm extends CommandBase {
	private boolean armPos;

	public AdjustArm() {
		this(true);
	}

	public AdjustArm(boolean position) {
		requires(arm);
		armPos = position;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		arm.setPosition(armPos);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}