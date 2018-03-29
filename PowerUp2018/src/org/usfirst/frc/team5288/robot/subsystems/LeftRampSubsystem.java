package org.usfirst.frc.team5288.robot.subsystems;

import org.usfirst.frc.team5288.robot.Robot;
import org.usfirst.frc.team5288.robot.RobotMap;
import org.usfirst.frc.team5288.robot.subsystems.RightRampSubsystem.state;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LeftRampSubsystem extends Subsystem {
	// Define left servo angles
	private final double servoInitialAngle = 0;
	private final double servoNeutralAngle = 0;
	private final double servoPlantedAngle = 90;
	// Define ramp states
	public enum state {initial,neutral, planted};
	private state currentState;
	// Define motors
	private VictorSP lmotor1 = new VictorSP(RobotMap.LRampMotors);	//	Left ramp motors; PWM is split.
	private Servo lServo = new Servo(RobotMap.lRampServo);
	// Define limit switch
	private DigitalInput lLimitSwitch = new DigitalInput(RobotMap.LRampLimitSwitch);
    boolean limitSwitchStatus;
    boolean isOverride;
    private double rampLooseningOutput = -0.5;
    private double rampMotorOutput = 0.5;
    
    
	public LeftRampSubsystem() {
		currentState = state.initial;
	}
	public void initDefaultCommand() {
    }
    
    public void outputToLeftRamp(double power) {
    	rampMotorOutput = power;
    }
    public void updateSensors() {
		limitSwitchStatus = lLimitSwitch.get();
	}
    public void updateOutputs() {
    	if(!isOverride) {
	    	if (currentState == state.planted){
	    		if(limitSwitchStatus){
		    	lmotor1.set(rampMotorOutput);
		    	lServo.set(servoPlantedAngle);
	    		}
	    		else {
			    	lmotor1.set(0);
			    	lServo.setAngle(servoPlantedAngle);
	    		}
	    	}
	    	else if(currentState == state.neutral) {
	    		lmotor1.set(0);
		    	lServo.setAngle(servoNeutralAngle);
	    	}
	    	else if (currentState == state.initial) {
	    		lmotor1.set(0);
		    	lServo.setAngle(servoInitialAngle);
	    	}
    	}
    }
    public void setState(state newState) {
    	currentState = newState;
    }
    public state getCurrentState() {
    	return currentState;
    }
    public boolean isLimitChecked() {
    	return limitSwitchStatus;
    }
    public void setOverride(boolean leftRampOverride) {
    	isOverride = leftRampOverride;
    }
    public boolean getOverride() {
    	return isOverride;
    }
    public void setServo(double angle) {
    	lServo.setAngle(angle);
    }
    
}

