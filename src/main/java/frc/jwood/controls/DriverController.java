package frc.jwood.controls;

/**
 * The DriverController class used to control the robot
 */
public class DriverController extends Xbox
{    
    /**
     * The AxisSettings class contains all of the settings for on axis on the controller
     */
    public class AxisSettings
    {
        public double axisDeadzone;
        public double axisMinOutput;
        public double axisMaxOutput;
        public boolean axisIsFlipped;
        public AxisScale axisScale;

        public AxisSettings()
        {
            // Call the other constructor with the default values
            this(0.1, 0.0, 1.0, false, AxisScale.kLinear);
        }

        public AxisSettings(double deadzone, double minOutput, double maxOutput, boolean isFlipped, AxisScale axisScale)
        {
            this.axisDeadzone = deadzone;
            this.axisMinOutput = minOutput;
            this.axisMaxOutput = maxOutput;
            this.axisIsFlipped = isFlipped;
            this.axisScale = axisScale;
        }

        @Override
        public String toString()
        {
            String str = "";

            str += "Deadzone   : " + axisDeadzone + "\n";
            str += "Min Output : " + axisMinOutput + "\n";
            str += "Max Output : " + axisMaxOutput + "\n";
            str += "Is Flipped : " + axisIsFlipped + "\n"; 
            str += "Axis Scale : " + axisScale + "\n";

            return str;
        }
    }
    
    public enum DriverButtonControls
    {
        kShoot, kClimb;
    }

    // The axisSettings array is used to hold all of the settings for all of the axes
    private AxisSettings[] axisSettings = new AxisSettings[6];

    private static final DriverController instance = new DriverController(0);

    private DriverController(int port)
    {
        super(port);
        System.out.println(this.getClass().getName() + " : Started Constructor");

        // Initialize all of the settings for each axis
        axisSettings[Axis.kLeftX.value] = new AxisSettings(0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kLeftX);

        // TODO: Initialize the other 5 axes

        System.out.println(this.getClass().getName() + " : Finished Constructor");
    }

    public static DriverController getInstance()
    {
        return instance;
    }

    /**
     * This private method will call the super class methods to modify the settings for an axis
     */
    private void setAxisSettings(Axis axis)
    {
        setAxisDeadzone(axis, axisSettings[axis.value].axisDeadzone);
        setAxisMaxOutput(axis, axisSettings[axis.value].axisMaxOutput);
        setAxisIsFlipped(axis, axisSettings[axis.value].axisIsFlipped);
        setAxisScale(axis, axisSettings[axis.value].axisScale);
    }

    /**
     * This public method will modify the array settings and then call the private method to modify the settings for an axis.
     * The axisDeadzone and axisMaxOutput will normalized between [0, 1].
     * @param axis The controller axis to modify
     * @param axisSettings The new settings for the axis
     */
    public void setAxisSettings(Axis axis, AxisSettings axisSettings)
    {
        this.axisSettings[axis.value].axisDeadzone = Math.min(Math.abs(axisSettings.axisDeadzone), 1.0);
        this.axisSettings[axis.value].axisMaxOutput = Math.min(Math.abs(axisSettings.axisMaxOutput), 1.0);
        this.axisSettings[axis.value].axisIsFlipped = axisSettings.axisIsFlipped;
        this.axisSettings[axis.value].axisScale = axisSettings.axisScale;

        setAxisSettings(axis);
    }

    /**
     * This public method will return the current settings for a particular axis
     * @param axis The controller axis
     * @return The settings for the axis parameter
     */
    public AxisSettings getAxisSettings(Axis axis)
    {
        return axisSettings[axis.value];
    }
}