package frc.jwood.robot;

// import frc.jwood.components.Drivetrain;
// import frc.jwood.controls.DriverController;
import frc.jwood.shuffleboard.MainShuffleboard;

public class Teleop
{
    private MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();
    // private DriverController driverController = DriverController.getInstance();
    // private Drivetrain drivetrain = Drivetrain.getInstance();

    private static Teleop instance = new Teleop();

    /**
     * The constructor for the Teleop class. 
     */
    private Teleop()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    /**
     * The method to retrieve the instance of Teleop.
     * @return instance 
     */
    public static Teleop getInstance()
    {
        return instance;
    }

    /**
     * Call this method from teleopInit() method in the Robot class
     */
    public void init()
    {
        mainShuffleboard.setDriverControllerSettings();
        // driverController.resetRumbleIndex();
    }

    /**
     * Call this method from teleopPeriodic() method in the Robot class
     */
    public void periodic()
    {
        // double move = driverController.getAction(DriverController.AxisAction.kMove);
        // boolean intake = driverController.getAction(DriverController.ButtonAction.kIntake);

        // drivetrain.setRightPower(move);
        // drivetrain.setLeftPower(move);

        // mainShuffleboard.updateEncoderValues();


        // driverController.checkRumbleEvents();
    }

    // private void testShroudInit()
    // {
	//     position = 0;
	//     speed = 1.0; // initial speed for this example
	//     shroud.resetCounter();
    // }

    // private void testShroudPeriodic()
    // {
    //     position = shroud.getPosition();
	// 	System.out.println("Position " + position + ", Speed " + speed);

	// 	if (position >= 175) blockForward = true; // example check for at limit switch
	// 	else blockForward = false;

	// 	if (position <= 0) blockReverse = true; // example check for at limit switch
	// 	else blockReverse = false;

	// 	if (blockForward) speed = -1.0; // example if at a limit switch go back the other way
	// 	if (blockReverse) speed = +1.0;

	// 	// call CheckDirectionChange with same speed as Set() with (or before or after) every motor Set() to update position if reversing direction
	// 	shroud.setSpeed(shroud.checkDirectionChange(speed)); // refresh or change speed, update position if changing direction
	// 	// Wait(0.01); // ticks won't be lost but wait less to see them all here and respond faster

    //     // shroud.setSpeed(xbox.getRawAxis(Xbox.Axis.kLeftY));
    //     // System.out.println("Shroud encoder position = " + shroud.getEncoderPosition());
    // }

}