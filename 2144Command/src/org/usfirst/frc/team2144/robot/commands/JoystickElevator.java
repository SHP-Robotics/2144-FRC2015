package org.usfirst.frc.team2144.robot.commands;


/**
 *
 */
public class JoystickElevator extends CommandBase {
	public static final double WINCH_MULTIPLIER = .8;
	
    public JoystickElevator() {
        requires(elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	boolean stopOverride = oi.getStopOverride();
    	
    	if(!elevator.getTopLeftLimit() || !elevator.getTopRightLimit()&& !stopOverride){//if touch sensor at top is pressed, then...
    		if(oi.getElevatorY()>0){//if trying to go up, set motor speed to 0 (Not moving)
    			elevator.setSpeed(-0.1);
    		}
    		else{
    			elevator.setSpeed(oi.getElevatorY()*-WINCH_MULTIPLIER);//otherwise go down
    		}
    	}
    	else if(!elevator.getBottomLeftLimit()|| !elevator.getBottomRightLimit() && !stopOverride){//if touch sensor at bottom is pressed, then...
    		if(oi.getElevatorY()<0){//if trying to go down, set motor speed to 0 (Not moving)
    			elevator.setSpeed(-0.1);
    		}
    		else{
    			elevator.setSpeed(oi.getElevatorY()*-WINCH_MULTIPLIER);//otherwise go up
    		}
    	}
    	if(!elevator.getTopLeftLimit()|| !elevator.getTopRightLimit()&& !stopOverride && oi.getCameraReset()){//if touch sensor at top is pressed, then...
    		if(oi.getElevatorY()>0){//if trying to go up, set motor speed to 0 (Not moving)
    			elevator.setSpeed(-0.1);
    		}
    		else{
    			elevator.setSpeed(oi.getElevatorY()*-0.5);//otherwise go down
    		}
    	}
    	else if(!elevator.getBottomLeftLimit()|| !elevator.getBottomRightLimit()&& !stopOverride && oi.getCameraReset()){//if touch sensor at bottom is pressed, then...
    		if(oi.getElevatorY()<0){//if trying to go down, set motor speed to 0 (Not moving)
    			elevator.setSpeed(-0.1);
    		}
    		else{
    			elevator.setSpeed(oi.getElevatorY()*-0.5);//otherwise go up
    		}
    	}
    	else if(oi.getCameraReset()){
    		elevator.setSpeed((oi.getElevatorY()*-0.5)-0.1);
    	}
    	else{
    		elevator.setSpeed((oi.getElevatorY()*-WINCH_MULTIPLIER)-0.1);
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