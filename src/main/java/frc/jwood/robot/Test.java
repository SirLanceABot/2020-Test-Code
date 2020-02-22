package frc.jwood.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.jwood.components.Shroud;

public class Test
{
    private static Shroud shroud = Shroud.getInstance();
    private static Timer timer = new Timer();
    private static Test instance = new Test();

    /**
     * The constructor for the Test class. 
     */
    private Test()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    /**
     * The method to retrieve the instance of Test.
     * @return instance 
     */
    public static Test getInstance()
    {
        return instance;
    }

    /**
     * Call this method from testInit() method in the Robot class
     */
    public void init()
    {
        shroud.resetCounter();
        shroud.resetPosition();
        timer.reset();
        timer.start();
    }

    /**
     * Call this method from testPeriodic() method in the Robot class
     */
    public void periodic()
    {
        if(timer.get() < 2.0)
        {
            shroud.setSpeed(0.2);
        }
        else if(timer.get() < 4.0)
        {
            shroud.setSpeed(0.0);
        }
        else if(timer.get() < 6.0)
        {
            shroud.setSpeed(-0.2);
        }
        else
        {
            shroud.setSpeed(0.0);
            timer.stop();
        }
    }

}