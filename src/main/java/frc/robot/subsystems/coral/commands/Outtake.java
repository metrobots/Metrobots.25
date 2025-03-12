package frc.robot.subsystems.coral.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.coral.Coral;

public class Outtake extends Command {
    private final Coral coral;
    private final double speed;
    
    /**
     * Command to move the Coral subsystem forward, without all the checks of the Intake command.
     * 
     * @param coral The Coral subsystem
     * @param speed The speed at which to move the wheels
     */
    public Outtake(Coral coral, double speed) {
        this.coral = coral;
        this.speed = speed;
        addRequirements(coral);
    }
    
    @Override
    public void initialize() {
        // Start moving forwards
        coral.forward(speed);
    }
    
    @Override
    public void execute() {
        // Command runs continuously until interrupted
    }
    
    @Override
    public void end(boolean interrupted) {
        // Stop motors
        coral.stop();
    }
    
    @Override
    public boolean isFinished() {
        // This command runs until interrupted
        return false;
    }
}