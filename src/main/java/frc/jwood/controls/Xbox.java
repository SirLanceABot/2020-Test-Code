package frc.jwood.controls;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Defines the Xbox controller used for driving the robot
 */
public class Xbox extends Joystick
{
    public enum Button
    {
        kA(1), kB(2), kX(3), kY(4), kLB(5), kRB(6), kBack(7), kStart(8), kLeftStick(9), kRightStick(10);

        public final int value;

        private Button(int value)
        {
            this.value = value;
        }
    }

    public enum Axis
    {
        kLeftX(0), kLeftY(1), kLeftTrigger(2), kRightTrigger(3), kRightX(4), kRightY(5);

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

    public static final int NUMBER_OF_AXES = 6;

    // set the default axis values
    private final double DEFAULT_DEADZONE = 0.1;
    private final double DEFAULT_MIN_OUTPUT = 0.0;
    private final double DEFAULT_MAX_OUTPUT = 1.0;
    private final boolean DEFAULT_IS_FLIPPED = false;
    private final AxisScale DEFAULT_AXIS_SCALE = AxisScale.kLinear;

    //store the default axis values into their own array lists
    private double[] axisDeadzone = new double[NUMBER_OF_AXES];
    private double[] axisMinOutput = new double[NUMBER_OF_AXES];
    private double[] axisMaxOutput = new double[NUMBER_OF_AXES];
    private boolean[] axisIsFlipped = new boolean[NUMBER_OF_AXES];
    private AxisScale[] axisScale = new AxisScale[NUMBER_OF_AXES];

    /**
     * Protected constructor for Xbox controller
     * @param port
     */
    protected Xbox(int port)
    {
        super(port);

        // loop to set the defaults for every axis
        for(int index = 0; index <= NUMBER_OF_AXES - 1; index++)
        {
            axisDeadzone[index] = DEFAULT_DEADZONE;
            axisMinOutput[index] = DEFAULT_MIN_OUTPUT;
            axisMaxOutput[index] = DEFAULT_MAX_OUTPUT;
            axisIsFlipped[index] = DEFAULT_IS_FLIPPED;
            axisScale[index] = DEFAULT_AXIS_SCALE;
        }
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
            double dead = axisDeadzone[axis];
            double min = axisMinOutput[axis];
            double max = axisMaxOutput[axis];
            double sign = Math.signum(value);

            switch(axisScale[axis])
            {
                case kLinear:
                    value = (max - min) / (1.0 - dead) * (value - dead * sign) + min * sign;
                    //value = (axisMaxOutput[axis] - axisMinOutput[axis]) / (1.0 - axisDeadzone[axis]) * (value - axisDeadzone[axis] * Math.signum(value)) + axisMinOutput[axis] * Math.signum(value);
                    break;
                case kSquared:
                    value = (max - min) * sign / Math.pow(1.0 - dead, 2) * Math.pow(value - dead, 2) + min * sign;
                    //value = (axisMaxOutput[axis] - axisMinOutput[axis]) * Math.signum(value) / Math.pow((1.0 - axisDeadzone[axis]), 2) * Math.pow((value - axisDeadzone[axis] * Math.signum(value)), 2);
                    break;
                case kCubed:
                    value = (max - min) / Math.pow(1.0 - dead, 3) * Math.pow(value - dead * sign, 3) + min * sign;
                    //value = (axisMaxOutput[axis] - axisMinOutput[axis]) / Math.pow((1.0 - axisDeadzone[axis]), 3) * Math.pow((value - axisDeadzone[axis] * Math.signum(value)), 3) + axisMinOutput[axis] * Math.signum(value);
                    break;
            }
        }
        return value;
    }
    
    public double getRawAxis(Axis axis)
    {
        return getRawAxis(axis.value);
    }

    public boolean getRawButton(Button button)
    {
        return super.getRawButton(button.value);
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

        this.axisMaxOutput[axis.value] = axisMinOutput;
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

    public void setAxisSettings(Axis axis, double axisDeadzone, double axisMinOutput, double axisMaxOutput, boolean axisIsFlipped, AxisScale axisScale)
    {
        setAxisDeadzone(axis, axisDeadzone);
        setAxisMinOutput(axis, axisMinOutput);
        setAxisMaxOutput(axis, axisMaxOutput);
        setAxisIsFlipped(axis, axisIsFlipped);
        setAxisScale(axis, axisScale);
    }

    public void setAxisSettings(Axis axis, AxisSettings axisSettings)
    {
        setAxisDeadzone(axis, axisSettings.axisDeadzone);
        setAxisMinOutput(axis, axisSettings.axisMinOutput);
        setAxisMaxOutput(axis, axisSettings.axisMaxOutput);
        setAxisIsFlipped(axis, axisSettings.axisIsFlipped);
        setAxisScale(axis, axisSettings.axisScale);
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



    // @Override
    // public String toString()
    // {
    //     String str = "";

    //     for (int index = 1; index <= 10; index++)
    //     { 
    //         if (getRawButton(index)) 
    //             str = str + "1 ";
    //         else
    //             str = str + "0 ";
    //     }

    //     for (int index = 0; index <= 5; index++)
    //     {
    //         str = str + getRawAxis(index);
    //     }

    //     return str;
    // }
    
}