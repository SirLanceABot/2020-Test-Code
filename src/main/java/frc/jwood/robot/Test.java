package frc.jwood.robot;

public class Test
{
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

    }

    /**
     * Call this method from testPeriodic() method in the Robot class
     */
    public void periodic()
    {

    }

}