package frc.ishaan;

import frc.ishaan.Xbox.Button;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

public class MyRobot
{
    private static Roller roller = new Roller();
    private static Xbox xbox = new Xbox(0);

    public void robot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** ISHAAN's Test Code ***");
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

    }

    public void teleopPeriodic()
    {
        double encoderValue = roller.getEncoderValue();
        encoderValue = Math.round(encoderValue);

        if(xbox.getRawButton(Xbox.Button.kA))
        {
            roller.intake();
        }
        else if(xbox.getRawButton(Xbox.Button.kB))
        {
            roller.eject();
        }
        else if(xbox.getRawButton(Xbox.Button.kY))
        {
            roller.stop();
        }
        else if(xbox.getRawButton(Xbox.Button.kStart))
        {
            roller.resetEncoderValue();
        }
        double setPoint = xbox.getRawAxis(Xbox.Axis.kLeftY) * roller.maxRPM;
        roller.setRPMSpeed(setPoint);
        System.out.println("Setpoint:" + setPoint);
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