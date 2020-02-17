package frc.jwood.components;

import frc.jwood.robot.Port;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Roller
{

    // private static final int MASTER_MOTOR_ID = 1;
    //private static final int SLAVE_MOTOR_ID = 2;

    private static final double kP = 0.000351; 
    private static final double kI = 0.00000005;
    private static final double kD = 0; 
    private static final double kIz = 0; 
    private static final double kFF = 0.0;//0.000084; 
    private static final double kMaxOutput = 1; 
    private static final double kMinOutput = -1;
    private static final double maxRPM = 210;

    private static CANSparkMax masterMotor = new CANSparkMax(Port.Motor.INTAKE_ROLLER, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
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
        pidController.setFeedbackDevice(encoder);
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

    public void setSpeed(double speed)
    {
        // double rpmSpeed = speed * maxRPM;
        pidController.setReference(3000, ControlType.kVelocity);
        System.out.println(getEncoderVelocity() + " ");
        SmartDashboard.putNumber("RPM: ", getEncoderVelocity());
    }
}