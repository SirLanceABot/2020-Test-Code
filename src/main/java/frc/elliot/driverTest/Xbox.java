package frc.elliot.driverTest;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Defines the Xbox controller used for driving the robot
 * @author Elliot Measel
 */
public class Xbox extends Joystick
{
    private static final String className = new String("[Xbox]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    public enum Axis
    {
        kLeftY(1),  kRightX(4);

        public final int value;

        private Axis(int value)
        {
            this.value = value;
        }
    }

    public enum AxisScale
    {
        kLinear, kSquared, kCubed;
    }

    public class AxisSettings 
    {
        public double axisDeadzone;
        public double axisMinOutput;
        public double axisMaxOutput;
        public boolean axisIsFlipped;
        public AxisScale axisScale;
    }

    // set the default axis values
    private final double DEADZONE = 0.1;
    private final double MIN_OUTPUT = 0.0;
    private final double MAX_OUTPUT = 0.3;
    private final boolean IS_FLIPPED = false;
    private final AxisScale AXIS_SCALE = AxisScale.kLinear;

    //store the default axis values into their own array lists
    public double[] axisDeadzone = new double[6];
    private double[] axisMinOutput = new double[6];
    private double[] axisMaxOutput = new double[6];
    private boolean[] axisIsFlipped = new boolean[6];
    private AxisScale[] axisScale = new AxisScale[6];

    private static Xbox instance = new Xbox(0);

    /**
     * Protected constructor for Xbox controller
     * @param port
     */
    private Xbox(int port)
    {
        super(port);

        System.out.println(className + " : Constructor Started");

        // loop to set the defaults for every axis
        for(int index = 0; index <= 5; index++)
        {
            axisDeadzone[index] = DEADZONE;
            axisMinOutput[index] = MIN_OUTPUT;
            axisMaxOutput[index] = MAX_OUTPUT;
            axisIsFlipped[index] = IS_FLIPPED;
            axisScale[index] = AXIS_SCALE;
        }

        System.out.println(className + ": Constructor Finished");
    }

    public static Xbox getInstance()
    {
        return instance;
    }

   
    @Override
    /**
     * Public method to get the value of an axis
     * @param axis
     */
    public double getRawAxis(int axis)
    {
        double value = super.getRawAxis(axis);

        if(axisIsFlipped[axis])
        {
            value *= -1;
        }
        if(Math.abs(value) <= axisDeadzone[axis])
        {
            value = 0.0;
        }
        else
        {
            switch(axisScale[axis])
            {
                case kLinear:
                    value = (axisMaxOutput[axis] - axisMinOutput[axis]) / (1.0 - axisDeadzone[axis]) * (value - axisDeadzone[axis] * Math.signum(value)) + (axisMinOutput[axis] * Math.signum(value));
                    break;
                case kSquared:
                    value = (axisMaxOutput[axis] - axisMinOutput[axis]) * Math.signum(value) / Math.pow((1.0 - axisDeadzone[axis]), 2) * Math.pow((value - axisDeadzone[axis] * Math.signum(value)), 2)  + (axisMinOutput[axis] * Math.signum(value));
                    break;
                case kCubed:
                    value = (axisMaxOutput[axis] - axisMinOutput[axis]) / Math.pow((1.0 - axisDeadzone[axis]), 3) * Math.pow((value - axisDeadzone[axis] * Math.signum(value)), 3)  + (axisMinOutput[axis] * Math.signum(value));
                    break;
            }
        }
        return value;
    }
    
    public double getRawAxis(Axis axis)
    {
        return getRawAxis(axis.value);
    }

    public AxisSettings getAxisSettings(Axis axis)
    {
        AxisSettings axisSettings = new AxisSettings();

        axisSettings.axisDeadzone = axisDeadzone[axis.value];
        axisSettings.axisMinOutput = axisMinOutput[axis.value];
        axisSettings.axisMaxOutput = axisMaxOutput[axis.value];
        axisSettings.axisIsFlipped = axisIsFlipped[axis.value];
        axisSettings.axisScale = axisScale[axis.value];

        return axisSettings;
    }

    /**
     * Public method to manually set an axis deadzone
     * @param axis
     * @param axisDeadzone
     */
    public void setAxisDeadzone(Axis axis, double axisDeadzone)
    {
        axisDeadzone = Math.abs(axisDeadzone);
        axisDeadzone = Math.min(1.0, axisDeadzone);

        this.axisDeadzone[axis.value] = axisDeadzone;
    }

    /**
     * Public method to manually set an axis min output
     * @param axis
     * @param axisMinOutput
     */
    public void setAxisMinOutput(Axis axis, double axisMinOutput)
    {
        axisMinOutput = Math.abs(axisMinOutput);
        axisMinOutput = Math.min(axisMinOutput, 1.0);

        this.axisMinOutput[axis.value] = axisMinOutput;
    }

    /**
     * Public method to manually set an axis max output
     * @param axis
     * @param axisMaxOutput
     */
    public void setAxisMaxOutput(Axis axis, double axisMaxOutput)
    {
        axisMaxOutput = Math.abs(axisMaxOutput);
        axisMaxOutput = Math.min(axisMaxOutput, 1.0);

        this.axisMaxOutput[axis.value] = axisMaxOutput;
    }

    /**
     * Public method to manually set whether an axis is flipped or not
     * @param axis
     * @param axisIsFlipped
     */
    public void setAxisIsFlipped(Axis axis, boolean axisIsFlipped)
    {
        this.axisIsFlipped[axis.value] = axisIsFlipped;
    }

    /**
     * Public method to manually set the scale for an axis
     * @param axis
     * @param axisScale
     */
    public void setAxisScale(Axis axis, AxisScale axisScale)
    {
        this.axisScale[axis.value] = axisScale;
    }

    /**
     * Public method to initialize the settings for one axis
     * @param axis
     * @param axisDeadzone
     * @param axisMinOutput
     * @param axisMaxOutput
     * @param axisIsFlipped
     * @param axisScale
     */
    public void setAxisSettings(Axis axis, double axisDeadzone, double axisMinOutput, double axisMaxOutput, boolean axisIsFlipped, AxisScale axisScale)
    {
        setAxisDeadzone(axis, axisDeadzone);
        setAxisMinOutput(axis, axisMinOutput);
        setAxisMaxOutput(axis, axisMaxOutput);
        setAxisIsFlipped(axis, axisIsFlipped);
        setAxisScale(axis, axisScale);
    }

    /**
     * Public method to initialize the settings for one axis
     * @param axis
     * @param axisSettings
     */
    public void setAxisSettings(Axis axis, AxisSettings axisSettings)
    {
        setAxisSettings(axis, axisSettings.axisDeadzone, axisSettings.axisMinOutput, axisSettings.axisMaxOutput, axisSettings.axisIsFlipped, axisSettings.axisScale);
        
    }
}