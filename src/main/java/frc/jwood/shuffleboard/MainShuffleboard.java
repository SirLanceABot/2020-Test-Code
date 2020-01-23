package frc.jwood.shuffleboard;

import frc.jwood.shuffleboard.AutonomousTab.AutonomousTabData;

public class MainShuffleboard
{
    private AutonomousTab autonomousTab = AutonomousTab.getInstance();
    private DriverControllerTab driverControllerTab = DriverControllerTab.getInstance();

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

    // ----------------------------------------------------------------------------------
    // AUTONOMOUS TAB
    public AutonomousTabData getAutonomousTabData()
    {
        return autonomousTab.getAutonomousTabData();
    }

    public void checkForNewAutonomousTabData()
    {
        autonomousTab.checkForNewAutonomousTabData();
    }

    // ------------------------------------------------------------------------------------
    // DRIVER CONTROLLER TAB
    public void setDriverControllerSettings()
    {
        driverControllerTab.setDriverControllerAxisSettings();
    }
}