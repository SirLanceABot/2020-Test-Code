package frc.jwood.shuffleboard;

import frc.jwood.components.Drivetrain;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;


public class SensorsTab
{
    private static final String className = new String("[SensorsTab]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    // Create a Shuffleboard Tab
    private ShuffleboardTab sensorsTab = Shuffleboard.getTab("Sensors");

    private Drivetrain drivetrain = Drivetrain.getInstance();

    private NetworkTableEntry leftDriveEntry;
    private NetworkTableEntry rightDriveEntry;
    
    private static SensorsTab instance = new SensorsTab();

    private SensorsTab()
    {
        System.out.println(className + " : Constructor Started");

        // Create the text boxes for the Drivetrain encoders
        leftDriveEntry = createTextBox("Front Left Drive", 0, 0, 0, 4, 2);
        rightDriveEntry = createTextBox("Front Right Drive", 0, 0, 4, 4, 2);

        System.out.println(className + ": Constructor Finished");
    }

    public static SensorsTab getInstance()
    {
        return instance;
    }

    /**
    * Create a <b>Text Box</b>
    * <p>Create an entry in the Network Table and add the Text Box to the Shuffleboard Tab
    */
    private NetworkTableEntry createTextBox(String title, Integer defaultValue, int column, int row, int width, int height)
    {
        return sensorsTab.add(title, defaultValue)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(column, row)
            .withSize(width, height)
            .getEntry();
    }

    public void updateEncoderValues()
    {
        leftDriveEntry.setNumber(drivetrain.getLeftPosition());
        rightDriveEntry.setNumber(drivetrain.getRightPosition());
    }
}