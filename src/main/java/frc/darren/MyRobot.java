package frc.darren;

import edu.wpi.first.wpilibj.Timer;

public class MyRobot
{
    private static Wrist wrist = Wrist.getInstance();
    private static Xbox xbox = new Xbox(0);
    
    double newTimeOut = 1.0;

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
        System.out.println("***Synchronizing pneumatics***");
        System.out.println("Force lowering wrist");
        wrist.forceLower();
        Timer.delay(1.0);
        System.out.println("Force raising wrist");
        wrist.forceRaise();
    }

    public void teleopPeriodic()
    {
        if(xbox.getRawButton(Xbox.Button.kA))
        {
            System.out.println("Input button A");
            System.out.println("Wrist lowering");
            wrist.lower();
        }
        else if(xbox.getRawButton(Xbox.Button.kB))
        {
            System.out.println("Input button B");
            System.out.println("Wrist raising");
            wrist.raise();
        }
        else if(xbox.getRawButton(Xbox.Button.kX))
        {
            System.out.println("Input button X");
            System.out.println("Force lowering wrist");
            wrist.forceLower();
        }
        else if(xbox.getRawButton(Xbox.Button.kY))
        {
            System.out.println("Input button Y");
            System.out.println("Force raising wrist");
            wrist.forceRaise();
        }
        else if(xbox.getRawButton(Xbox.Button.kStart))
        {
            if(newTimeOut == 10.0)
            {
                System.out.println("Time out upper limit reached at 10.0s");
            }
            else
            {
                newTimeOut = newTimeOut + 0.5;
                System.out.println("Increasing the new time out to " + Double.toString(newTimeOut) + "s");
            }
        }
        else if(xbox.getRawButton(Xbox.Button.kBack))
        {
            if(newTimeOut == 1.0)
            {
                System.out.println("Time out lower limit reached at 1.0s");
            }
            else
            {
                newTimeOut = newTimeOut - 0.5;
                System.out.println("Decreasing the new time out to " + Double.toString(newTimeOut) + "s");
            }
        }
        else if(xbox.getRawButton(Xbox.Button.kLB))
        {
            System.out.println("Setting new lowering time out at " + Double.toString(newTimeOut) + "s");
            wrist.setLoweringTimeOut(newTimeOut);
        }
        else if(xbox.getRawButton(Xbox.Button.kRB))
        {
            System.out.println("Setting new raising time out at " + Double.toString(newTimeOut) + "s");
            wrist.setRaisingTimeOut(newTimeOut);
        }

        if(wrist.isUp())
        {
            System.out.println("Arm is up");
        }
        else if(wrist.isDown())
        {
            System.out.println("Arm is down");
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