package com.team871.commands;

import com.team871.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveCommand extends CommandBase {

    private final DriveTrain driveTrain;
    private final XboxController xboxController;
    public DriveCommand(DriveTrain driveTrain, XboxController xboxController){
        super();
        this.driveTrain = driveTrain;
        this.xboxController = xboxController;
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        super.execute();
        driveTrain.driveMecanum(xboxController.getLeftY(), xboxController.getLeftX(), xboxController.getRightX());
    }
}
