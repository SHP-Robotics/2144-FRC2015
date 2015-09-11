package org.usfirst.frc.team2144.robot.subsystems;

import org.usfirst.frc.team2144.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TurretRotation extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	static Victor turretX = RobotMap.turretX;
	static AnalogPotentiometer turretPot = RobotMap.turretPot;
	
	public double getTurretPos(){
		return turretPot.get();
		
	}
	public void move(double speed){
		turretX.set(speed);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

