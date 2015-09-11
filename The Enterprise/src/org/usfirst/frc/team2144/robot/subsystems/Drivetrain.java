package org.usfirst.frc.team2144.robot.subsystems;

import org.usfirst.frc.team2144.robot.Robot;
import org.usfirst.frc.team2144.robot.RobotMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drivetrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	static Victor left = RobotMap.left;
	static Victor right = RobotMap.right;
	public static RobotDrive robot = new RobotDrive(left,right);
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
	
}

