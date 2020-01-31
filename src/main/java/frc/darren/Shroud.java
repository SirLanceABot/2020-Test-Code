package frc.darren;

// import wpilibj.Bosch;

/**
 * Class for controlling the Shroud of the Shooter.
 * @author Darren Fife
 */
public class Shroud
{
    private double previousSpeed = 0;

    private static Shroud instance = new Shroud();

    private Shroud()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        // Bosch(CANTalonDeviceID, encoderPort);

        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    protected static Shroud getInstance()
    {
        return instance;
    }

    private double checkDirectionChange(double newSpeed)
    {
        // Update position accumulator if changing direction
        // Encoder doesn't know the direction so we have to remember the direction for it
        if((previousSpeed < 0 && newSpeed >= 0) || (previousSpeed >= 0 && newSpeed < 0))
        {
            // position = motor.getPosition();
            // counter.reset();
            previousSpeed = newSpeed;
        }

        return newSpeed;
    }
}