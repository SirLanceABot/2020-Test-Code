package frc.jwood.robot;

import frc.jwood.components.Drivetrain;
import frc.jwood.controls.DriverController;
import frc.jwood.shuffleboard.MainShuffleboard;

public class Teleop
{
    private MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();
    private DriverController driverController = DriverController.getInstance();
    private Drivetrain drivetrain = Drivetrain.getInstance();

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
        double move = driverController.getAction(DriverController.AxisAction.kMove);
        // boolean intake = driverController.getAction(DriverController.ButtonAction.kIntake);

        drivetrain.setRightPower(move);
        drivetrain.setLeftPower(move);

        mainShuffleboard.updateEncoderValues();


        // driverController.checkRumbleEvents();
    }

}