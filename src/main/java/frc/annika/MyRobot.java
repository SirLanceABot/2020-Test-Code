package frc.annika;

public class MyRobot
{
    private static LIDAR_Lite lidar = new LIDAR_Lite(Constants.LIDAR.PORT, Constants.LIDAR.ADDRESS);

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
        testLidar();
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

    public void testLidar()
    {
        if(lidar.IsWorking())
        {
            System.out.println("Lidar distance: " + lidar.GetDistance());
        }
        else
        {
            System.out.println("Error");
        }
    }
}