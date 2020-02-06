package frc.jwood;

import frc.jwood.controls.DriverController;
import frc.jwood.controls.DriverController.Action;
import frc.jwood.shuffleboard.MainShuffleboard;

public class MyRobot
{
    private MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();
    private DriverController driverController = DriverController.getInstance();

    private boolean isPreAutonomous = true;
    
    public void myRobot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** JWOOD's Test Code ***");

        System.out.println(this.getClass().getName() + " : Finished Constructor");
    }

    public void robotInit()
    {

    }

    public void robotPeriodic()
    {

    }

    public void autonomousInit()
    {
        isPreAutonomous = false;
    }

    public void autonomousPeriodic()
    {

    }

    public void teleopInit()
    {
        mainShuffleboard.setDriverControllerSettings();
    }

    public void teleopPeriodic()
    {
        System.out.println(driverController.getRawAxis(0));
        driverController.getAxis(Action.kMove);
        driverController.getButton(Action.kIntake);
    }

    public void testInit()
    {
        isPreAutonomous = true;
    }

    public void testPeriodic()
    {

    }

    public void disabledInit()
    {

    }

    public void disabledPeriodic()
    {
        if(isPreAutonomous)
        {
            mainShuffleboard.checkForNewAutonomousTabData();
        }
    }

}