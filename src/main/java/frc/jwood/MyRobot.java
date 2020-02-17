package frc.jwood;

import frc.jwood.robot.Autonomous;
import frc.jwood.robot.Disabled;
import frc.jwood.robot.Teleop;
import frc.jwood.robot.Test;

public class MyRobot
{
    public enum RobotState
    {
        kNone, kStartup, kBeforeGame, kAutonomous, kBetweenAutoAndTeleop, kTeleop, kAfterGame, kTest;

        public RobotState nextState()
        {
            RobotState nextRobotState = kNone;

            switch(this)
            {
                case kNone:
                    nextRobotState = kNone;
                    break;
                case kStartup:
                    nextRobotState = kBeforeGame;
                    break;
                case kBeforeGame:
                    nextRobotState = kAutonomous;
                    break;
                case kAutonomous:
                    nextRobotState = kBetweenAutoAndTeleop;
                    break;
                case kBetweenAutoAndTeleop:
                    nextRobotState = kTeleop;
                    break;
                case kTeleop:
                    nextRobotState = kAfterGame;
                    break;
                case kAfterGame:
                    nextRobotState = kTest;
                    break;
                case kTest:
                    nextRobotState = kBeforeGame;
                    break;    
            }

            return nextRobotState;
        }
    }

    private Autonomous autonomous = Autonomous.getInstance();
    private Teleop teleop = Teleop.getInstance();
    private Test test = Test.getInstance();
    private Disabled disabled = Disabled.getInstance();

    private static RobotState robotState = RobotState.kNone;
    
    public void myRobot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** JWOOD's Test Code ***");

        System.out.println(this.getClass().getName() + " : Finished Constructor");
    }

    public void robotInit()
    {
        robotState = RobotState.kStartup;
    }

    public void robotPeriodic()
    {

    }

    public void autonomousInit()
    {
        robotState = RobotState.kAutonomous;

        autonomous.init();
    }

    public void autonomousPeriodic()
    {
        autonomous.periodic();
    }

    public void teleopInit()
    {
        robotState = RobotState.kTeleop;

        teleop.init();
    }

    public void teleopPeriodic()
    {
        teleop.periodic();
    }

    public void testInit()
    {
        robotState = RobotState.kTest;

        test.init();
    }

    public void testPeriodic()
    {
        test.periodic();
    }

    public void disabledInit()
    {
        robotState = robotState.nextState();

        disabled.init();
    }

    public void disabledPeriodic()
    {
        disabled.periodic();
    }

    public static RobotState getRobotState()
    {
        return robotState;
    }

}