package frc.elliot.driverTest;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends DifferentialDrive
{
    private static CANSparkMax rightFrontDrive = new CANSparkMax(1, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANSparkMax rightRearDrive = new CANSparkMax(2, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANSparkMax leftRearDrive = new CANSparkMax(3, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANSparkMax leftFrontDrive = new CANSparkMax(4, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);

    private static SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFrontDrive, rightFrontDrive);
    private static SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftRearDrive, leftFrontDrive);

    private static DriveTrain driveTrain = new DriveTrain();

    private DriveTrain()
    {
        super(leftMotors, rightMotors);

        rightFrontDrive.restoreFactoryDefaults();
        rightRearDrive.restoreFactoryDefaults();
        leftRearDrive.restoreFactoryDefaults();
        leftFrontDrive.restoreFactoryDefaults();

        rightFrontDrive.setInverted(true);
        rightRearDrive.setInverted(true);
        leftRearDrive.setInverted(false);
        leftFrontDrive.setInverted(false);
    }

    public static DriveTrain getInstance()
    {
        return driveTrain;
    }

    public void westCoastDrive(double move, double rotate, boolean isSquared)
    {
        super.arcadeDrive(move, rotate, isSquared);
    }
}