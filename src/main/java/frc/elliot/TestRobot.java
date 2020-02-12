package frc.elliot;

import com.revrobotics.CANSparkMax;

public class TestRobot
{
    private static final int SHUTTLE_MOTOR_ID = 0;
    private static final int INTAKE_MOTOR_ID = 1;

    private static CANSparkMax shuttleMotor = new CANSparkMax(SHUTTLE_MOTOR_ID, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANSparkMax intakeMotor = new CANSparkMax(INTAKE_MOTOR_ID, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);

    public TestRobot()
    {
        shuttleMotor.restoreFactoryDefaults();
        intakeMotor.restoreFactoryDefaults();
    }
}