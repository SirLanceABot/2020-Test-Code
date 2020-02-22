package frc.annika;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.Joystick;

public class MyRobot
{
    private static Roller roller = Roller.getInstance();
    private static Joystick joystick = new Joystick(0);

    public void myRobot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** ANNIKA's Test Code ***");

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
        testRoller();
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

    public void testRoller()
    {
        if(joystick.getRawButton(1))
        {
            roller.intake();
        }
        else if(joystick.getRawButton(2))
        {
            roller.eject();
        }
        else
        {
            roller.stop();
        }
    }
}