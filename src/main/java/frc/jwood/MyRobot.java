package frc.jwood;

import frc.jwood.controls.DriverController;
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
        driverController.resetRumbleIndex();
    }

    public void teleopPeriodic()
    {
        double move = driverController.getAction(DriverController.AxisAction.kMove);
        boolean intake = driverController.getAction(DriverController.ButtonAction.kIntake);
        double val = driverController.getRawAxis(DriverController.Axis.kLeftX);

        driverController.checkRumbleEvents();
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