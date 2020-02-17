package frc.jwood.controls;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;
import frc.jwood.robot.Port;

/**
 * The DriverController class used to control the robot
 */
public class DriverController extends Xbox
{    
    public enum AxisAction
    {
        // kAction1(    Axis.kLeftX.value)
        kMove(       Axis.kLeftY.value),
        // kAction3(    Axis.kLeftTrigger.value),
        // kAction4(    Axis.kRightTrigger.value),
        kRotate(     Axis.kRightX.value),
        // kAction6(    Axis.kRightY.value)
        ;

        public final int value;

        private AxisAction(int value)
        {
            this.value = value;
        }
    }

    public enum ButtonAction
    {
        kIntake(    Button.kA.value),
        // kAction2(   Button.kB.value),
        // kAction3(   Button.kX.value),
        // kAction4(   Button.kY.value),
        // kAction5(   Button.kLB.value),
        // kAction6(   Button.kRB.value),
        // kAction7(   Button.kBack.value),
        // kAction8(   Button.kStart.value),
        // kAction9(   Button.kLeftStick.value),
        // kAction10(  Button.kRightStick.value)
        ;

        public final int value;

        private ButtonAction(int value)
        {
            this.value = value;
        }
    }

    public class RumbleEvent
    {
        public double startTime;
        public double duration;
        public double left;
        public double right;

        public RumbleEvent(double startTime, double duration, double left, double right)
        {
            this.startTime = startTime;
            this.duration = duration;
            this.left = left;
            this.right = right;
        }
    }

    private ArrayList<RumbleEvent> rumbleEvents = new ArrayList<>();
    private int rumbleIndex = 0;

    // private double[] rumbleTimes = {60.0, 30.0, 3.0, 2.0, 1.0};
    // private double rumbleDuration = 0.25;
    // private double rumbleIntensity = 0.5;
    
    private final DriverStation dStation = DriverStation.getInstance();
    private static final DriverController instance = new DriverController(Port.Controller.DRIVER);

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

        // Put the Rumble Events in descending order by startTime
        // Need to call checkRumbleEvent() in teleopPeriodic()
        // And call resetRumbleIndex() in teleopInit()
        createRumbleEvent(60.0, 0.75, 0.5, 0.0);
        createRumbleEvent(31.0, 0.75, 0.5, 0.5);
        createRumbleEvent(30.0, 0.75, 0.5, 0.5);
        createRumbleEvent(10.0, 1.0, 0.75, 0.75);
        createRumbleEvent(5.0, 1.0, 1.0, 1.0);

        System.out.println(this.getClass().getName() + " : Finished Constructor");
    }

    public static DriverController getInstance()
    {
        return instance;
    }

    @Deprecated
    public double getRawAxis(Axis axis)
    {
        return getRawAxis(axis.value);
    }

    public double getAction(AxisAction action)
    {
        return getRawAxis(action.value);
    }

    public boolean getAction(ButtonAction action)
    {
        return getRawButton(action.value);
    }

    private void createRumbleEvent(double startTime, double duration, double leftSide, double rightSide)
    {
        rumbleEvents.add(new RumbleEvent(startTime, duration, leftSide, rightSide));
    }

    public void checkRumbleEvents()
    {
        if(rumbleIndex < rumbleEvents.size())
        {
            double matchTime = dStation.getMatchTime();
            double startTime = rumbleEvents.get(rumbleIndex).startTime;
            double duration = rumbleEvents.get(rumbleIndex).duration;
            double left = rumbleEvents.get(rumbleIndex).left;
            double right = rumbleEvents.get(rumbleIndex).right;

            if(startTime >= matchTime && matchTime >= startTime - duration)
            {
                setRumble(RumbleType.kLeftRumble, left);
                setRumble(RumbleType.kRightRumble, right);
            }
            else if(startTime - duration > matchTime)
            {
                rumbleIndex++;
                setRumble(RumbleType.kLeftRumble, 0.0);
                setRumble(RumbleType.kRightRumble, 0.0);
            }
            else
            {
                setRumble(RumbleType.kLeftRumble, 0.0);
                setRumble(RumbleType.kRightRumble, 0.0);
            }
        }
    }

    public void resetRumbleIndex()
    {
        rumbleIndex = 0;
    }
}