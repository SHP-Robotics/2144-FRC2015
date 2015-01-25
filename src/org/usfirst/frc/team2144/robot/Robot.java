package org.usfirst.frc.team2144.robot;                     
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	Joystick stick;
	Joystick stick2;
	DigitalInput winchtop1; 
	DigitalInput winchtop2; 
	//Relay spike;
	Solenoid out;
	Solenoid in;
	//Gyro gyro;
	//I2C i2c;
	CANTalon winch;
	PowerDistributionPanel pdp;
	int autoLoopCounter;
	
	
	
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	myRobot = new RobotDrive(0,1,2,3);//2:Green, 3:Pink, 0:Blue, 1:Orange
    	stick = new Joystick(0);
    	stick2 = new Joystick(1);
    	winchtop1 = new DigitalInput(0);
    	winchtop2 = new DigitalInput(1);
    	pdp = new PowerDistributionPanel();
    	//spike = new Relay(0);
    	out = new Solenoid(0);
    	in = new Solenoid(1);
    	winch = new CANTalon(0);
    	//gyro = new Gyro(0);
    	myRobot.setInvertedMotor(RobotDrive.MotorType.kRearLeft,true);
    	myRobot.setInvertedMotor(RobotDrive.MotorType.kFrontLeft,true);
    	//i2c = new I2C(I2C.Port.kOnboard, 168);
    	pdp.clearStickyFaults();
    	winch.changeControlMode(CANTalon.ControlMode.PercentVbus);
    	winch.enableControl();
    	
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	myRobot.setLeftRightMotorOutputs(1, 0);
    	/*if(autoLoopCounter<=100){
    		myRobot.mecanumDrive_Polar(0.2, 0, 0);
	    	
    	}
    	if(autoLoopCounter>=100){
    		if(autoLoopCounter<=200){
        		myRobot.mecanumDrive_Polar(0.2, 90, 0);
    	    	
        	}
    	}
    	if(autoLoopCounter>=200){
    		if(autoLoopCounter<=300){
        		myRobot.mecanumDrive_Polar(0.2, 180, 0);
    	    	
        	}
    	}
    	if(autoLoopCounter>=300){
    		if(autoLoopCounter<=400){
        		myRobot.mecanumDrive_Polar(0.2, 270, 0);
    	    	
        	}
    	}
    	autoLoopCounter++;*/
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    }
    // Hi Ender and Giorgio approves!!! 
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	if(stick.getRawButton(4)){
    		myRobot.mecanumDrive_Polar(0.7, 90, stick.getY()*-1);
    	}
    	else if(stick.getRawButton(5)){
    		myRobot.mecanumDrive_Polar(0.7, 270, stick.getY()*-1);
    	}
    	
    	else{
    		myRobot.arcadeDrive(stick.getX()*-1, stick.getY()*-1);
    	}
    	
    	if(stick2.getRawButton(2)){
    		out.set(false);
    		in.set(true);
    	}
    	else if(stick2.getRawButton(3)){
    		out.set(true);
    		in.set(false);
    	}
    	
    	if(!winchtop1.get()){//if touch sensor at top is pressed, then...
    		if(stick2.getY()<0){//if trying to go up, set motor speed to 0 (Not moving)
    			winch.set(0.1);
    		}
    		else{
    			winch.set(stick2.getY()*-0.5);//otherwise go down
    		}
    	}
    	else if(!winchtop2.get()){
    		if(stick2.getY()<0){
    			winch.set(0.1);
    		}
    		else{
    			winch.set(stick2.getY()*-0.5);
    		}
    	}
    	else{
    		winch.set(stick2.getY()*-0.5);
    	}
		
    	
    	System.out.println(winch.getPosition());
    		
    	
 
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
//RJ's commwent section'
//
//system.out.println("awwwww");
//Andrew's commwent section
/*double Xsquared = stick.getX()*stick.getX();//squares X input of stick1
double Ysquared = stick.getY()*stick.getY();//squares y input of stick1
double mag = Math.sqrt(Xsquared+Ysquared);//pythag theorem to get magnitude of result vector
double radAngle = Math.atan2(stick.getY(), stick.getX());//inverse tangent of two vectors to get angle of result
double rawAngle = radAngle * 57.2957795;//converts radian to degrees
double angle = 0;
if(Double.isNaN(radAngle)){
	if(stick.getY()<0){
		angle = 0;
	}
	else{
		angle = 180;
	}
}
else{
	if(stick.getX()>0){
    	if(stick.getY()<0){
    		angle = 90 - rawAngle;
    	}
    	if(stick.getY()>0){
    		angle = 90 + rawAngle;
    	}
	}
	if(stick.getX()<0){
    	if(stick.getY()<0){
    		angle = 270 + rawAngle;
    	}
    	if(stick.getY()>0){
    		angle = 270 - rawAngle;
    	}
	}
}//This comment section is the trig behind polar mecanum*/
/*double rawMag = (stick.getX()+stick.getY()*-1)/2;
double mag = rawMag/5;
double angle = stick.getDirectionDegrees();
//if(stick.getRawButton(1))
if(stick.getY()<0){
	myRobot.mecanumDrive_Polar(rawMag, angle+180, stick2.getX()*-1);
}
if(stick.getX()<0){
	myRobot.mecanumDrive_Polar(rawMag, angle+180, stick2.getX()*-1);
}
else{
	myRobot.mecanumDrive_Polar(rawMag, angle, stick2.getX()*-1);
}//old mecanum*/