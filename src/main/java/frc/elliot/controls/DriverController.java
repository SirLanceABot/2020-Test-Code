package frc.elliot.controls;

/**
 * Customized Xbox instance used for driving the robot
 * @author Elliot Measel
 */
public class DriverController extends Xbox
{
    // the one and only instance of driver controller
    private static DriverController driverController = new DriverController(); 

    /**
     * Private constructor for driver controller
     */
    private DriverController()
    {
        super(0);
        System.out.println(this.getClass().getName() + " : constructor started");
        setAxisIsFlipped(Xbox.Axis.kLeftY, true);
        setAxisIsFlipped(Xbox.Axis.kRightY, true);
        System.out.println(this.getClass().getName() + " : constructor finished");
    }

    /**
     * Public method to return the one instance of the driver controller
     * @return
     */
    public static DriverController getInstance()
    {
        return driverController;
    }
}