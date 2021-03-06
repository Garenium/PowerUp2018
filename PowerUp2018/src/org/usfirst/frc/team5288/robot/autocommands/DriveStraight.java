package org.usfirst.frc.team5288.robot.autocommands;

import org.usfirst.frc.team5288.robot.Robot;
import org.usfirst.frc.team5288.robot.RobotMap;

import accessories.SpartanPID;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraight extends Command {

	/*
	 * Use PID to ensure this equation holds true from encoder values:
	 * basePower = command instantiation input 
	 * L - R = 0  or (L + R)/2 = L = R
	 * PID INPUT = (L + R /2) -(L or R, doesnt matter as long as its an absolute value) 
	 * PID TARGET = 0
	 * PID output = Gain
	 * Motor output Left = basePower + Gain
	 * Motor output Right = basePower - Gain
	 */
	
	double m_basePower =  0.0;
	double error = 0;
	double gain = 0;
	SpartanPID straightPID;
    public DriveStraight(double basePower) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
		m_basePower = basePower;
    	straightPID = new SpartanPID(RobotMap.StraightP, RobotMap.StraightI, RobotMap.StraightD, RobotMap.StraightFF); //
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	straightPID = new SpartanPID(RobotMap.StraightP, RobotMap.StraightI, RobotMap.StraightD, RobotMap.StraightFF);
    	straightPID.setTarget(0); 
    	System.out.println(m_basePower);
    	Robot.drivetrain.resetEncoders();     }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	error = Robot.drivetrain.getLeftDistanceInches() - Robot.drivetrain.getRightDistanceInches();
    	System.out.println("Error: " + error);
    	System.out.println("Encoder values(Left,Right): "+ Robot.drivetrain.getLeftDistanceInches() + ", " + Robot.drivetrain.getRightDistanceInches());
    	straightPID.update(error);
    	gain = straightPID.getOutput();
    	Robot.drivetrain.setLPower(-m_basePower - gain);
    	Robot.drivetrain.setRPower(-m_basePower + gain);

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
