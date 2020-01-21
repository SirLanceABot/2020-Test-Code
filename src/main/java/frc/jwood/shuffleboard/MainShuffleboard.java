package frc.jwood.shuffleboard;

import frc.jwood.shuffleboard.AutonomousTab.AutonomousTabData;

public class MainShuffleboard
{
    private AutonomousTab autonomousTab = AutonomousTab.getInstance();
    private AutonomousTabData autonomousTabData;

    private DriverControllerTab driverControllerTab = DriverControllerTab.getInstance();

    private boolean isNewAutonomousDataAvailable = true;

    private static MainShuffleboard instance = new MainShuffleboard();

    private MainShuffleboard()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");

        //Shuffleboard.selectTab("Autonomous");

        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    public static MainShuffleboard getInstance()
    {
        return instance;
    }

    public void updateAutonomousTabData()
    {
        boolean isSendDataButtonPressed = autonomousTab.getSendDataButton();

        if (isSendDataButtonPressed && isNewAutonomousDataAvailable)
        {
            // Get values from the Combo Boxes
            autonomousTabData = autonomousTab.getAutonomousTabData();

            System.out.println(autonomousTabData);

            isNewAutonomousDataAvailable = false;
        }
        
        if (!isSendDataButtonPressed && !isNewAutonomousDataAvailable)
        {
            isNewAutonomousDataAvailable = true;
        }
    }

    public AutonomousTabData getAutonomousTabData()
    {
        return autonomousTabData;
    }

    public void setDriverControllerSettings()
    {
        //leftXAxisSettings = driverControllerTab.getAxisSettingsData();

        driverControllerTab.setAxisSettings();
    }
}