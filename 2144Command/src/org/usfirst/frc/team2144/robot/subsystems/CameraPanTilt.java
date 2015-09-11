package org.usfirst.frc.team2144.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CameraPanTilt extends Subsystem {
	private Servo cameraX, cameraY;

	public void initDefaultCommand() {
	}

	public CameraPanTilt() {
		cameraX = new Servo(9);
		cameraY = new Servo(6);
	}

	public void setXAngle(double angle) {
		cameraX.setAngle(angle);
	}

	public void setYAngle(double angle) {
		cameraY.setAngle(angle);
	}

	public double getXAngle() {
		return cameraX.getAngle();
	}

	public double getYAngle() {
		return cameraY.getAngle();
	}

}