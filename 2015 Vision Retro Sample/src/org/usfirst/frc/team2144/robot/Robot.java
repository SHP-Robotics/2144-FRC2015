package org.usfirst.frc.team2144.robot;

import java.lang.Math;
import java.util.Comparator;
import java.util.Vector;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;

/**
 * Example of finding yellow totes based on retroreflective target.
 * This example utilizes an image file, which you need to copy to the roboRIO
 * To use a camera you will have to integrate the appropriate camera details with this example.
 * To use a USB camera instead, see the SimpelVision and AdvancedVision examples for details
 * on using the USB camera. To use an Axis Camera, see the AxisCamera example for details on
 * using an Axis Camera.
 *
 * Sample images can found here: http://wp.wpi.edu/wpilib/2015/01/16/sample-images-for-vision-projects/ 
 */
public class Robot extends SampleRobot {
		//A structure to hold measurements of a particle
		public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
			double PercentAreaToImageArea;
			double Area;
			double BoundingRectLeft;
			double BoundingRectTop;
			double BoundingRectRight;
			double BoundingRectBottom;
			double CenterofMassY;
			double CenterofMassX;
			
			public int compareTo(ParticleReport r)
			{
				return (int)(r.Area - this.Area);
			}
			
			public int compare(ParticleReport r1, ParticleReport r2)
			{
				return (int)(r1.Area - r2.Area);
			}
		};

		//Structure to represent the scores for the various tests used for target identification
		double Area;
		double Aspect;
		
		
		
		int arrayID;
		boolean toteFound = false;
		
		//Images
		Image frame;
		Image binaryFrame;
		int imaqError;
		
		//Camera
		AxisCamera camera;
		
		//camera mount servos
		Servo cameraX;
		Servo cameraY;
		
		//stuff
		int cameraXPos = 111;
		int cameraYPos = 87;
		//boolean camLEDs = false;
		Relay spike;
		int numTotes = 0;
		double toteX;
		double toteY;
		double distancetoTote;
		boolean goingRight = true;
		
		//Joysticks
		Joystick stick2;
		
		//Robot Stuff
		RobotDrive myRobot;
		Joystick stick;
		DigitalInput winchtopL; //0: TopLeft; 2 orange/1 green, 1: BottomLeft; 2grn/1blu, 2: TOPRight; ?, 3: BottomRight; ?
		DigitalInput winchbottomL;
		DigitalInput winchbottomR;
		DigitalInput winchtopR;
		Solenoid out;
		Solenoid in;
		//Gyro gyro;
		//I2C i2c;
		Talon winch;
		PowerDistributionPanel pdp;
		Compressor pneumatics;
		int autoLoopCounter;
		boolean camLEDs = false;
		boolean movingToTote = true;
		
		//Constants
		NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(98, 0);	//Default hue range for yellow tote
		NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(88, 255);	//Default saturation range for yellow tote
		NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(134, 255);	//Default value range for yellow tote
		double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
		double LONG_RATIO = 2.22; //Tote long side = 26.9 / Tote height = 12.1 = 2.22
		double SHORT_RATIO = 1.4; //Tote short side = 16.9 / Tote height = 12.1 = 1.4
		double SCORE_MIN = 75.0;  //Minimum score to be considered a tote
		double VIEW_ANGLE = 49.4; //View angle fo camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
		NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
		NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);

		public void robotInit() {
			// create images
			frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
			binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
			criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);
			
			//camera stuff
			camera = new AxisCamera("10.21.44.11");
			cameraX = new Servo(9);
	    	cameraY = new Servo(8);
	    	spike = new Relay(0);
	    	//stick2 = new Joystick(1);
	    	
	    	//init drive code
	    	myRobot = new RobotDrive(0,1,2,3);//2:Green, 3:Pink, 0:Blue, 1:Orange
	    	stick = new Joystick(0);
	    	stick2 = new Joystick(1);
	    	winchtopL = new DigitalInput(0);
	    	winchtopR = new DigitalInput(2);
	    	winchbottomL = new DigitalInput(1);
	    	winchbottomR = new DigitalInput(3);
	    	pdp = new PowerDistributionPanel();
	    	pneumatics = new Compressor();
	    	out = new Solenoid(0);
	    	in = new Solenoid(1);
	    	winch = new Talon(4);
	    	//gyro = new Gyro(0);
	    	myRobot.setInvertedMotor(RobotDrive.MotorType.kRearLeft,true);
	    	myRobot.setInvertedMotor(RobotDrive.MotorType.kFrontLeft,true);
	    	//i2c = new I2C(I2C.Port.kOnboard, 168);
	    	pdp.clearStickyFaults();
	    	pneumatics.clearAllPCMStickyFaults();
	    	//winch.changeControlMode(CANTalon.ControlMode.PercentVbus);
	    	//winch.enableControl();
			
	    	
			//Put default values to SmartDashboard so fields will appear
			SmartDashboard.putNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
			SmartDashboard.putNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
			SmartDashboard.putNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
			SmartDashboard.putNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
			SmartDashboard.putNumber("Tote val min", TOTE_VAL_RANGE.minValue);
			SmartDashboard.putNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
			SmartDashboard.putNumber("Area min %", AREA_MINIMUM);
		}

		public void autonomous() {
			while (isAutonomous() && isEnabled())
			{
				//set camera to center
				//cameraXPos = 111;
				//cameraYPos = 87;
				//camLEDs = true;
				
				cameraX.setAngle(cameraXPos);
		    	cameraY.setAngle(cameraYPos);
		    	spike.set(Relay.Value.kForward);
		    	
		    	int CAMERA_RES_X = getCameraXResolution(camera);
		    	int CAMERA_RES_Y = getCameraYResolution(camera);
				
				//read file in from disk. For this example to run you need to copy image.jpg from the SampleImages folder to the
				//directory shown below using FTP or SFTP: http://wpilib.screenstepslive.com/s/4485/m/24166/l/282299-roborio-ftp
				camera.getImage(frame);
				System.out.println("getImage Done");
				
				//Update threshold values from SmartDashboard. For performance reasons it is recommended to remove this after calibration is finished.
				TOTE_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
				TOTE_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
				TOTE_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
				TOTE_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
				TOTE_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Tote val min", TOTE_VAL_RANGE.minValue);
				TOTE_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote val max", TOTE_VAL_RANGE.maxValue);

				//Threshold the image looking for yellow (tote color)
				NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE, TOTE_VAL_RANGE);
				System.out.println("Img Threshold Done");
				
				//Send particle count to dashboard
				int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
				SmartDashboard.putNumber("Masked particles", numParticles);
				System.out.println("Particles" + numParticles);
				
				//Send masked image to dashboard to assist in tweaking mask.
				CameraServer.getInstance().setImage(binaryFrame);

				//filter out small particles
				float areaMin = (float)SmartDashboard.getNumber("Area min %", AREA_MINIMUM);
				criteria[0].lower = areaMin;
				imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);
				System.out.println(areaMin);
				
				//Send particle count after filtering to dashboard
				numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
				SmartDashboard.putNumber("Filtered particles", numParticles);

				if(numParticles > 0)
				{
					System.out.println("Entered If");
					//Measure particles and sort by particle size
					Vector<ParticleReport> particles = new Vector<ParticleReport>();
					for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
					{
						ParticleReport par = new ParticleReport();
						par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
						par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
						par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
						par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
						par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
						par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
						par.CenterofMassX = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
						par.CenterofMassY = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
						System.out.println(particleIndex);
						particles.add(par);
						System.out.println("particle done");
					}
					particles.sort(null);
					System.out.println("Img Analysis Done");
					
					
					Vector<Integer> toteParticles = new Vector<Integer>();
					//This example only scores the largest particle. Extending to score all particles and choosing the desired one is left as an exercise
					//for the reader. Note that this scores and reports information about a single particle (single L shaped target). To get accurate information 
					//about the location of the tote (not just the distance) you will need to correlate two adjacent targets in order to find the true center of the tote.
					for(int particleNum = 0; particleNum < numParticles; particleNum++){
						Aspect = AspectScore(particles.elementAt(particleNum));
						SmartDashboard.putNumber("Aspect" + particleNum, Aspect);
						Area = AreaScore(particles.elementAt(particleNum));
						SmartDashboard.putNumber("Area" + particleNum, Area);
						
						if(Aspect > SCORE_MIN && Area > SCORE_MIN){
							toteParticles.add(toteParticles.size(), particleNum);
						}
						else{
							
						}
						
					}
					
					if(toteParticles.size() > 1){
						toteFound = true;
					}
					else{
						toteFound = false;
					}
					
					if(toteFound){
						toteX = ToteXPos(particles.elementAt((int) toteParticles.elementAt(0)),particles.elementAt((int) toteParticles.elementAt(1)));
						SmartDashboard.putNumber("X", toteX);
						toteY = ToteYPos(particles.elementAt((int) toteParticles.elementAt(0)),particles.elementAt((int) toteParticles.elementAt(1)));
						SmartDashboard.putNumber("Y", toteY);
						distancetoTote = computeDistance(binaryFrame, particles.elementAt((int) toteParticles.elementAt(0)));
						SmartDashboard.putNumber("Distance", distancetoTote);

						
					}
					
					
					
					
					//Send distance and tote status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a tote
					SmartDashboard.putBoolean("IsTote", toteFound);
					
				} else {
					SmartDashboard.putBoolean("IsTote", false);
				}

				if(toteFound){//camera tracking code
					if(toteX > CAMERA_RES_X/2){
						goingRight = true;
						cameraXPos++;
					}
					else if(toteX < CAMERA_RES_X/2){
						goingRight = false;
						cameraXPos--;
					}
					if(toteY > CAMERA_RES_Y/2){
						cameraYPos--;
					}
					else if(toteY < CAMERA_RES_Y/2){
						cameraYPos++;
					}
				}
				else{//scan for tote
					cameraYPos = 87;
					cameraXPos = 111;
					myRobot.tankDrive(0.3, -0.3);
					/*if(goingRight){
						if(cameraXPos >= 160){
							goingRight = false;
						}
						else{
							cameraXPos = cameraXPos+5;
						}
					}
					else{
						if(cameraXPos <= 10){
							goingRight = true;
						}
						else{
							cameraXPos = cameraXPos-5;
						}
					}*/
					
				}
				
				if(toteFound && movingToTote){//robot drive code
					if(cameraXPos < 91){
						myRobot.tankDrive(0.1, 0.3);
					}
					else if(cameraXPos > 131){
						myRobot.tankDrive(0.3,0.1);
					}
					else{
						myRobot.tankDrive(0.3, 0.3);
					}
				}
				
				if(toteFound && distancetoTote < 2){
					//movingToTote = false;
					System.out.println("INSERT TOTE PICKUP CODE");
				}
				Timer.delay(0.005);				// wait for a motor update time
			}//end auto
		}

		public void operatorControl() {
			while(isOperatorControl() && isEnabled()) {
				Timer.delay(0.005);				// wait for a motor update time
			}
		}

		//Comparator function for sorting particles. Returns true if particle 1 is larger
		static boolean CompareParticleSizes(ParticleReport particle1, ParticleReport particle2)
		{
			//we want descending sort order
			return particle1.PercentAreaToImageArea > particle2.PercentAreaToImageArea;
		}

		/**
		 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
		 * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
		 */
		double ratioToScore(double ratio)
		{
			return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
		}

		double AreaScore(ParticleReport report)
		{
			double boundingArea = (report.BoundingRectBottom - report.BoundingRectTop) * (report.BoundingRectRight - report.BoundingRectLeft);
			//Tape is 7" edge so 49" bounding rect. With 2" wide tape it covers 24" of the rect.
			return ratioToScore((49/24)*report.Area/boundingArea);
		}

		/**
		 * Method to score if the aspect ratio of the particle appears to match the retro-reflective target. Target is 7"x7" so aspect should be 1
		 */
		double AspectScore(ParticleReport report)
		{
			return ratioToScore(((report.BoundingRectRight-report.BoundingRectLeft)/(report.BoundingRectBottom-report.BoundingRectTop)));
		}
		
		/**
		 * Returns the X position of the largest particle.
		 * @author Team2144
		 **/
		double ToteXPos(ParticleReport report1,ParticleReport report2)
		{
			return (report1.CenterofMassX+report2.CenterofMassX)/2;
		}
		
		/**
		 *  Returns the Y position of the largest particle.
		 *  @author Team2144
		 */
		double ToteYPos(ParticleReport report1,ParticleReport report2)
		{
			return (report1.CenterofMassY+report2.CenterofMassY)/2;
		}

		/**
		 * Computes the estimated distance to a target using the width of the particle in the image. For more information and graphics
		 * showing the math behind this approach see the Vision Processing section of the ScreenStepsLive documentation.
		 *
		 * @param image The image to use for measuring the particle estimated rectangle
		 * @param report The Particle Analysis Report for the particle
		 * @param isLong Boolean indicating if the target is believed to be the long side of a tote
		 * @return The estimated distance to the target in feet.
		 */
		double computeDistance (Image image, ParticleReport report) {
			double normalizedWidth, targetWidth;
			NIVision.GetImageSizeResult size;

			size = NIVision.imaqGetImageSize(image);
			normalizedWidth = 2*(report.BoundingRectRight - report.BoundingRectLeft)/size.width;
			targetWidth = 7;

			return  targetWidth/(normalizedWidth*12*Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
		}
		
		int getCameraXResolution(AxisCamera cam){
			switch(cam.getResolution()){
			case k160x120:
				return 160;
			case k176x144:
				return 176;
			case k240x180:
				return 240;
			case k320x240:
				return 320;
			case k480x360:
				return 480;
			case k640x480:
				return 640;
			default:
				System.out.println("Unknown Resolution");
				return 0;
			
			}
		}
		
		int getCameraYResolution(AxisCamera cam){
			switch(cam.getResolution()){
			case k160x120:
				return 120;
			case k176x144:
				return 144;
			case k240x180:
				return 180;
			case k320x240:
				return 240;
			case k480x360:
				return 360;
			case k640x480:
				return 480;
			default:
				System.out.println("Unknown Resolution");
				return 0;
			
			}
		}
}
