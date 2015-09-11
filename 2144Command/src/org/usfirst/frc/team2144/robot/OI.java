package org.usfirst.frc.team2144.robot;

import org.usfirst.frc.team2144.robot.commands.AdjustArm;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick jLeft = new Joystick(0), jRight = new Joystick(1);
	public Button precisionMode = new JoystickButton(jLeft, 1),
			mecanumLeft = new JoystickButton(jLeft, 4),
			mecanumRight = new JoystickButton(jLeft, 5),
			openArm = new JoystickButton(jRight, 3),
			closeArm = new JoystickButton(jRight, 4),
			cameraReset = new JoystickButton(jRight, 1),
			stopOverride = new JoystickButton(jRight, 7);

	public OI() {
		openArm.whenPressed(new AdjustArm(false));
		closeArm.whenPressed(new AdjustArm(true));
	}

	public double getDriveX() {
		return jLeft.getX();
	}

	public double getDriveY() {
		return jLeft.getY();
	}

	public double getElevatorY() {
		return jRight.getY();
	}

	public boolean getMecanumLeft() {
		return mecanumLeft.get();
	}

	public boolean getMecanumRight() {
		return mecanumRight.get();
	}

	public boolean getPrecisionMode() {
		return precisionMode.get();
	}

	public boolean getCameraReset() {// Also used in elevator?
		return cameraReset.get();
	}

	public boolean getStopOverride() {
		return stopOverride.get();
	}

	public int getCameraPOV() {
		return jRight.getPOV();
	}
}
