package org.usfirst.frc.team5288.robot.commands.lift;

import org.usfirst.frc.team5288.robot.Robot;

import accessories.SpartanPID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ResistLiftWeight extends Command {
	
	
	SpartanPID liftResistPID;
	double initialHeight;
	double currentHeight;
	double deltaHeight;// current - initial
    public ResistLiftWeight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lift);
    	
    	liftResistPID = new SpartanPID(0.005,0,0.001,0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//double pValue = Robot.getDashboardNumber("LIFTP");
    	//double iValue = Robot.getDashboardNumber("LIFTI");
    	//double dValue = Robot.getDashboardNumber("LIFTD");
    	//liftResistPID = new SpartanPID(pValue,iValue,dValue, 0);
    	initialHeight = Robot.lift.getEncoderPosition();
    	currentHeight = initialHeight;
    	deltaHeight = 0;
    	liftResistPID.setTarget(0);
    	 
    	System.out.println("Starting ResistLiftWeight.");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentHeight = Robot.lift.getEncoderPosition();
    	deltaHeight = currentHeight - initialHeight;
    	liftResistPID.update(deltaHeight);
    	Robot.lift.outputToLift(liftResistPID.getOutput());
    	//	Robot.lift.outputToLift(0);
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
    	System.out.println("Lift weight resist interrupted.");
    	//TODO: Try too not set motors to 0 when ending a command unless necessary, adds unnecessary jitter.
    	Robot.lift.outputToLift(0);
    }
}
