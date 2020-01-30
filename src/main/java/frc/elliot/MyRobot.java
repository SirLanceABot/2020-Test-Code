package frc.elliot;

import frc.elliot.components.Intake;
import frc.elliot.components.Roller;
import frc.elliot.controls.DriverController;
import frc.elliot.controls.Xbox;

public class MyRobot
{
    //private static ShooterTest shooter = new ShooterTest();
    //private static Roller roller = Roller.getInstance();
    //private static DriverController driverController = DriverController.getInstance();
    private static Intake intake = Intake.getInstance();
    public void myRobot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** ELLIOT's Test Code ***");

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

    }

    public void autonomousPeriodic()
    {

    }

    public void teleopInit()
    {

    }

    public void teleopPeriodic()
    {
        //shooter.teleopPeriodic();
        // if (xbox.getRawButton(Xbox.Button.kA))
        //     roller.intake();
        // else if (xbox.getRawButton(Xbox.Button.kB))
        //     roller.eject();
        // else
        //     roller.stop();

        // if (driverController.getRawButton(Xbox.Button.kA))
        // {

        // }

        intake.runFSM();
        
    }

    public void testInit()
    {

    }

    public void testPeriodic()
    {

    }

    public void disabledInit()
    {

    }

    public void disabledPeriodic()
    {

    }

}