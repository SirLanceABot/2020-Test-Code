package frc.maxwell;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.elliot.controls.DriverController;

/**
 * Class for controlling the shuttling of powercells from the intake to 
 * the shooter system.
 * Uses a 550 neo
 * @author Maxwell Li
 */
public class Shuttle
{
    private static DriverController driverController = DriverController.getInstance();
    /**
     * Enum for the states that the component can operate within
     */
    private enum State
    {
        Off()
        {
            @Override
            void doAction() 
            {   
                if(driverController.getRawButton(0))
                {
                    currentState = Transition.findNextState(currentState, Event.ReadyToFeed);
                }
                if(sensor5.get())
                {
                    currentState = Transition.findNextState(currentState, Event.PowerCellAtFlywheel);
                }       
                System.out.println("Shuttle State: Off");
                stop();
                if(isFull())
                {
                    currentState = Transition.findNextState(currentState, Event.ShuttleFull);
                }       
                else if(sensor1.get())
                {
                    currentState = Transition.findNextState(currentState, Event.PowerCellReadyToShuttle);
                }
                else
                {                    
                    currentState = Transition.findNextState(currentState, Event.NoPowerCellReadyToShuttle);
                } 
            }
        },
        MovingOnePosition()
        {
            private double currentPosition = getEncoderPosition();
            private double targetPosition = currentPosition + inchesToTicks(7);
            @Override
            void doAction() 
            {
                if(driverController.getRawButton(0))
                {
                    currentState = Transition.findNextState(currentState, Event.ReadyToFeed);
                }
                if(sensor5.get())
                {
                    currentState = Transition.findNextState(currentState, Event.PowerCellAtFlywheel);
                }       
                if(isFull())
                {
                    currentState = Transition.findNextState(currentState, Event.ShuttleFull);
                }       

                System.out.println("Shuttle State: MovingOnePosition");

                if(currentPosition < targetPosition - 10)
                {
                    motor.set(0.5);
                }
                else if(currentPosition > targetPosition + 10)
                {
                    motor.set(-0.5);
                }
                else
                {
                    currentState = Transition.findNextState(currentState, Event.NoPowerCellReadyToShuttle);
                }
                //TODO: Add PID Position Control
            }
        },
        UnloadingShuttle()
        {
            @Override
            void doAction() 
            {
                if(driverController.getRawButton(0))
                {
                    currentState = Transition.findNextState(currentState, Event.ReadyToFeed);
                }
                System.out.println("Shuttle State: Unloading");
                if(!isEmpty())
                {
                    setSpeed(0.25); //TODO: Find the right feed speed
                }
                else
                {
                    stop();
                    currentState = Transition.findNextState(currentState, Event.ShuttleEmpty);
                }
            }
        },
        Full()
        {
            @Override
            void doAction() 
            {
                if(driverController.getRawButton(0))
                {
                    currentState = Transition.findNextState(currentState, Event.ReadyToFeed);
                }
                if(sensor5.get())
                {
                    currentState = Transition.findNextState(currentState, Event.PowerCellAtFlywheel);
                }       
                System.out.println("Shuttle State: Full");
                if(isFull())
                {
                    stop();
                }
                else
                {
                    if(sensor1.get())
                    {
                        currentState = Transition.findNextState(currentState, Event.PowerCellReadyToShuttle);
                    }
                    else
                    {                    
                        currentState = Transition.findNextState(currentState, Event.NoPowerCellReadyToShuttle);
                    }
                }
            }
        },
        Empty()
        {
            @Override
            void doAction()
            {
                if(driverController.getRawButton(0))
                {
                    currentState = Transition.findNextState(currentState, Event.ReadyToFeed);
                }
                if(sensor5.get())
                {
                    currentState = Transition.findNextState(currentState, Event.PowerCellAtFlywheel);
                }       
                System.out.println("Shuttle State: Empty");
                if(sensor1.get())
                {
                    currentState = Transition.findNextState(currentState, Event.PowerCellReadyToShuttle);
                }
                else
                {                    
                    currentState = Transition.findNextState(currentState, Event.NoPowerCellReadyToShuttle);
                }
            }
        };

        abstract void doAction();
        State() {}
    }

    /**
     * Event enum that contains all the events that the machine can go through
     */
    private enum Event
    {
        PowerCellReadyToShuttle, ShuttleEmpty, ShuttleFull, NoPowerCellReadyToShuttle, PowerCellAtFlywheel, ReadyToFeed;
    }

    /**
     * Transition enum for the transition table that will direct the states
     */
    private enum Transition
    {
        //-----------------------------------------------TRANSITION TABLE---------------------------------------------------
        //----Name-----Current State--------------------------Event------------------------------------Target State---------
        TRANSITION_O_1(State.Off,                       Event.PowerCellReadyToShuttle,              State.MovingOnePosition),
        // TRANSITION_O_2(State.Off,                       Event.ShuttleEmpty,                         State.Off),
        TRANSITION_O_3(State.Off,                       Event.ShuttleFull,                          State.Full),
        TRANSITION_O_4(State.Off,                       Event.NoPowerCellReadyToShuttle,            State.Off),
        // TRANSITION_O_5(State.Off,                       Event.PowerCellAtFlywheel,                  State.Off),
        TRANSITION_O_6(State.Off,                       Event.ReadyToFeed,                          State.UnloadingShuttle),
        
        // TRANSITION_M_1(State.MovingOnePosition,         Event.PowerCellReadyToShuttle,              State.MovingOnePosition),
        // TRANSITION_M_2(State.MovingOnePosition,         Event.ShuttleEmpty,                         State.Off),
        TRANSITION_M_3(State.MovingOnePosition,         Event.ShuttleFull,                          State.Full),
        TRANSITION_M_4(State.MovingOnePosition,         Event.NoPowerCellReadyToShuttle,            State.Off),
        // TRANSITION_M_5(State.MovingOnePosition,         Event.PowerCellAtFlywheel,                  State.Off),
        TRANSITION_M_6(State.MovingOnePosition,         Event.ReadyToFeed,                          State.UnloadingShuttle),

        // TRANSITION_U_1(State.UnloadingClip,             Event.PowerCellReadyToShuttle,              State.UnloadingClip),
        TRANSITION_U_2(State.UnloadingShuttle,             Event.ShuttleEmpty,                         State.Off),
        // TRANSITION_U_3(State.UnloadingClip,             Event.ShuttleFull,                          State.Off),
        // TRANSITION_U_4(State.UnloadingClip,             Event.NoPowerCellReadyToShuttle,            State.UnloadingClip),
        // TRANSITION_U_5(State.UnloadingClip,             Event.PowerCellAtFlywheel,                  State.Off),
        TRANSITION_U_6(State.UnloadingShuttle,             Event.ReadyToFeed,                          State.UnloadingShuttle),

        // TRANSITION_F_1(State.Full,                      Event.PowerCellReadyToShuttle,              State.Off),
        // TRANSITION_F_2(State.Full,                      Event.ShuttleEmpty,                         State.Off),
        // TRANSITION_F_3(State.Full,                      Event.ShuttleFull,                          State.Off),
        // TRANSITION_F_4(State.Full,                      Event.NoPowerCellReadyToShuttle,            State.Off),
        // TRANSITION_F_5(State.Full,                      Event.PowerCellAtFlywheel,                  State.Off),
        TRANSITION_F_6(State.Full,                      Event.ReadyToFeed,                          State.UnloadingShuttle),

        TRANSITION_E_1(State.Empty,                     Event.PowerCellReadyToShuttle,              State.MovingOnePosition),
        // TRANSITION_E_2(State.Empty,                     Event.ShuttleEmpty,                         State.Off),
        // TRANSITION_E_3(State.Empty,                     Event.ShuttleFull,                          State.Off),
        // TRANSITION_E_4(State.Empty,                     Event.NoPowerCellReadyToShuttle,            State.MovingOnePosition),
        // TRANSITION_E_5(State.Empty,                     Event.PowerCellAtFlywheel,                  State.Off),
        TRANSITION_E_6(State.Empty,                     Event.ReadyToFeed,                          State.UnloadingShuttle);

        //-------------------------------------------------------------------------------------------------------------------
        private final State currentState;
        private final Event event;
        private final State nextState;
    
        /**
         * Constructor for transitions, shouldn't have to add any as it is all self contained
         * @param currentState
         * @param event
         * @param nextState
         */
        private Transition(State currentState, Event event, State nextState) 
        {
            this.currentState = currentState;
            this.event = event;
            this.nextState = nextState;
        }

        /**
         * table lookup to determine new state given the current state and the event
         * There is room for optimization
         * @param currentState
         * @param event
         * @return the next state (NEED TO STORE TO CURRENT STATE VARIABLE)
         */
        private static State findNextState(State currentState, Event event) 
        {
            for (Transition transition : Transition.values()) 
            {
                if (transition.currentState == currentState && transition.event == event) 
                {
                    return transition.nextState;
                }
            }
            System.out.println("ERROR: NO STATE TO TRANSITION TO FOUND");
            return currentState; // throw an error if here
        }
    }

    private static final int MOTOR_ID = 1; //TOD0: Change to actual motor id
    private static final double TICKS_PER_ROTATION = 4096.0;

    private static CANSparkMax motor = new CANSparkMax(MOTOR_ID, MotorType.kBrushless);
    private static CANEncoder encoder = new CANEncoder(motor);
    private static CANPIDController pidController = new CANPIDController(motor);
    //private static double currentPosition;
    private static double targetPosition;
    private static DigitalInput sensor1 = new DigitalInput(0);
    private static DigitalInput sensor2 = new DigitalInput(0);
    private static DigitalInput sensor3 = new DigitalInput(0);
    private static DigitalInput sensor4 = new DigitalInput(0);
    private static DigitalInput sensor5 = new DigitalInput(0);
    private static State currentState = State.Empty;
    private static DriverController controller = DriverController.getInstance();
    private static Shuttle instance = new Shuttle();

    protected Shuttle()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        motor.restoreFactoryDefaults();
        motor.setSmartCurrentLimit(40);
        setEncoderPosition(0);
        // currentPosition = 0;
        targetPosition = 0;
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    public static Shuttle getInstance()
    {
        return instance;
    }

    private static void setSpeed(double speed)
    {
        motor.set(speed);
    }
    
    private static void setSpeed()
    {
        motor.set(0.5);
    }

    private static void stop()
    {
        motor.set(0.0);
    }
    
    private static double getEncoderPosition()
    {
        return encoder.getPosition();
    }
    
    private static void setEncoderPosition(double position)
    {
        encoder.setPosition(position);
    }

    private static boolean isFull()
    {
        if(sensor1.get() && sensor2.get() && sensor3.get() && sensor4.get() && sensor5.get())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static boolean isEmpty()
    {
        if(!sensor1.get() && !sensor2.get() && !sensor3.get() && !sensor4.get() && !sensor5.get())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    // private static void setCurrentPosition(double position)
    // {
    //     currentPosition = position;
    // }

    // public static double getCurrentPosition()
    // {
    //     return currentPosition;
    // }

    public static void setTargetPosition(double position)
    {
        targetPosition = position;
    }

    public static double getTargetPosition()
    {
        return targetPosition;
    }

    private static double inchesToTicks(double inches)
    {
        return inches * TICKS_PER_ROTATION;
    }

    private void runFSM()
    {
        currentState.doAction();
    }

 
}