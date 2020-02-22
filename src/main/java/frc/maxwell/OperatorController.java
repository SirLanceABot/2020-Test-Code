package frc.maxwell;

public class OperatorController extends Logitech
{
    public enum ButtonAction
    {
        kShoot(Button.kTrigger);

        public final Button button;

        private ButtonAction(Button button)
        {
           this.button = button;
        }
    }

    public enum AxisAction
    {
        kMoveTurret(Axis.kZAxis),
        kMoveShroud(Axis.kYAxis);

        public final Axis axis;

        private AxisAction(Axis axis)
        {
           this.axis = axis;
        }
    }

    private static final int OPERATOR_CONTROLLER_PORT = 1;

    private static OperatorController instance = new OperatorController(OPERATOR_CONTROLLER_PORT);

    private OperatorController(int port)
    {
        super(port);
        System.out.println(this.getClass().getName() + " : constructor started");

        setAxisSettings(Axis.kXAxis, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kYAxis, 0.1, 0.0, 1.0, true, AxisScale.kLinear);
        setAxisSettings(Axis.kZAxis, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kSlider, 0.1, 0.0, 1.0, false, AxisScale.kLinear);

        System.out.println(this.getClass().getName() + " : constructor finished");
    }

    public static OperatorController getInstance()
    {
        return instance;
    }

    @Deprecated
    public boolean getRawButton(Button button)
    {
        return super.getRawButton(button);
    }

    @Deprecated
    public double getRawAxis(Axis axis)
    {
        return super.getRawAxis(axis);
    }

    public boolean getAction(ButtonAction buttonAction)
    {
        return getRawButton(buttonAction.button);
    }

    public double getAction(AxisAction axisAction)
    {
        return getRawAxis(axisAction.axis);
    }
}