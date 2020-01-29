package frc.darren;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 * Class for controlling the gear Shifter of the Drivetrain.
 * @author Darren Fife
 */
// TODO: Test if the system works with only one solenoid.
public class Shifter
{
    // Constants
    private static enum ShifterState
    {
        kLowGear, kShiftingUp, kHighGear, kShiftingDown
    };

    // TODO: Change these if the solenoids are going in the wrong direction
    private static final int SHIFTER_SOLENOID_EXTEND_PORT = 1;
    private static final int SHIFTER_SOLENOID_RETRACT_PORT = 0;
    // private static final int SHIFTER_SOLENOID_RIGHT_EXTEND_PORT = 2;
    // private static final int SHIFTER_SOLENOID_RIGHT_RETRACT_PORT = 3;

    private static DoubleSolenoid shifterSolenoid = new DoubleSolenoid(SHIFTER_SOLENOID_EXTEND_PORT, SHIFTER_SOLENOID_RETRACT_PORT);
    // private static DoubleSolenoid shifterSolenoidRight = new DoubleSolenoid(SHIFTER_SOLENOID_RIGHT_EXTEND_PORT, SHIFTER_SOLENOID_RIGHT_RETRACT_PORT);

    private ShifterState shifterState = ShifterState.kLowGear; // TODO: Check defaul position
    private static Timer timer = new Timer();;
    private double shiftingUpTimeOut = 1.0;
    private double shiftingDownTimeOut = 1.0;

    private static Shifter instance = new Shifter();

    private Shifter()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    protected static Shifter getInstance()
    {
        return instance;
    }

    /**
     * Shifts the dog gear into high gear (14 feet per second).
     */
    public void shiftDown()
    {
        if(shifterState !=  ShifterState.kLowGear && shifterState != ShifterState.kShiftingDown)
        {
            timer.reset();
            setPneumatics(DoubleSolenoid.Value.kForward);
            shifterState = ShifterState.kShiftingDown;
            timer.start();
        }
    }

    /**
     * Checks if the Shifter is in Low Gear or should be in Low Gear by this time.
     * @return Whether the Shifter is in Low Gear (true) or not.
     */
    public boolean isLowGear()
    {
        if(shifterState == ShifterState.kLowGear)
        {
            return true;
        }
        else if((shifterState == ShifterState.kShiftingDown) && (timer.get() >= shiftingDownTimeOut)) // TODO: Check limit switch possibly
        {
            shifterState = ShifterState.kLowGear;
            timer.stop();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Shifts the dog gear into low gear (7 feet per second).
     */
    public void shiftUp()
    {
        if(shifterState != ShifterState.kHighGear && shifterState != ShifterState.kShiftingUp)
        {
            timer.reset();
            setPneumatics(DoubleSolenoid.Value.kReverse);
            shifterState = ShifterState.kShiftingUp;
            timer.start();
        }
    }

    /**
     * Checks if the Shifter is in High Gear or should be in High Gear by this time.
     * @return Whether the Shifter is in High Gear (true) or not.
     */
    public boolean isHighGear()
    {
        if(shifterState == ShifterState.kHighGear)
        {
            return true;
        }
        else if((shifterState == ShifterState.kShiftingUp) && (timer.get() >= shiftingUpTimeOut)) // TODO: Check limit switch possibly
        {
            shifterState = ShifterState.kHighGear;
            timer.stop();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Synchronizes the position with the state by force shifting down the Shifter.
     */
    public void forceShiftDown()
    {
        setPneumatics(DoubleSolenoid.Value.kReverse);
        shifterState = ShifterState.kLowGear;
    }

    /**
     * Synchronizes the position with the state by force shifting up the Shifter.
     */
    public void forceShiftUp()
    {
        setPneumatics(DoubleSolenoid.Value.kForward);
        shifterState = ShifterState.kHighGear;
    }

    /**
     * Sets the time out for shifting up the Shifter.
     * @param newTimeOut
     */
    public void setShiftingUpTimeOut(double newTimeOut)
    {
        shiftingUpTimeOut = newTimeOut;
    }

    /**
     * Sets the time out for shifting down the Shifter.
     * @param newTimeOut
     */
    public void setShiftingDownTimeOut(double newTimeOut)
    {
        shiftingDownTimeOut = newTimeOut;
    }

    /**
     * Sets the pneumatics position of the Shifter of the Drivetrain.
     * <p>DoubleSolenoid.Value.kReverse sets the gear into low gear.
     * <p>DoubleSolenoid.Value.kForward sets the gear into high gear.
     * @param position The position of the cylinder.
     */
    private static void setPneumatics(DoubleSolenoid.Value position)
    {
        shifterSolenoid.set(position);
        // shifterSolenoidRight.set(position);
    }
}