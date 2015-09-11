package org.usfirst.frc.team2144.robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Victor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	public final static Victor left = new Victor(10);
	public final static Victor right = new Victor(9);
	public static final Victor turretX = new Victor(8);
	public static final AnalogPotentiometer turretPot = new AnalogPotentiometer(8,1023,0);
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
