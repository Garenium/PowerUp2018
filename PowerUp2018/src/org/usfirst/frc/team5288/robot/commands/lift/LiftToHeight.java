package org.usfirst.frc.team5288.robot.commands.lift;

import org.usfirst.frc.team5288.robot.Robot;
import org.usfirst.frc.team5288.robot.subsystems.Lift.liftState;

import accessories.SpartanPID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LiftToHeight extends Command {
	
	
	SpartanPID liftResistPID;
	double initialHeight;
	//double deltaHeight;// current - initial
	double wantedHeight;
	double currentHeightInches = 0;
    public LiftToHeight(double height) {

    	requires(Robot.lift);
    	liftResistPID = new SpartanPID(0.20,0,0.02,0.1);
    	wantedHeight = height;
    }

    protected void initialize() {
    	//
    	initialHeight = Robot.lift.getLiftHeight();
    	currentHeightInches = initialHeight;
    	//deltaHeight = 0;
    	liftResistPID.setTarget(wantedHeight);
    	Robot.lift.setState(liftState.PID); 
    	System.out.println("Sending");
    }

    protected void execute() {
    	currentHeightInches = Robot.lift.getLiftHeight();
    	//deltaHeight = currentHeight - initialHeight;
    	liftResistPID.update(currentHeightInches);
    	System.out.println(liftResistPID.getOutput());
    	currentHeightInches = Robot.lift.getLiftHeight();
    	if(currentHeightInches > wantedHeight + 2) {
    		Robot.lift.outputToLift(-0.4);
    	}
    	else {
        	Robot.lift.outputToLift(liftResistPID.getOutput());
    	}
    }

    protected boolean isFinished() {
    	if(currentHeightInches <= wantedHeight+1 && currentHeightInches >= wantedHeight-1) {
    		Robot.lift.setState(liftState.stopped);
    		return true;
    	}
    	else return false;
    }

    protected void end() {
    }

    protected void interrupted() {

    }
}
