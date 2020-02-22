package frc.elliot.driverTest;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends DifferentialDrive
{
    private static CANSparkMax rightDriveMaster = new CANSparkMax(1, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANSparkMax rightDriveSlave = new CANSparkMax(2, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANSparkMax leftDriveMaster = new CANSparkMax(3, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANSparkMax leftDriveSlave = new CANSparkMax(4, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);

    private static DriveTrain driveTrain = new DriveTrain();

    private DriveTrain()
    {
        super(leftDriveMaster, rightDriveMaster);

        rightDriveMaster.restoreFactoryDefaults();
        rightDriveSlave.restoreFactoryDefaults();
        leftDriveMaster.restoreFactoryDefaults();
        leftDriveSlave.restoreFactoryDefaults();

        rightDriveMaster.setInverted(true);
        rightDriveSlave.setInverted(true);
        leftDriveMaster.setInverted(false);
        leftDriveSlave.setInverted(false);
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