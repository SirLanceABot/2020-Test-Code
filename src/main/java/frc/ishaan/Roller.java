package frc.ishaan;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

public class Roller
{

    private static final int MASTER_MOTOR_ID = 4;
    private static final int SLAVE_MOTOR_ID = 1;
    private static final int ENCODER_ID = 2;

    private static CANSparkMax masterMotor = new CANSparkMax(MASTER_MOTOR_ID, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANSparkMax slaveMotor = new CANSparkMax(SLAVE_MOTOR_ID, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);

    private static CANEncoder encoder = masterMotor.getEncoder();
    private static double motorSpeed = 0.5;
    private static double DESIRED_VELOCITY = 50.0;

    protected CANPIDController m_pidController = masterMotor.getPIDController();
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

    
    private static Roller instance = new Roller();

    protected Roller()
    {
        masterMotor.restoreFactoryDefaults();
        slaveMotor.restoreFactoryDefaults();
        slaveMotor.follow(masterMotor);

        encoder.setPosition(0);
    }

    protected static Roller getInstance()
    {
        return instance;
    } 

    public void intake()
    {
        setSpeed(0.05);
    }

    public void eject()
    {
        setSpeed(-0.05);
    }

    public void stop()
    {
        setSpeed(0.0);
    }

    private void setSpeed(double speed)
    {
        masterMotor.set(speed);
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

    public void setRPMSpeed(double velocity)
    {
        // PID coefficients
        kP = 5e-5; 
        kI = 1e-6;
        kD = 0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;
        maxRPM = 5700;

        // set PID coefficients
        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);
        
        m_pidController.setReference(velocity, ControlType.kVelocity);
        System.out.println("Setpoint:" + velocity);

        double currentVelocity = getEncoderVelocity();
        if(Math.abs(DESIRED_VELOCITY - currentVelocity) > DESIRED_VELOCITY * 0.1)
        {
            motorSpeed = motorSpeed + Math.signum(DESIRED_VELOCITY - currentVelocity) * 0.01;
            motorSpeed = Math.min(motorSpeed, 1.0);
        }
    }
}