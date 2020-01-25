package frc.elliot.components;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController;

public class Roller
{

    private static final int MASTER_MOTOR_ID = 4;
    //private static final int SLAVE_MOTOR_ID = 1;

    private static final double kP = 5e-5; 
    private static final double kI = 1e-6;
    private static final double kD = 0; 
    private static final double kIz = 0; 
    private static final double kFF = 0; 
    private static final double kMaxOutput = 1; 
    private static final double kMinOutput = -1;
    private static final double maxRPM = 2000;

    private static CANSparkMax masterMotor = new CANSparkMax(MASTER_MOTOR_ID, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    //private static CANSparkMax slaveMotor = new CANSparkMax(SLAVE_MOTOR_ID, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);

    private static CANEncoder encoder = masterMotor.getEncoder();
    private static CANPIDController pidController = new CANPIDController(masterMotor);

    private static Roller instance = new Roller();
    
    private Roller()
    {
        masterMotor.restoreFactoryDefaults();
        //slaveMotor.restoreFactoryDefaults();
        //slaveMotor.follow(masterMotor);
        encoder.setPosition(0);

        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        pidController.setOutputRange(kMinOutput, kMaxOutput);
    }

    public static Roller getInstance()
    {
        return instance;
    } 

    public void intake()
    {
        setSpeed(1.0);
    }

    public void eject()
    {
        setSpeed(-1.0);
    }

    public void stop()
    {
        setSpeed(0.0);
    }

    public double getEncoderValue()
    {
        return encoder.getPosition();
    }

    public void resetEncoderValue()
    {
        encoder.setPosition(0.0);
    }

    public double getEncoderVelocity()
    {
        return encoder.getVelocity();
    }

    private void setSpeed(double speed)
    {
        double rpmSpeed = speed * maxRPM;
        pidController.setReference(rpmSpeed, ControlType.kVelocity);
        System.out.println(rpmSpeed);
    }
}