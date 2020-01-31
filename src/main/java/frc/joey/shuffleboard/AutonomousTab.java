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

    public enum OrderOfOperations
    {
        kShootThenMove, kMoveThenShoot, kDelayThenShoot;
    }

    public enum InitiationLine
    {
        kNone, kTowardPowerPort, kTowardRendezvousPoint;
    }

    public enum PickUpPowerCell
    {
        kYes, kNo;
    }

    public enum PickUpLocation
    {
        kTrench, kRendezvousPoint;
    }

    // Create a class to hold the data on the Shuffleboard tab
    protected static class AutonomousTabData
    {
        public StartingLocation startingLocation = StartingLocation.kNone;
        public ShootPowerCell shootPowerCell = ShootPowerCell.kYes;
        public InitiationLine initiationLine = InitiationLine.kNone;
        public PickUpPowerCell pickUpPowerCell = PickUpPowerCell.kNo;
        public PickUpLocation pickUpLocation = PickUpLocation.kRendezvousPoint;
        public OrderOfOperations orderOfOperations = OrderOfOperations.kShootThenMove;
        
        @Override
        public String toString()
        {
            String str = "";

            str += " \n";
            str += "*****  AUTONOMOUS SELECTION  *****\n";
            str += "Starting Location   : "  + startingLocation  + "\n";
            str += "Shoot Power Cell    : "  + shootPowerCell    + "\n";
            str += "Initiation Line     : "  + initiationLine    + "\n";
            str += "Pick Up Power Cell  : "  + pickUpPowerCell   + "\n";
            str += "Pick Up Location    : "  + pickUpLocation    + "\n";
            str += "Order of Operations : "  + orderOfOperations + "\n";

            return str;
        }
    }

    // Create a Shuffleboard Tab
    private ShuffleboardTab autonomousTab = Shuffleboard.getTab("Autonomous");

    // Create an object to store the data in the Combo Boxes
    private AutonomousTabData autonomousTabData = new AutonomousTabData();
 
    // Create the Combo Box objects
    private SendableChooser<StartingLocation> startingLocationBox = new SendableChooser<>();
    private SendableChooser<ShootPowerCell> shootPowerCellBox = new SendableChooser<>();
    private SendableChooser<InitiationLine> initiationLineBox = new SendableChooser<>();
    private SendableChooser<PickUpPowerCell> pickUpPowerCellBox = new SendableChooser<>();
    private SendableChooser<PickUpLocation> pickUpLocationBox = new SendableChooser<>();
    private SendableChooser<OrderOfOperations> orderOfOperationsBox = new SendableChooser<>();


    // Create the Button object
    private SendableChooser<Boolean> sendDataButton = new SendableChooser<>();

    private boolean previousStateOfSendButton = false;

    private static AutonomousTab instance = new AutonomousTab();

    private AutonomousTab()
    {
        System.out.println(this.getClass().getName() + ": Started Constructor");

        createStartingLocationBox();
        createShootPowerCellBox();
        createInitiationLineBox();
        createPickUpPowerCellBox();
        createPickUpLocationBox();
        createOrderOfOperationsBox();

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
    private void createStartingLocationBox()
    {
        //create and name the Combo Box
        SendableRegistry.add(startingLocationBox, "Starting Location");
        SendableRegistry.setName(startingLocationBox, "Starting Location");
        
        //add options to Combo Box
        startingLocationBox.setDefaultOption("None (default)", StartingLocation.kNone);
        startingLocationBox.addOption("Left", StartingLocation.kLeft);
        startingLocationBox.addOption("Center", StartingLocation.kCenter);
        startingLocationBox.addOption("Right", StartingLocation.kRight);

        //put the widget on the shuffleboard
        autonomousTab.add(startingLocationBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(0, 0)
            .withSize(4, 2);
    }

    /**
    * <b>Shoot Power Cell</b> Combo Box
    * <p>Create an entry in the Network Table and add the Combo Box to the Shuffleboard Tab
    */
    private void createShootPowerCellBox()
    {
        //create and name the Box
        SendableRegistry.add(shootPowerCellBox, "Shoot Power Cell");
        SendableRegistry.setName(shootPowerCellBox, "Shoot Power Cell");

        //add options to Box
        shootPowerCellBox.setDefaultOption("Yes (default)", ShootPowerCell.kYes);
        shootPowerCellBox.addOption("No", ShootPowerCell.kNo);

        //put the widget on the shuffleboard
        autonomousTab.add(shootPowerCellBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(0, 3)
            .withSize(4, 2);
    }

    private void createOrderOfOperationsBox()
    {
        //create and name the Box
        SendableRegistry.add(orderOfOperationsBox, "Order of Operations");
        SendableRegistry.setName(orderOfOperationsBox, "Order of Operations");

        //add options to box
        orderOfOperationsBox.setDefaultOption("Shoot Then Move", OrderOfOperations.kShootThenMove);

        //TODO: FINiSH THiS
    }

    /**
    * <b>Initiation Line</b> Combo Box
    * <p>Create an entry in the Network Table and add the Combo Box to the Shuffleboard Tab
    */
    private void createInitiationLineBox()
    {
        //create and name the Combo Box
        SendableRegistry.add(initiationLineBox, "Move Off Initiation Line");
        SendableRegistry.setName(initiationLineBox, "Move Off Initiation Line");

        //add options to Combo Box
        initiationLineBox.setDefaultOption("None (default)", InitiationLine.kNone);
        initiationLineBox.addOption("Toward Rendezvous Point", InitiationLine.kTowardRendezvousPoint);
        initiationLineBox.addOption("Toward Power Port", InitiationLine.kTowardPowerPort);
        
        //put the widget on the shuffleboard
        autonomousTab.add(initiationLineBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(0, 6)
            .withSize(4, 2);
    
    }

    /**
    * <b>Pick Up Power Cell</b> Combo Box
    * <p>Create an entry in the Network Table and add the Combo Box to the Shuffleboard Tab
    */
    private void createPickUpPowerCellBox()
    {
        //create and name the Combo Box
        SendableRegistry.add(pickUpPowerCellBox, "Pick Up Power Cell\n After Shooting");
        SendableRegistry.setName(pickUpPowerCellBox, "Pick Up Power Cell\n After Shooting");

        //add options to Combo Box
        pickUpPowerCellBox.setDefaultOption("No (default)", PickUpPowerCell.kNo);
        pickUpPowerCellBox.addOption("Yes", PickUpPowerCell.kYes);

        //put the widget on the shuffleboard
        autonomousTab.add(pickUpPowerCellBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(0, 9)
            .withSize(4, 2);
    }

    /**
    * <b>Power Cell Pick Up Location</b> Combo Box
    * <p>Create an entry in the Network Table and add the Combo Box to the Shuffleboard Tab
    */
    private void createPickUpLocationBox()
    {
        //create and name the Combo Box
        SendableRegistry.add(pickUpLocationBox, "Power Cell\n Pick Up Location");
        SendableRegistry.setName(pickUpLocationBox, "Power Cell\n Pick Up Location");

        //add options to Combo Box
        pickUpLocationBox.setDefaultOption("Rendezvous Point (default)", PickUpLocation.kRendezvousPoint);
        pickUpLocationBox.addOption("Trench", PickUpLocation.kTrench);
        
        //put the widget on the shuffleboard
        autonomousTab.add(pickUpLocationBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(0, 12)
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
        autonomousTabData.startingLocation = startingLocationBox.getSelected();
        autonomousTabData.shootPowerCell = shootPowerCellBox.getSelected();
        autonomousTabData.initiationLine = initiationLineBox.getSelected();
        autonomousTabData.pickUpPowerCell = pickUpPowerCellBox.getSelected();
        autonomousTabData.pickUpLocation = pickUpLocationBox.getSelected();
        
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

//TODO: CHANGE COMBOS TO SPLIT AND ADD NEW BOX