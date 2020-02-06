package frc.jwood.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;

public class PIDTunerTab
{
    // public class PIDSettings
    // {
    //     public double kP;
    //     public double kI;
    //     public double kD;
    //     public double kIz;
    //     public double kFF;
    //     public double kMinOutput;
    //     public double kMaxOutput;
    // }

    // Create a Shuffleboard Tab
    private ShuffleboardTab pidTunerTab = Shuffleboard.getTab("PID Tuner");

    private NetworkTableEntry kPEntry;
    private NetworkTableEntry kIEntry;
    private NetworkTableEntry kDEntry;
    private NetworkTableEntry kIzEntry;
    private NetworkTableEntry kFFEntry;
    private NetworkTableEntry kMinOutputEntry;
    private NetworkTableEntry kMaxOutputEntry;
    private NetworkTableEntry rpmEntry;

    private static PIDTunerTab instance = new PIDTunerTab();

    private PIDTunerTab()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");

        createPIDWidgets();

        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    protected static PIDTunerTab getInstance()
    {
        return instance;
    }

    private void createPIDWidgets()
    {
        // Create the text box to set the deadzone of the axis
        kPEntry = createTextBox("P = ", "0.0", 0, 0, 4, 2);
        kIEntry = createTextBox("I = ", "0.0", 0, 3, 4, 2);
        kDEntry = createTextBox("I = ", "0.0", 0, 6, 4, 2);

        kIzEntry = createTextBox("Iz = ", "0.0", 5, 0, 4, 2);
        kFFEntry = createTextBox("FF = ", "0.0", 5, 3, 4, 2);

        kMinOutputEntry = createTextBox("MinOutput = ", "-1.0", 10, 0, 4, 2);
        kMaxOutputEntry = createTextBox("MaxOutput = ", "1.0", 10, 3, 4, 2);

        kMaxOutputEntry = createTextBox("RPM = ", "0.0", 10, 9, 4, 2);
    }

    // private void createPIDController()
    // {
    //     SendableRegistry.add(comboBox, title);
    //     SendableRegistry.setName(comboBox, title);

    //     pidTunerTab.add(comboBox)
    //         .withWidget(BuiltInWidgets.kPIDController)
    //         .withPosition(column, row)
    //         .withSize(width, height);
    // }

    // private void createSimpleDial(SendableChooser<DriverController.AxisScale> comboBox, String title, DriverController.AxisScale defaultValue, int column, int row, int width, int height)
    // {
    //     SendableRegistry.add(comboBox, title);
    //     SendableRegistry.setName(comboBox, title);

    //     pidTunerTab.add(comboBox)
    //         .withWidget(BuiltInWidgets.kDial)
    //         .withPosition(column, row)
    //         .withSize(width, height);
    // }


    /**
    * Create a <b>Text Box</b>
    * <p>Create an entry in the Network Table and add the Text Box to the Shuffleboard Tab
    */
    private NetworkTableEntry createTextBox(String title, String defaultValue, int column, int row, int width, int height)
    {
        return pidTunerTab.add(title, defaultValue)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(column, row)
            .withSize(width, height)
            .getEntry();
    }

    // public PIDSettings getPIDSettings()
    // {
    //     PIDSettings pidSettings = new PIDSettings();

    //     pidSettings.kP = Double.valueOf(kPEntry.getString("0.0"));
    //     pidSettings.kI = Double.valueOf(kIEntry.getString("0.0"));
    //     pidSettings.kD = Double.valueOf(kDEntry.getString("0.0"));

    //     pidSettings.kIz = Double.valueOf(kIzEntry.getString("0.0"));
    //     pidSettings.kFF = Double.valueOf(kFFEntry.getString("0.0"));

    //     pidSettings.kMinOutput = Double.valueOf(kMinOutputEntry.getString("1.0"));
    //     pidSettings.kMaxOutput = Double.valueOf(kMaxOutputEntry.getString("1.0"));

    //     return pidSettings;
    // }

    public double getP()
    {
        return Double.valueOf(kPEntry.getString("0.0"));
    }

    public void setRPM(double rpm)
    {

    }

}