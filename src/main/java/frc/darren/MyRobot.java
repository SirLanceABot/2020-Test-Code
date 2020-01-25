package frc.darren;

import edu.wpi.first.wpilibj.Timer;
import frc.darren.Xbox.Button;

public class MyRobot
{
    private static Wrist wrist = Wrist.getInstance();
    private static Xbox xbox = new Xbox(0);

    public void robot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** DARREN's Test Code ***");

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
        System.out.println("Synchronizing pneumatics");
        wrist.lower();
        Timer.delay(1.0);
        wrist.raise();
    }

    public void teleopPeriodic()
    {
        if(xbox.getRawButton(Xbox.Button.kA))
        {
            wrist.lower();
        }
        else if(xbox.getRawButton(Xbox.Button.kB))
        {
            wrist.raise();
        }

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