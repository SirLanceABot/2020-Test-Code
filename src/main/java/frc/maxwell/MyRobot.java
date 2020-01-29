package frc.maxwell;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.maxwell.Xbox.Axis;

public class MyRobot
{
    private static TalonSRX motor = new TalonSRX(0);
    PowerDistributionPanel pdp = new PowerDistributionPanel();
    //private static Turret turret = Turret.getInstance();
    private static Xbox joystick = new Xbox(0);
    public void myRobot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** MAXWELL's Test Code ***");

        System.out.println(this.getClass().getName() + " : Finished Constructor");
    }

    public void robotInit()
    {

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
        motor.configFactoryDefault();
    }

    public void teleopPeriodic()
    {
        motor.set(ControlMode.PercentOutput, joystick.getRawAxis(Axis.kLeftX));
        System.out.println(pdp.getTotalCurrent());
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