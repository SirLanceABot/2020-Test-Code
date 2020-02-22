package frc.maxwell;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.maxwell.OperatorController;
import frc.maxwell.Shuttle;

public class MyRobot {

    //private static Intake intake = Intake.getInstance();
    //private static Xbox joystick = new Xbox(0);
    private static OperatorController operatorController = OperatorController.getInstance();
    private static TalonSRX turretMotor = new TalonSRX(1);
    private static Shuttle shuttle = Shuttle.getInstance();
    private static TalonSRX flywheelMasterMotor = new TalonSRX(4);
    private static VictorSPX flywheelSlaveMotor = new VictorSPX(0);
    private static CANSparkMax motor = new CANSparkMax(2, MotorType.kBrushless);

    public void myRobot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** MAXWELL's Test Code ***");
        flywheelMasterMotor.configFactoryDefault();
        flywheelSlaveMotor.configFactoryDefault();
        flywheelSlaveMotor.follow(flywheelMasterMotor);
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

        if(Math.abs(operatorController.getTwist()) > 0.2)
        {
            turretMotor.set(ControlMode.PercentOutput, operatorController.getTwist() / 2.0);
        }
        else
        {
            turretMotor.set(ControlMode.PercentOutput, 0);
        }

        //System.out.println(operatorController.getRawAxis(3));
        if(operatorController.getRawAxis(3) < 0.8)
        {
            flywheelMasterMotor.set(ControlMode.PercentOutput, -(1 + Math.abs(operatorController.getRawAxis(3)) / 2.0));
        }
        else
        {
            flywheelMasterMotor.set(ControlMode.PercentOutput, 0);
        }
        // motor.set(-0.5);
        // System.out.println(motor.getEncoder().getPosition());
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