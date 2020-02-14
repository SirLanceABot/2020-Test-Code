package frc.joey;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.joey.controls.DriverController;
import frc.joey.shuffleboard.MainShuffleboard;

public class MyRobot
{
    private MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();
    private DriverController driverController = DriverController.getInstance();
    private DigitalInput magnetSensor = new DigitalInput(0);

    private boolean isPreAutonomous = true;
    
    public void myRobot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** JOEY's Test Code ***");

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
        System.out.println(magnetSensor.get());
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