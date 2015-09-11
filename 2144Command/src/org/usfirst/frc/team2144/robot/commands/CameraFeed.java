package org.usfirst.frc.team2144.robot.commands;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.AxisCamera;

/**
 * Could be a subsytem with a command. Should it be? Note: This class is
 * singleton. Don't directly instantiate it. Call getInstance()
 */
public class CameraFeed extends Command {
	public static CameraFeed instance;

	AxisCamera camera;

	public static CameraFeed getInstance() {
		if (instance == null)
			instance = new CameraFeed();
		return instance;
	}

	private CameraFeed() {
		camera = new AxisCamera("axis-camera2144.local");
		CameraServer.getInstance().setQuality(50);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Image image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		boolean wasSuccesful = camera.getImage(image);// Does this throw an
														// exception
		if (wasSuccesful) {
			NIVision.imaqFlip(image, image, NIVision.FlipAxis.HORIZONTAL_AXIS);
			NIVision.imaqFlip(image, image, NIVision.FlipAxis.VERTICAL_AXIS);
			CameraServer.getInstance().setImage(image);
		} else {
			System.out.println("Camera failed to get image");
		}
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