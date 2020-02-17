package frc.jwood.robot;

import frc.jwood.MyRobot;
import frc.jwood.shuffleboard.MainShuffleboard;

public class Disabled
{
    private MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();

    private static Disabled instance = new Disabled();

    /**
     * The constructor for the Disabled class. 
     */
    private Disabled()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    /**
     * The method to retrieve the instance of Disabled.
     * @return instance 
     */
    public static Disabled getInstance()
    {
        return instance;
    }

    /**
     * Call this method from disabledInit() method in the Robot class
     */
    public void init()
    {

    }

    /**
     * Call this method from disabledPeriodic() method in the Robot class
     */
    public void periodic()
    {
        if(MyRobot.getRobotState() == MyRobot.RobotState.kBeforeGame)
        {
            mainShuffleboard.checkForNewAutonomousTabData();
        }
    }

}