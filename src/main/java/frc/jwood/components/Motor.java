package frc.jwood.components;

public class Motor
{
    public enum MotorType
    {
        kFalcon, k775, kRedLine, kCim, kBosch;
    }

    public enum MotorControllerType
    {
        kTalonFX, kTalonSRX, kWPI_TalonFX, kWPI_TalonSRX, kSparkMax;
    }

    private int motorPort = 0;

    public Motor(int port)
    {
        motorPort = port;
    }
}

