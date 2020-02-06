package frc.joey.shuffleboard;

//import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;


public class AutonomousTab
{
    // Create enumerated types for each Box
    //-------------------------------------------------------------------//
    public enum StartingLocation
    {
        kNone, kRight, kCenter, kLeft;
    }

    //-------------------------------------------------------------------//

    public enum OrderOfOperations
    {
        kShootThenMove, kMoveThenShoot;
    }

    //-------------------------------------------------------------------//

    public enum ShootPowerCell
    {
        kYes, kNo;
    }

    public enum ShootDelay
    {
        k0, k1, k2, k3, k4, k5;
    }

    //-------------------------------------------------------------------//

    public enum Move
    {
        kYes, kNo;
    }

    public enum MoveDelay
    {
        k0, k1, k2, k3, k4, k5;
    }

    public enum MoveDirection
    {
        kTowardPowerPort, kTowardRendezvousPoint;
    }

    //-------------------------------------------------------------------//

    public enum PickUpPowerCell
    {
        kYes, kNo;
    }

    public enum PickUpLocation
    {
        kTrench, kRendezvousPoint;
    }

    //-------------------------------------------------------------------//

    public enum ShootNewPowerCells
    {
        kYes, kNo;
    }



    // Create a class to hold the data on the Shuffleboard tab
    protected static class AutonomousTabData
    {
        public StartingLocation startingLocation = StartingLocation.kNone;

        public OrderOfOperations orderOfOperations = OrderOfOperations.kShootThenMove;

        public ShootPowerCell shootPowerCell = ShootPowerCell.kYes;
        public ShootDelay shootDelay = ShootDelay.k0;

        public Move move = Move.kYes;
        public MoveDelay moveDelay = MoveDelay.k0;
        public MoveDirection moveDirection = MoveDirection.kTowardPowerPort;

        public PickUpPowerCell pickUpPowerCell = PickUpPowerCell.kNo;
        public PickUpLocation pickUpLocation = PickUpLocation.kRendezvousPoint;

        public ShootNewPowerCells shootNewPowerCells = ShootNewPowerCells.kYes;
        
        @Override
        public String toString()
        {
            String str = "";

            str += " \n";
            str += "*****  AUTONOMOUS SELECTION  *****\n";
            str += "Starting Location     : "  + startingLocation   + "\n";

            str += "Order of Operations   : "  + orderOfOperations  + "\n";

            str += "Shoot Power Cell      : "  + shootPowerCell     + "\n";
            str += "Shoot Delay           : "  + shootDelay         + "\n";
            
            str += "Move                  : "  + move               + "\n";
            str += "Move Delay            : "  + moveDelay          + "\n";
            str += "Move Direction        : "  + moveDirection      + "\n";

            str += "Pick Up Power Cell    : "  + pickUpPowerCell    + "\n";
            str += "Pick Up Location      : "  + pickUpLocation     + "\n";
            
            str += "Shoot New Power Cells : "  + shootNewPowerCells + "\n";

            return str;
        }
    }

    // Create a Shuffleboard Tab
    private ShuffleboardTab autonomousTab = Shuffleboard.getTab("Autonomous");

    // Create an object to store the data in the Boxes
    private AutonomousTabData autonomousTabData = new AutonomousTabData();
 
    // Create the Box objects
    private SendableChooser<StartingLocation> startingLocationBox = new SendableChooser<>();

    private SendableChooser<OrderOfOperations> orderOfOperationsBox = new SendableChooser<>();

    private SendableChooser<ShootPowerCell> shootPowerCellBox = new SendableChooser<>();
    private SendableChooser<ShootDelay> shootDelayBox = new SendableChooser<>();
    
    private SendableChooser<Move> moveBox = new SendableChooser<>();
    private SendableChooser<MoveDelay> moveDelayBox = new SendableChooser<>();
    private SendableChooser<MoveDirection> moveDirectionBox = new SendableChooser<>();

    private SendableChooser<PickUpPowerCell> pickUpPowerCellBox = new SendableChooser<>();
    private SendableChooser<PickUpLocation> pickUpLocationBox = new SendableChooser<>();

    private SendableChooser<ShootNewPowerCells> shootNewPowerCellBox = new SendableChooser<>();
    


    // Create the Button object
    private SendableChooser<Boolean> sendDataButton = new SendableChooser<>();

    private boolean previousStateOfSendButton = false;

    private static AutonomousTab instance = new AutonomousTab();

    private AutonomousTab()
    {
        System.out.println(this.getClass().getName() + ": Started Constructor");

        createStartingLocationBox();

        createOrderOfOperationsBox();
        
        createShootPowerCellBox();
        createShootDelayBox();
        
        createMoveBox();
        createMoveDelayBox();
        createMoveDirectionBox();

        createPickUpPowerCellBox();
        createPickUpLocationBox();

        createShootNewPowerCellBox();
        

        createSendDataButton();

        System.out.println(this.getClass().getName() + ": Finished Constructor");
    }

    protected static AutonomousTab getInstance()
    {
        return instance;
    }



    /**
    * <b>Starting Location</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createStartingLocationBox()
    {
        //create and name the Box
        SendableRegistry.add(startingLocationBox, "Starting Location");
        SendableRegistry.setName(startingLocationBox, "Starting Location");
        
        //add options to  Box
        startingLocationBox.setDefaultOption("None (default)", StartingLocation.kNone);
        startingLocationBox.addOption("Left", StartingLocation.kLeft);
        startingLocationBox.addOption("Center", StartingLocation.kCenter);
        startingLocationBox.addOption("Right", StartingLocation.kRight);

        //put the widget on the shuffleboard
        autonomousTab.add(startingLocationBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(0, 0)
            .withSize(8, 2);
    }



    /**
    * <b>Order of Operations</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createOrderOfOperationsBox()
    {
        //create and name the Box
        SendableRegistry.add(orderOfOperationsBox, "Order of Operations");
        SendableRegistry.setName(orderOfOperationsBox, "Order of Operations");

        //add options to box
        orderOfOperationsBox.setDefaultOption("Shoot Then Move (default)", OrderOfOperations.kShootThenMove);
        orderOfOperationsBox.addOption("Move Then Shoot", OrderOfOperations.kMoveThenShoot);

        //put the widget on the Shuffleboard
        autonomousTab.add(orderOfOperationsBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(0,3)
            .withSize(8, 2);
    }



    /**
    * <b>Shoot Power Cell</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
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
            .withPosition(1, 6)
            .withSize(5, 2);
    }

    /**
    * <b>Shoot Delay</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createShootDelayBox()
    {
        //create and name the Box
        SendableRegistry.add(shootDelayBox, "Shoot Delay (Seconds)");
        SendableRegistry.setName(shootDelayBox, "Shoot Delay (Seconds)");

        //add options to Box
        shootDelayBox.setDefaultOption("0 (default)", ShootDelay.k0);
        shootDelayBox.addOption("1", ShootDelay.k1);
        shootDelayBox.addOption("2", ShootDelay.k2);
        shootDelayBox.addOption("3", ShootDelay.k3);
        shootDelayBox.addOption("4", ShootDelay.k4);
        shootDelayBox.addOption("5", ShootDelay.k5);

        //put the widget on the shuffleboard
        autonomousTab.add(shootDelayBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(7, 6)
            .withSize(4, 2);
    }



    /**
    * <b>Move</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createMoveBox()
    {
        //create and name the Box
        SendableRegistry.add(moveBox, "Move");
        SendableRegistry.setName(moveBox, "Move");

        //add options to Box
        moveBox.setDefaultOption("Yes (default)", Move.kYes);
        moveBox.addOption("No", Move.kNo);

        //put the widget on the shuffleboard
        autonomousTab.add(moveBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(1, 9)
            .withSize(4, 2);
    }

    /**
    * <b>Move Delay</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createMoveDelayBox()
    {
        //create and name the Box
        SendableRegistry.add(moveDelayBox, "Move Delay");
        SendableRegistry.setName(moveDelayBox, "Move Delay");

        //add options to Box
        moveDelayBox.setDefaultOption("0 (default)", MoveDelay.k0);
        moveDelayBox.addOption("1", MoveDelay.k1);
        moveDelayBox.addOption("2", MoveDelay.k2);
        moveDelayBox.addOption("3", MoveDelay.k3);
        moveDelayBox.addOption("4", MoveDelay.k4);
        moveDelayBox.addOption("5", MoveDelay.k5);

        //put the widget on the shuffleboard
        autonomousTab.add(moveDelayBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(6, 9)
            .withSize(4, 2);
    }

    /**
    * <b>Move Direction</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createMoveDirectionBox()
    {
        //create and name the Box
        SendableRegistry.add(moveDirectionBox, "Move Direction");
        SendableRegistry.setName(moveDirectionBox, "Move Direction");

        //add options to Box
        moveDirectionBox.setDefaultOption("Toward Power Port (default)", MoveDirection.kTowardPowerPort);
        moveDirectionBox.addOption("Toward Rendezvous Point", MoveDirection.kTowardRendezvousPoint);
        
        
        //put the widget on the shuffleboard
        autonomousTab.add(moveDirectionBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(11, 9)
            .withSize(4, 2);
    }

    /**
    * <b>Pick Up Power Cell</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createPickUpPowerCellBox()
    {
        //create and name the Box
        SendableRegistry.add(pickUpPowerCellBox, "Pick Up Power Cell\n After Shooting");
        SendableRegistry.setName(pickUpPowerCellBox, "Pick Up Power Cell\n After Shooting");

        //add options to Box
        pickUpPowerCellBox.setDefaultOption("No (default)", PickUpPowerCell.kNo);
        pickUpPowerCellBox.addOption("Yes", PickUpPowerCell.kYes);

        //put the widget on the shuffleboard
        autonomousTab.add(pickUpPowerCellBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(1, 12)
            .withSize(4, 2);
    }

    /**
    * <b>Power Cell Pick Up Location</b> Box
    * <p>Create an entry in the Network Table and add the Box to the Shuffleboard Tab
    */
    private void createPickUpLocationBox()
    {
        //create and name the Box
        SendableRegistry.add(pickUpLocationBox, "Power Cell\n Pick Up Location");
        SendableRegistry.setName(pickUpLocationBox, "Power Cell\n Pick Up Location");

        //add options to Box
        pickUpLocationBox.setDefaultOption("Rendezvous Point (default)", PickUpLocation.kRendezvousPoint);
        pickUpLocationBox.addOption("Trench", PickUpLocation.kTrench);
        
        //put the widget on the shuffleboard
        autonomousTab.add(pickUpLocationBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(6, 12)
            .withSize(4, 2);
    }

    private void createShootNewPowerCellBox()
    {
        //create and name the Box
        SendableRegistry.add(shootNewPowerCellBox, "Shoot New Power Cells");
        SendableRegistry.setName(shootNewPowerCellBox, "Shoot New Power Cells");

        //add options to Box
        shootNewPowerCellBox.setDefaultOption("Yes (default)", ShootNewPowerCells.kYes);
        shootNewPowerCellBox.addOption("No", ShootNewPowerCells.kNo);

        //put the widget on the shuffleboard
        autonomousTab.add(shootNewPowerCellBox)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(1, 15)
            .withSize(4, 2);
    }

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

        autonomousTabData.orderOfOperations = orderOfOperationsBox.getSelected();
        
        autonomousTabData.shootPowerCell = shootPowerCellBox.getSelected();
        autonomousTabData.shootDelay = shootDelayBox.getSelected();

        autonomousTabData.move = moveBox.getSelected();
        autonomousTabData.moveDelay = moveDelayBox.getSelected();
        autonomousTabData.moveDirection = moveDirectionBox.getSelected();

        autonomousTabData.pickUpPowerCell = pickUpPowerCellBox.getSelected();
        autonomousTabData.pickUpLocation = pickUpLocationBox.getSelected();
        
        autonomousTabData.shootNewPowerCells = shootNewPowerCellBox.getSelected();
    }

    public void checkForNewAutonomousTabData()
    {
        boolean isSendDataButtonPressed = getSendDataButton();

        if(isSendDataButtonPressed && !previousStateOfSendButton)
        {
            previousStateOfSendButton = true;

            // Get values from the Boxes
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