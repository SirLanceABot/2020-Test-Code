package frc.jwood.robot;

public class Autonomous
{
    private static Autonomous instance = new Autonomous();

    /**
     * The constructor for the Autonomous class. 
     */
    private Autonomous()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    /**
     * The method to retrieve the instance of Autonomous.
     * @return instance 
     */
    public static Autonomous getInstance()
    {
        return instance;
    }

    /**
     * Call this method from autonomousInit() method in the Robot class
     */
    public void init()
    {

    }

    /**
     * Call this method from autonomousPeriodic() method in the Robot class
     */
    public void periodic()
    {

    }

}