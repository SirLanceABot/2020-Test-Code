package frc.jwood.controls;

/**
 * The DriverController class used to control the robot
 */
public class DriverController extends Xbox
{    
    public enum Action
    {
        kMove(Axis.kLeftY.value), 
        kRotate(Axis.kRightX.value), 
        kIntake(Button.kA.value);

        public final int value;

        private Action(int value)
        {
            this.value = value;
        }
    }

    private static final DriverController instance = new DriverController(0);

    private DriverController(int port)
    {
        super(port);
        System.out.println(this.getClass().getName() + " : Started Constructor");

        // Initialize all of the settings for each axis
        setAxisSettings(Axis.kLeftX, 0.2, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kLeftY, 0.1, 0.0, 1.0, true, AxisScale.kLinear);
        setAxisSettings(Axis.kLeftTrigger, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kRightTrigger, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kRightX, 0.2, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kRightY, 0.1, 0.0, 1.0, true, AxisScale.kLinear);

        System.out.println(this.getClass().getName() + " : Finished Constructor");
    }

    public static DriverController getInstance()
    {
        return instance;
    }

    public boolean getButton(Action action)
    {
        return getRawButton(action.value);
    }

    public double getAxis(Action action)
    {
        return getRawAxis(action.value);
    }
}