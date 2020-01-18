package frc.maxwell;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Class for controlling the turret that aims the shooter on the X plane
 * 0 degrees is straight ahead, -180 to the left, +180 to the right
 * @author Maxwell Li
 */
public class Turret
{
    private static TalonSRX motor = new TalonSRX(Constants.MOTOR_ID);
    private static double currentPostion;
    private static boolean isMoving = false;
    private static Turret instance = new Turret();

    private Turret()
    {

        System.out.println(this.getClass().getName() + ": Started Constructing");
        motor.configFactoryDefault();
		/* Configure talon with feedback device to double check CANifier */
        motor.configSelectedFeedbackSensor(	FeedbackDevice.QuadEncoder, 0, Constants.TIMEOUT_MS); //TODO: Find out port
        System.out.println(this.getClass().getName() + ": Finished Constructing");        
    }

    public static Turret getInstance()
    {
        return instance;
    }

    /**
     * Private function that handles all of the setting of motor speed
     * @param speed
     */
    public void setSpeed(double speed)
    {
        motor.set(ControlMode.PercentOutput, speed);
        setCurrentPosition(getEncoderPosition());
        setIsMoving(true);
    }

    /**
     * Stops the motors
     */
    public void stop()
    {
        motor.set(ControlMode.PercentOutput, 0.0);
        setCurrentPosition(getEncoderPosition());
        setIsMoving(false);
    }

    /**
     * sets the current position of the turret (ticks)
     * @param position
     */
    public void setCurrentPosition(double position)
    {
        currentPostion = position;
    }

    /**
     * Setter method for the encoder position (in encoder units)
     * @param position
     */
    public void setEncoderPosition(int position)
    {
		motor.setSelectedSensorPosition(position, 0, Constants.TIMEOUT_MS);
    }

    /**
     * Getter method to get the position in encoder untis
     * @return
     */
    public double getEncoderPosition()
    {
        return motor.getSelectedSensorPosition(0);
    }

    /**
     * gets the current position (absolute degrees)
     * @return
     */
    public double getCurrentPosition()
    {
        return currentPostion;
    }

    /**
     * Uses the current position in encoder values to calc the angle (absolute)
     * @return the absolute angle of the turret
     */
    public double getCurrentAngle()
    {
        return ((currentPostion - Constants.ZERO_LOCATION) / Constants.TOTAL_TICKS) * 180.0;
    }


    /**
     * sets the instance variable that determines if it is running
     * @param moving
     */
    private static void setIsMoving(boolean moving)
    {
        isMoving = moving;
    }

    /**
     * getter function for the isRunning boolean
     * @return true if it is running, false if it is not
     */
    public boolean getIsMoving()
    {
        return isMoving;
    }

    /**
     * Init function to be run to zero the location of the turret out.
     * Can be run at the init for auto and then again for teleop so the 
     * turret always starts in the same position 
     */
    public void init()
    {   
        double currentPositionInTicks = getEncoderPosition();

        if(currentPositionInTicks > Constants.ZERO_LOCATION + 5)
        {
            setSpeed(-0.1);
        }
        else if(currentPositionInTicks < Constants.ZERO_LOCATION - 5)
        {
            setSpeed(0.1);
        }
        else
        {
            setSpeed(0.0);
            setEncoderPosition(0); //TODO: Testing
        }
    }

    /**
     * Moves the shooter to an absolute position (in degrees)
     * uses a PID loop
     * @param angle
     */
    public void rotateTo(double angle)
    {
        int P = 1;
        int I = 1;
        int D = 1;
        int integral = 0;
        int previous_error = 0;
    
        double error = angle - getCurrentAngle(); // Error = Target - Actual
        integral += (error * .02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        double derivative = (error - previous_error) / .02;
        double speed = P * error + I * integral + D * derivative;

        if(error + Constants.ROTATION_TOLERANCE < angle || error - Constants.ROTATION_TOLERANCE > angle)
        {
            setSpeed(speed);
        }
        else
        {
            stop();
        }

        // motor.configMotionSCurveStrength(curveStrength, timeoutMs)
        // motor.set(ControlMode.MotionMagic, 2);
    }
    

    /**
     * Rotates the turret to a relative position (in degrees)
     * Uses a PID Loop
     * @param angle
     */
    public void rotate(double angle)
    {
        int P = 1;
        int I = 1;
        int D = 1;
        int integral = 0;
        int previous_error = 0;
    
        double error = angle; // Error = Target - Actual
        integral += (error * .02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        double derivative = (error - previous_error) / .02;
        double speed = P * error + I * integral + D * derivative;

        setSpeed(speed);
    }


    private class Constants
    {
        private static final int MOTOR_ID = 0;
        private static final double ZERO_LOCATION = 0; //TODO: Find out what value is straight ahead
        /* Nonzero to block the config until success, zero to skip checking */
        private static final int TIMEOUT_MS = 30;
        private static final double TOTAL_TICKS = 0.0; //TODO: Find out how many total ticks there are
        private static final double ROTATION_TOLERANCE = 2.0;
    }
}