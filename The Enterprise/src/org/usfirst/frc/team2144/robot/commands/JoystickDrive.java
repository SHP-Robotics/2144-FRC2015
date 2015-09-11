package org.usfirst.frc.team2144.robot.commands;

import org.usfirst.frc.team2144.robot.OI;
import org.usfirst.frc.team2144.robot.Robot;
import org.usfirst.frc.team2144.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickDrive extends Command {

    public JoystickDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Drivetrain.robot.tankDrive(OI.stick.getY()*-1,OI.stick2.getY()*-1);
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
