package frc.elliot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANEncoder;
// import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

public class Roller
{

    private static final int MASTER_MOTOR_ID = 0;
    private static final int SLAVE_MOTOR_ID = 1;
    private static final int ENCODER_ID = 2;

    private static CANSparkMax masterMotor = new CANSparkMax(MASTER_MOTOR_ID, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANSparkMax slaveMotor = new CANSparkMax(SLAVE_MOTOR_ID, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);

    private static CANEncoder encoder = masterMotor.getEncoder();
    private static double motorSpeed = 0.5;
    private static final double DESIRED_VELOCITY = 300.0;

    private static Roller instance = new Roller();

    private Roller()
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
        setSpeed(0.5);
    }

    public void eject()
    {
        setSpeed(-0.5);
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

    public void setRPMSpeed()
    {
        double currentVelocity = getEncoderVelocity();
        if(Math.abs(DESIRED_VELOCITY - currentVelocity) > DESIRED_VELOCITY * 0.1)
        {
            motorSpeed = motorSpeed + Math.signum(DESIRED_VELOCITY - currentVelocity) * 0.01;
            motorSpeed = Math.min(motorSpeed, 1.0);
        }
        setSpeed(motorSpeed);
    }
}