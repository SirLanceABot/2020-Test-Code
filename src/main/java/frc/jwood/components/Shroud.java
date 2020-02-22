package frc.jwood.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// import edu.wpi.first.wpilibj.AnalogInput;
// import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

// import wpilibj.Bosch;

/**
 * Class for controlling the Shroud of the Shooter.
 * @author Darren Fife
 */
public class Shroud
{
    private TalonSRX motor = new TalonSRX(0);
    // private AnalogInput analogInput = new AnalogInput(3);
    // private AnalogTrigger analogTrigger = new AnalogTrigger(analogInput);
    private DigitalInput digitalInput = new DigitalInput(0);
    
    private Counter counter = new Counter(digitalInput);

    private double previousSpeed = 0.0;
    private int position = 0;

    private static Shroud instance = new Shroud();

    private Shroud()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        motor.configFactoryDefault();
        // motor.configSelectedFeedbackSensor(FeedbackDevice.SensorSum);
        counter.reset();
        // digitalInput.setUpSourceEdge(true, false);

        // analogTrigger.setLimitsVoltage(0,0.5);//3.5, 3.8);   // Values higher than the highest minimum (pulse floor), lower than the lowest maximum (pulse ceiling)
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    public static Shroud getInstance()
    {
        return instance;
    }

    /**
     * Sets the speed of the shroud motor.
     * @param speed
     */
    public void setSpeed(double speed)
    {
        motor.set(ControlMode.PercentOutput, checkDirectionChange(speed));
        // System.out.println(motor.getSensorCollection().getAnalogInRaw());
        // System.out.println(position + "   " + analogInput.getVoltage());
        // System.out.println(position + "  " + counter.get());// + "  " + digitalInput.get());
        boolean di = digitalInput.get();
        System.out.println((di ? "|---" : "---|") + "  "  + counter.get() + "  " + position);
    }

    public double checkDirectionChange(double newSpeed)
    {
        // Update position accumulator if changing direction
        // Encoder doesn't know the direction so we have to remember the direction for it
        if((previousSpeed < 0 && newSpeed >= 0) || (previousSpeed >= 0 && newSpeed < 0))
        {
            if(previousSpeed >= 0)
            {
                // return position + motor.getSelectedSensorPosition();    // been going forward so add counter
                position = position + counter.get();    // been going forward so add counter
            }
            else
            {
                // return position - motor.getSelectedSensorPosition();    // been going backward so subtract counter
                position = position - counter.get();    // been going backward so subtract counter
            }

            // position = getPosition();           // Changing directions so save what we have
            counter.reset();                    // and start counting in the new direction
            previousSpeed = newSpeed;           // Return input speed for ease of use (may include it in the set() argument => set(checkDirectionChange(speed)))
        }

        return newSpeed;
    }

    /**
     * Gets the encoder position of the shroud motor.
     * @return
     */
    public int getPosition()
    {
        // Position from previous direction change plus what's been accumulated so far in this direction
        if(previousSpeed >= 0)
        {
            // return position + motor.getSelectedSensorPosition();    // been going forward so add counter
            return position + counter.get();    // been going forward so add counter
        }
        else
        {
            // return position - motor.getSelectedSensorPosition();    // been going backward so subtract counter
            return position - counter.get();    // been going backward so subtract counter
        }
    }

    public int getEncoderPosition()
    {
        return position;
    }

    public void resetCounter()
    {
        counter.reset();
    }

    public void resetPosition()
    {
        position = 0;
    }
}