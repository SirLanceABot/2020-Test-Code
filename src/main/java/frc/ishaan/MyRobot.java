package frc.ishaan;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
public class MyRobot
{
    private static TalonSRX motor1 = new TalonSRX(0);
    private static TalonSRX motor2 = new TalonSRX(1);
    private static Xbox xbox = new Xbox(0);

    private CANSparkMax motor = new CANSparkMax(1, MotorType.kBrushless);

    public void myRobot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** ISHAAN's Test Code ***");
        motor1.configFactoryDefault();
        motor2.follow(motor1);

        motor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);

        // motor1.setSensorPhase(true);

		// /* Config the peak and nominal outputs */
		// motor1.configNominalOutputForward(0, 30);
		// motor1.configNominalOutputReverse(0, 30);
		// motor1.configPeakOutputForward(1, 30);
		// motor1.configPeakOutputReverse(-1, 30);

		// /* Config the Velocity closed loop gains in slot0 */
		// motor1.config_kF(0, 0               , 30);
		// motor1.config_kP(0, 0.125            , 30);
		// motor1.config_kI(0, 0.0005               , 30);
		// motor1.config_kD(0, 0.0000000               , 30);
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
        motor1.set(ControlMode.PercentOutput, 0.75);
        
        // double targetVelocity_UnitsPer100ms = 50000;//500.0 * 4096 / 600;
        // /* 500 RPM in either direction */
        System.out.println("Velocity: " + motor1.getSelectedSensorVelocity(0));
        // motor1.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
        // double encoderValue = roller.getEncoderValue();
        // encoderValue = Math.round(encoderValue);

        // if(xbox.getRawButton(Xbox.Button.kA))
        // {
        //     roller.intake();
        // }
        // else if(xbox.getRawButton(Xbox.Button.kB))
        // {
        //     roller.eject();
        // }
        // else if(xbox.getRawButton(Xbox.Button.kY))
        // {
        //     roller.stop();
        // }
        // else if(xbox.getRawButton(Xbox.Button.kStart))
        // {
        //     roller.resetEncoderValue();
        // }
        // double setPoint = xbox.getRawAxis(Xbox.Axis.kLeftY) * roller.maxRPM;
        // roller.setRPMSpeed(setPoint);
        // System.out.println("Setpoint:" + setPoint);
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