package frc.jwood.controls;

public class DriverController extends Xbox
{    
    public class AxisSettings
    {
        public double axisDeadzone;
        public double axisMaxOutput;
        public boolean axisIsFlipped;
        public AxisScale axisScale;

        public AxisSettings()
        {
            // Call the other constructor with the default values
            this(0.1, 1.0, false, AxisScale.kLinear);
        }

        public AxisSettings(double deadzone, double maxOutput, boolean isFlipped, AxisScale axisScale)
        {
            this.axisDeadzone = deadzone;
            this.axisMaxOutput = maxOutput;
            this.axisIsFlipped = isFlipped;
            this.axisScale = axisScale;
        }

        @Override
        public String toString()
        {
            String str = "";

            str += "Deadzone   : " + axisDeadzone + "\n";
            str += "Max Output : " + axisMaxOutput + "\n";
            str += "Is Flipped : " + axisIsFlipped + "\n"; 
            str += "Axis Scale : " + axisScale + "\n";

            return str;
        }
    }   

    private AxisSettings[] axisSettings = new AxisSettings[6];

    private static final DriverController instance = new DriverController(0);

    private DriverController(int port)
    {
        super(port);
        System.out.println(this.getClass().getName() + " : Started Constructor");

        axisSettings[Axis.kLeftX.value] = new AxisSettings(0.1, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kLeftX);

        System.out.println(this.getClass().getName() + " : Finished Constructor");
    }

    public static DriverController getInstance()
    {
        return instance;
    }

    private void setAxisSettings(Axis axis)
    {
        setAxisDeadzone(axis, axisSettings[axis.value].axisDeadzone);
        setAxisMaxOutput(axis, axisSettings[axis.value].axisMaxOutput);
        setAxisIsFlipped(axis, axisSettings[axis.value].axisIsFlipped);
        setAxisScale(axis, axisSettings[axis.value].axisScale);
    }

    public void setAxisSettings(Axis axis, AxisSettings axisSettings)
    {
        this.axisSettings[axis.value].axisDeadzone = axisSettings.axisDeadzone;
        this.axisSettings[axis.value].axisMaxOutput = axisSettings.axisMaxOutput;
        this.axisSettings[axis.value].axisIsFlipped = axisSettings.axisIsFlipped;
        this.axisSettings[axis.value].axisScale = axisSettings.axisScale;

        setAxisSettings(axis);
    }

    public AxisSettings getAxisSettings(Axis axis)
    {
        return axisSettings[axis.value];
    }
}