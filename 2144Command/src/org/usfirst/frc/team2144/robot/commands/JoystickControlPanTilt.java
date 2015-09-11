package org.usfirst.frc.team2144.robot.commands;

/**
 *
 */
public class JoystickControlPanTilt extends CommandBase {

	public JoystickControlPanTilt() {
		requires(cameraPanTilt);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		int cameraXPos = 85, cameraYPos = 160;

		if (oi.getCameraPOV() == 180 && cameraYPos > 0) {// down
			cameraYPos--;
		} else if (oi.getCameraPOV() == 0 && cameraYPos < 170) {// up
			cameraYPos++;
		} else if (oi.getCameraPOV() == 270 && cameraXPos > 0) {// left
			cameraXPos++;
		} else if (oi.getCameraPOV() == 90 && cameraXPos < 170) {// right
			cameraXPos--;
		} else if (oi.getCameraPOV() == 45 && cameraYPos < 170
				&& cameraXPos < 170) {// up, right
			cameraYPos++;
			cameraXPos--;
		} else if (oi.getCameraPOV() == 135 && cameraXPos < 170
				&& cameraYPos > 0) {// down, right
			cameraXPos--;
			cameraYPos--;
		} else if (oi.getCameraPOV() == 225 && cameraXPos > 0 && cameraYPos > 0) {// down,
																					// left
			cameraXPos++;
			cameraYPos--;
		} else if (oi.getCameraPOV() == 315 && cameraXPos > 0
				&& cameraYPos < 170) {// up, left
			cameraXPos++;
			cameraYPos++;
		} else if (oi.getCameraReset()) {
			cameraXPos = 85;
			cameraYPos = 160;

		}

		cameraPanTilt.setXAngle(cameraXPos);
		cameraPanTilt.setYAngle(cameraYPos);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}