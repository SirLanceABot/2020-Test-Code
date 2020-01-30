package frc.elliot.components;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 * Class for controlling the Wrist of the Intake system.
 * @author Darren Fife
 */
public class Wrist
{
    // Constants
    private static enum WristState
    {
        kUp, kLowering, kDown, kRaising
    };

    // TODO: Change these if the solenoids are going in the wrong direction
    private static final int WRIST_SOLENOID_LEFT_EXTEND_PORT = 1;
    private static final int WRIST_SOLENOID_LEFT_RETRACT_PORT = 0;
    private static final int WRIST_SOLENOID_RIGHT_EXTEND_PORT = 2;
    private static final int WRIST_SOLENOID_RIGHT_RETRACT_PORT = 3;

    private static DoubleSolenoid wristSolenoidLeft = new DoubleSolenoid(WRIST_SOLENOID_LEFT_EXTEND_PORT, WRIST_SOLENOID_LEFT_RETRACT_PORT);
    private static DoubleSolenoid wristSolenoidRight = new DoubleSolenoid(WRIST_SOLENOID_RIGHT_EXTEND_PORT, WRIST_SOLENOID_RIGHT_RETRACT_PORT);

    private WristState wristState = WristState.kUp;
    private static Timer timer;
    private double loweringTimeOut = 1.0;
    private double raisingTimeOut = 1.0;

    private static Wrist instance = new Wrist();

    private Wrist()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        timer = new Timer();
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    protected static Wrist getInstance()
    {
        return instance;
    }

    /**
     * Raises the Wrist on the Intake system.
     */
    public void raise()
    {
        if(wristState !=  WristState.kUp && wristState != WristState.kRaising)
        {
            timer.reset();
            setPneumatics(DoubleSolenoid.Value.kReverse);
            wristState = WristState.kRaising;
            timer.start();
        }
    }

    /**
     * Checks if the Wrist is up or should be up by this time.
     */
    public boolean isUp()
    {
        if(wristState == WristState.kUp)
        {
            return true;
        }
        else if((wristState == WristState.kRaising) && (timer.get() >= raisingTimeOut)) // TODO: Check limit switch possibly
        {
            wristState = WristState.kUp;
            timer.stop();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Lowers the Wrist on the Intake system.
     */
    public void lower()
    {
        if(wristState != WristState.kDown && wristState != WristState.kLowering)
        {
            timer.reset();
            setPneumatics(DoubleSolenoid.Value.kForward);
            wristState = WristState.kRaising;
            timer.start();
        }
    }

    /**
     * Checks if the Wrist is down or should be down by this time.
     */
    public boolean isDown()
    {
        if(wristState == WristState.kDown)
        {
            return true;
        }
        else if((wristState == WristState.kLowering) && (timer.get() >= loweringTimeOut)) // TODO: Check limit switch possibly
        {
            wristState = WristState.kDown;
            timer.stop();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Synchronizes the position with the state by force raising the Wrist.
     */
    public void forceRaise()
    {
        setPneumatics(DoubleSolenoid.Value.kReverse);
        wristState = WristState.kUp;
    }

    /**
     * Synchronizes the position with the state by force lowering the Wrist.
     */
    public void forceLower()
    {
        setPneumatics(DoubleSolenoid.Value.kForward);
        wristState = WristState.kDown;
    }

    /**
     * Sets the time out for lowering the Wrist.
     * @param newTimeOut
     */
    public void setLoweringTimeOut(double newTimeOut)
    {
        loweringTimeOut = newTimeOut;
    }

    /**
     * Sets the time out for raising the Wrist.
     * @param newTimeOut
     */
    public void setRaisingTimeOut(double newTimeOut)
    {
        raisingTimeOut = newTimeOut;
    }

    /**
     * Sets the pneumatics position of the Wrist on the Intake system.
     * <p>DoubleSolenoid.Value.kReverse raises the Wrist.
     * <p>DoubleSolenoid.Value.kForward lowers the Wirst.
     * @param position The position of the cylinder.
     */
    private static void setPneumatics(DoubleSolenoid.Value position)
    {
        wristSolenoidLeft.set(position);
        wristSolenoidRight.set(position);
    }
}