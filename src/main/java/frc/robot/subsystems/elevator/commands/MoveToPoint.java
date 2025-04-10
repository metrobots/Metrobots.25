package frc.robot.subsystems.elevator.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.Elevator.ElevatorState;

public class MoveToPoint extends Command {
    private final Elevator elevator;
    private final double targetPosition;
    private final Command nextCommand;
    private final double proximityThreshold;
    private final BooleanSupplier buttonSupplier;
    private boolean nextCommandScheduled = false;
    private boolean buttonWasHeldAtStart = false;

    // Original constructor for backward compatibility
    public MoveToPoint(Elevator elevator, double targetPosition) {
        this(elevator, targetPosition, null, 0.0, () -> false);
    }
    
    // Constructor with next command
    public MoveToPoint(Elevator elevator, double targetPosition, Command nextCommand) {
        this(elevator, targetPosition, nextCommand, 3.0, () -> false);
    }
    
    // Constructor with next command and button condition
    public MoveToPoint(Elevator elevator, double targetPosition, Command nextCommand, BooleanSupplier buttonSupplier) {
        this(elevator, targetPosition, nextCommand, 3.0, buttonSupplier);
    }
    
    // Full constructor with configurable threshold
    public MoveToPoint(Elevator elevator, double targetPosition, Command nextCommand, double proximityThreshold) {
        this(elevator, targetPosition, nextCommand, proximityThreshold, () -> false);
    }
    
    // Complete constructor with all parameters
    public MoveToPoint(Elevator elevator, double targetPosition, Command nextCommand, double proximityThreshold, BooleanSupplier buttonSupplier) {
        this.elevator = elevator;
        this.targetPosition = targetPosition;
        this.nextCommand = nextCommand != null ? nextCommand.withTimeout(0.5) : null; // Add 0.5-second timeout to next command
        this.proximityThreshold = proximityThreshold;
        this.buttonSupplier = buttonSupplier;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setPosition(targetPosition);
        nextCommandScheduled = false;
        // Check if the button is held when the command starts
        buttonWasHeldAtStart = buttonSupplier.getAsBoolean();
    }

    @Override
    public void execute() {
        // Check if we're close enough to the target but haven't scheduled the next command yet
        if (!nextCommandScheduled && nextCommand != null && buttonWasHeldAtStart) {
            double currentPosition = elevator.getPosition();
            double distanceToTarget = Math.abs(currentPosition - targetPosition);
            
            if (distanceToTarget <= proximityThreshold) {
                CommandScheduler.getInstance().schedule(nextCommand);
                nextCommandScheduled = true;
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            elevator.stop();
        }
    }

    @Override
    public boolean isFinished() {
        return elevator.getCurrentState() == ElevatorState.AT_SETPOINT;
    }
}