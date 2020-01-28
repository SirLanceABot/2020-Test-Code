package frc.joey.shuffleboard;

//import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;


public class AutonomousTab
{
    // Create enumerated types for each Combo Box
    public enum StartingLocation
    {
        kNone, kRight, kCenter, kLeft;
    }

    public enum ShootPowerCell
    {
        kYes, kNo;
    }

    public enum InitiationLine
    {
        kNone, kTowardPowerPort, kTowardRendezvousPoint;
    }

    public enum PickUpPowerCell
    {
        kYes, kNo;
    }

    // Create a class to hold the data on the Shuffleboard tab
    protected static class AutonomousTabData
    {
        public StartingLocation startingLocation = StartingLocation.kNone;
        public ShootPowerCell shootPowerCell = ShootPowerCell.kYes;
        public InitiationLine initiationLine = InitiationLine.kNone;
        public PickUpPowerCell pickUpPowerCell = PickUpPowerCell.kNo;

        // TODO: Add the other autonomous options
        
        @Override
        public String toString()
        {
            String str = "";

            str += "*****  AUTONOMOUS SELECTION  *****\n";
            str += "Starting Location: " + startingLocation + "\n";
            str += "Shoot Power Cell : " + shootPowerCell + "\n";
            str += "Direction to Move Off Initiation Line : " + initiationLine + "\n";
            str += "Pick Up Power Cell After Shooting : " + pickUpPowerCell + "\n";

            return str;
        }
    }

    // Create a Shuffleboard Tab
    private ShuffleboardTab autonomousTab = Shuffleboard.getTab("Autonomous");

    // Create an object to store the data in the Combo Boxes
    private AutonomousTabData autonomousTabData = new AutonomousTabData();
 
    // Create the Combo Box objects
    private SendableChooser<StartingLocation> startingLocationComboBox = new SendableChooser<>();
    private SendableChooser<ShootPowerCell> shootPowerCellComboBox = new SendableChooser<>();
    private SendableChooser<InitiationLine> initiationLineComboBox = new SendableChooser<>();
    private SendableChooser<PickUpPowerCell> pickUpPowerCellComboBox = new SendableChooser<>();

    // TODO: Add the other autonomous options


    // Create the Button object
    private SendableChooser<Boolean> sendDataButton = new SendableChooser<>();

    private boolean previousStateOfSendButton = false;

    private static AutonomousTab instance = new AutonomousTab();

    private AutonomousTab()
    {
        System.out.println(this.getClass().getName() + ": Started Constructor");

        createStartingLocationComboBox();
        createShootPowerCellComboBox();
        createInitiationLineComboBox();
        createPickUpPowerCellComboBox();

        // TODO: Call the other methods to create the autonomous widgets

        createSendDataButton();

        System.out.println(this.getClass().getName() + ": Finished Constructor");
    }

    protected static AutonomousTab getInstance()
    {
        return instance;
    }

    /**
    * <b>Starting Location</b> Combo Box
    * <p>Create an entry in the Network Table and add the Combo Box to the Shuffleboard Tab
    */
    private void createStartingLocationComboBox()
    {
        SendableRegistry.add(startingLocationComboBox, "Starting Location");
        SendableRegistry.setName(startingLocationComboBox, "Starting Location");
        
        startingLocationComboBox.setDefaultOption("None (default)", StartingLocation.kNone);
        startingLocationComboBox.addOption("Right", StartingLocation.kRight);
        startingLocationComboBox.addOption("Center", StartingLocation.kCenter);
        startingLocationComboBox.addOption("Left", StartingLocation.kLeft);

        autonomousTab.add(startingLocationComboBox)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(0, 0)
            .withSize(4, 2);
    }

    /**
    * <b>Shoot Power Cell</b> Combo Box
    * <p>Create an entry in the Network Table and add the Combo Box to the Shuffleboard Tab
    */
    private void createShootPowerCellComboBox()
    {
        SendableRegistry.add(shootPowerCellComboBox, "Shoot Power Cell");
        SendableRegistry.setName(shootPowerCellComboBox, "Shoot Power Cell");

        shootPowerCellComboBox.setDefaultOption("Yes (default)", ShootPowerCell.kYes);
        shootPowerCellComboBox.addOption("No", ShootPowerCell.kNo);

        autonomousTab.add(shootPowerCellComboBox)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(0, 3)
            .withSize(4, 2);
    }

    /**
    * <b>Initiation Line</b> Combo Box
    * <p>Create an entry in the Network Table and add the Combo Box to the Shuffleboard Tab
    */
    private void createInitiationLineComboBox()
    {
        //create and name the Combo Box
        SendableRegistry.add(initiationLineComboBox, "Move Off Initiation Line");
        SendableRegistry.setName(initiationLineComboBox, "Move Off Initiation Line");

        //add options to Combo Box
        //two lines??
        initiationLineComboBox.setDefaultOption("None (default)", InitiationLine.kNone);
        initiationLineComboBox.addOption("Toward Rendezvous Point", InitiationLine.kTowardRendezvousPoint);
        initiationLineComboBox.addOption("Toward Power Port", InitiationLine.kTowardPowerPort);
        
        //put the widget on the shuffleboard
        autonomousTab.add(initiationLineComboBox)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(0, 6)
            .withSize(4, 2);
    
    }

    /**
    * <b>Initiation Line</b> Combo Box
    * <p>Create an entry in the Network Table and add the Combo Box to the Shuffleboard Tab
    */
    private void createPickUpPowerCellComboBox()
    {
        //create and name the Combo Box
        SendableRegistry.add(pickUpPowerCellComboBox, "Pick Up Power Cell\n After Shooting");
        SendableRegistry.setName(pickUpPowerCellComboBox, "Pick Up Power Cell\n After Shooting");

        //add options to Combo Box
        pickUpPowerCellComboBox.setDefaultOption("No (default)", PickUpPowerCell.kNo);
        pickUpPowerCellComboBox.addOption("Yes", PickUpPowerCell.kYes);

        //put the widget on the shuffleboard
        autonomousTab.add(pickUpPowerCellComboBox)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(0, 9)
            .withSize(4, 2);
    }
    // TODO: Add methods for the other autonomous widgets

    /**
     * <b>Send Data</b> Button
     * <p>Create an entry in the Network Table and add the Button to the Shuffleboard Tab
     */
    private void createSendDataButton()
    {
        SendableRegistry.add(sendDataButton, "Send Data");
        SendableRegistry.setName(sendDataButton, "Send Data");

        sendDataButton.setDefaultOption("No", false);
        sendDataButton.addOption("Yes", true);

        autonomousTab.add(sendDataButton)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(9, 9)
            .withSize(4, 2);
    }

    private void updateAutonomousTabData()
    {
        autonomousTabData.startingLocation = startingLocationComboBox.getSelected();
        autonomousTabData.shootPowerCell = shootPowerCellComboBox.getSelected();
        autonomousTabData.initiationLine = initiationLineComboBox.getSelected();
        autonomousTabData.pickUpPowerCell = pickUpPowerCellComboBox.getSelected();
        //TODO: add other bois
    }

    public void checkForNewAutonomousTabData()
    {
        boolean isSendDataButtonPressed = getSendDataButton();

        if(isSendDataButtonPressed && !previousStateOfSendButton)
        {
            previousStateOfSendButton = true;

            // Get values from the Combo Boxes
            updateAutonomousTabData();

            System.out.println(autonomousTabData);
        }
        
        if (!isSendDataButtonPressed && previousStateOfSendButton)
        {
            previousStateOfSendButton = false;
        }
    }

    public AutonomousTabData getAutonomousTabData()
    {
        return autonomousTabData;
    }

    private boolean getSendDataButton()
    {
        return sendDataButton.getSelected();
    }
}