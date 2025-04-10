package frc.robot.subsystems.elevator.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;

/**
 * Command to manually control the elevator.
 */
public class MoveManual extends Command {
    private final Elevator elevator;
    private final double speed;

    /**
     * Creates a new MoveManual command.
     *
     * @param elevator The elevator subsystem used by this command.
     * @param speed The speed at which to move the elevator.
     */
    public MoveManual(Elevator elevator, double speed) {
        this.elevator = elevator;
        this.speed = speed;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        // Initialization code if needed
    }

    @Override
    public void execute() {
        elevator.manualControl(speed);
    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
    }

    @Override
    public boolean isFinished() {
        return false; // This command continues until interrupted
    }
}
