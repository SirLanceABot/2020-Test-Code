package frc.jwood.shuffleboard;

import frc.jwood.controls.DriverController;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;

public class DriverControllerTab
{
    private DriverController driverController = DriverController.getInstance();

    // Create a Shuffleboard Tab
    private ShuffleboardTab driverControllerTab = Shuffleboard.getTab("Driver Controller");

    // Create an object to store the data
    private NetworkTableEntry leftXDeadzoneEntry;
    private NetworkTableEntry leftXMaxOutputEntry;
    private SendableChooser<Boolean> leftXIsFlipped = new SendableChooser<>();
    private SendableChooser<DriverController.AxisScale> leftXAxisScaleComboBox = new SendableChooser<>();

    private static DriverControllerTab instance = new DriverControllerTab();

    private DriverControllerTab()
    {
        System.out.println(this.getClass().getName() + ": Started Constructor");

        createLeftXAxisWidgets();

        System.out.println(this.getClass().getName() + ": Finished Constructor");
    }

    protected static DriverControllerTab getInstance()
    {
        return instance;
    }

    public void createLeftXAxisWidgets()
    {
        int column = 0;
        int row = 0;
        int width = 4;
        int height = 2;

        DriverController.AxisSettings axisSettings = driverController.new AxisSettings();
        axisSettings = driverController.getAxisSettings(DriverController.Axis.kLeftX);

        leftXDeadzoneEntry = createTextBox("Left X Deadzone", Double.toString(axisSettings.axisDeadzone), column, row, width, height);
        
        row += 2;
        leftXMaxOutputEntry = createTextBox("Left X Max Output", Double.toString(axisSettings.axisMaxOutput), column, row, width, height);

        row += 2;
        createSplitButtonChooser(leftXIsFlipped, "Left X Is Flipped", axisSettings.axisIsFlipped, column, row, width, height);

        row += 3;
        createComboBox(leftXAxisScaleComboBox, "Left X Axis Scale", axisSettings.axisScale, column, row, width, height);
    }

    /**
    * Create a <b>Text Box</b>
    * <p>Create an entry in the Network Table and add the Text Box to the Shuffleboard Tab
    */
    private NetworkTableEntry createTextBox(String title, String defaultValue, int column, int row, int width, int height)
    {
        return driverControllerTab.add(title, defaultValue)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(column, row)
            .withSize(width, height)
            .getEntry();
    }

    /**
    * Create a <b>Combo Box</b>
    * <p>Create an entry in the Network Table and add the Combo Box to the Shuffleboard Tab
    */
    private void createComboBox(SendableChooser<DriverController.AxisScale> comboBox, String title, DriverController.AxisScale defaultValue, int column, int row, int width, int height)
    {
        SendableRegistry.add(comboBox, title);
        SendableRegistry.setName(comboBox, title);
        
        for(DriverController.AxisScale axisScale: DriverController.AxisScale.values())
        {
            if(axisScale == defaultValue)
            {
                comboBox.setDefaultOption(axisScale.toString(), axisScale);
            }
            else
            {
                comboBox.addOption(axisScale.toString(), axisScale);
            }
        }

        driverControllerTab.add(comboBox)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(column, row)
            .withSize(width, height);
    }

    /**
    * Create a <b>Split Button Chooser</b>
    * <p>Create an entry in the Network Table and add the Split Button Chooser to the Shuffleboard Tab
    */
    private void createSplitButtonChooser(SendableChooser<Boolean> splitButtonChooser, String title, boolean defaultValue, int column, int row, int width, int height)
    {
        SendableRegistry.add(splitButtonChooser, title);
        SendableRegistry.setName(splitButtonChooser, title);

        splitButtonChooser.setDefaultOption((defaultValue ? "Yes" : "No"), defaultValue);
        splitButtonChooser.addOption((defaultValue ? "Yes" : "No"), !defaultValue);

        driverControllerTab.add(splitButtonChooser)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(column, row)
            .withSize(width, height);
    }

    public void setAxisSettings()
    {
        DriverController.AxisSettings axisSettings = driverController.new AxisSettings();

        axisSettings.axisDeadzone = Double.valueOf(leftXDeadzoneEntry.getString("0.1"));
        axisSettings.axisMaxOutput = Double.valueOf(leftXMaxOutputEntry.getString("1.0"));
        axisSettings.axisIsFlipped = leftXIsFlipped.getSelected();
        axisSettings.axisScale = leftXAxisScaleComboBox.getSelected();

        driverController.setAxisSettings(DriverController.Axis.kLeftX, axisSettings);
    }
}