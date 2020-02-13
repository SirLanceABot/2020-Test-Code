package frc.maxwell;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.maxwell.Shuttle;

public class MyRobot {

    //private static Intake intake = Intake.getInstance();
    //private static Xbox joystick = new Xbox(0);
    static Shuttle shuttle = Shuttle.getInstance();
    private static CANSparkMax motor = new CANSparkMax(2, MotorType.kBrushless);
    public void myRobot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** MAXWELL's Test Code ***");

    }

    public void robotInit() {
      
    }

    public void robotPeriodic()
    {

    }

    public void autonomousInit()
    {

    }

    public void autonomousPeriodic()
    {

    }

    public void teleopInit()
    {
        
    }

    public void teleopPeriodic()
    {
        //intake.runFSM();
        //motor.set(joystick.getRawAxis(1));
        shuttle.runFSM();
        //]\[motor.set(-0.5);
        //System.out.println(motor.getEncoder().getPosition());
    }

    public void testInit()
    {

    }

    public void testPeriodic()
    {
    }

    public void disabledInit()
    {

    }

    public void disabledPeriodic()
    {

    }
}