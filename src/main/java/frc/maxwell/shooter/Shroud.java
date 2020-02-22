package frc.maxwell.shooter;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/**
 * Class for controlling the shroud 
 * @author Maxwell Li
 */
public class Shroud
{
    private static final int MOTOR_ID = 0;
    private static final int TIMEOUT_MS = 30;
    private static final int UPPER_LIMIT = 100; //TODO: Find out the upper limit
    private static final int LOWER_LIMIT = 0;   //TODO: Find out the lower limit
    private static final int TRENCH_SHOT = 70;
    private static final int CLOSE_SHOT = 10;
    private static final int TOTAL_TICKS = 100; //TODO: Find out the total ticks
    private static final int TOTAL_DEGREES = 45; //TODO: Find the total degrees
    private static final int PLUS_MINUS_ERROR = 5;

    private static double currentAngle;
    private static boolean isMoving = false;
    private static TalonSRX motor = new TalonSRX(MOTOR_ID);
    private static Shroud instance = new Shroud();

    private Shroud()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        motor.configFactoryDefault();
        motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT_MS); //TODO: Find out port
        motor.configForwardSoftLimitEnable(true);
        motor.configForwardSoftLimitThreshold(UPPER_LIMIT);
        motor.configReverseSoftLimitEnable(true);
        motor.configReverseSoftLimitThreshold(LOWER_LIMIT);
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    /**
     * @return returns THE (1) instance of the shroud 
     */
    public static Shroud getInstance()
    {
        return instance;
    }

    /**
     * the one method that sets the speed of the motor
     * @param speed
     */
    private void setSpeed(double speed)
    {
        motor.set(ControlMode.PercentOutput, speed);
        setCurrentAngle();
    }

    /**
     * Stops the motors
     */
    private void stop()
    {
        motor.set(ControlMode.PercentOutput, 0);
    }

    /**
     * Getter function for the current angle
     * @return the current angle of the robot
     */
    public double getCurrentAngle()
    {
        return currentAngle;
    }

    /**
     * finds the position and sets it the instance variable to it
     * current position is in absolute degrees
     */
    private void setCurrentAngle()
    {
        currentAngle = ((getCurrentEncoderPosition() - LOWER_LIMIT) / TOTAL_TICKS) * TOTAL_DEGREES;
    }

    /**
     * overides whatever value is stored in the variable and stores the value to it
     * current position is in absolute degrees
     * @param position
     */
    private void setCurrentAngle(double angle)
    {
        currentAngle = angle;
    }

    /**
     * converts the angle to encoder ticks
     * @param angle
     * @return the number of ticks corresponding to the sent angle
     */
    private double angleToTicks(double angle)
    {
        return (angle / TOTAL_DEGREES) * TOTAL_TICKS;  //need to add the calulation based on the total ticks
    }

    /**
     * gets the current encoder value
     * @return the current encoder value
     */
    public double getCurrentEncoderPosition()
    {
        return motor.getSelectedSensorPosition(0);
    }

    /**
     * sets the shroud to the angle of the trench shot
     */
    private void setAngleToTrenchShot()
    {
        if(motor.getSelectedSensorPosition(0) < TRENCH_SHOT - PLUS_MINUS_ERROR) {setSpeed(0.5);}
        if(motor.getSelectedSensorPosition(0) > TRENCH_SHOT + PLUS_MINUS_ERROR) {setSpeed(-0.5);}
    }
    
    /**
     * sets the shroud to the angle of the close shot
     */
    private void setAngleToCloseShot()
    {
        if(motor.getSelectedSensorPosition(0) < CLOSE_SHOT - PLUS_MINUS_ERROR) {setSpeed(0.5);}
        if(motor.getSelectedSensorPosition(0) > CLOSE_SHOT + PLUS_MINUS_ERROR) {setSpeed(-0.5);}
    }

    /**
     * Set the absolute position (must be within _ and _)
     * 0 degrees is the lower limit (most likely horizontal) TODO: Find the angle of the lower limit and upper limit
     * @param angle in degrees
     */
    private void moveTo(double angle)
    {  
        double targetPosition = angleToTicks(angle);
        double currentPosition = angleToTicks(currentAngle);

        if(currentPosition < targetPosition - PLUS_MINUS_ERROR)
        {
            setSpeed(0.5);
        }
        else if(currentPosition > targetPosition + PLUS_MINUS_ERROR)
        {
            setSpeed(-0.5);
        }
        else
        {
            stop();
        }
    }

    /**
     * moves relative to the current position
     * positive to move up
     * negative to move down
     * @param angle in degrees
     */
    private void move(double angle)
    {
        double absolute_angle = angle + currentAngle;
        moveTo(absolute_angle);
    }
}