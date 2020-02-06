package frc.jwood.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class SensorsTab
{
    // Create a Shuffleboard Tab
    private ShuffleboardTab sensorsTab = Shuffleboard.getTab("Sensors");
    
    private static final SensorsTab instance = new SensorsTab();

    private SensorsTab()
    {
        System.out.println(this.getClass().getName() + ": Started Constructor");
        

        System.out.println(this.getClass().getName() + ": Finished Constructor");
    }

    public SensorsTab getInstance()
    {
        return instance;
    }


}