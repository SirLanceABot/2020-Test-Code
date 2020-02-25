package frc.elliot;

import frc.elliot.components.Intake;
import frc.elliot.components.Roller;
import frc.elliot.controls.DriverController;
import frc.elliot.driverTest.Xbox;
import frc.elliot.driverTest.Xbox.Axis;
import frc.elliot.driverTest.DriveTrain;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.I2C;

public class MyRobot
{
    //private static ShooterTest shooter = new ShooterTest();
    //private static Roller roller = Roller.getInstance();
    //private static DriverController driverController = DriverController.getInstance();
    //private static Intake intake = Intake.getInstance();
    private static AHRS navX = new AHRS(I2C.Port.kMXP);

    private static DriveTrain driveTrain = DriveTrain.getInstance();

    private static CANSparkMax motor = new CANSparkMax(4, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);

    private static Xbox xbox = Xbox.getInstance();

    private static double move;
    private static double rotate;
    private static boolean isSquared = false;

    public void myRobot()
    {
        System.out.println(this.getClass().getName() + " : Started Constructor");
        System.out.println("*** ELLIOT's Test Code ***");

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
        //move = xbox.getRawAxis(Axis.kLeftY);
        //rotate = xbox.getRawAxis(Axis.kRightX);

        motor.set(0.1);

        //driveTrain.westCoastDrive(move / 10.0, rotate / 10.0, isSquared);

        //System.out.println(navX.getRawGyroX());
        //System.out.println(navX.getRawGyroY());
        //System.out.println(navX.getRawGyroZ());

        //shooter.teleopPeriodic();
        // if (xbox.getRawButton(Xbox.Button.kA))
        //     roller.intake();
        // else if (xbox.getRawButton(Xbox.Button.kB))
        //     roller.eject();
        // else
        //     roller.stop();

        // if (driverController.getRawButton(Xbox.Button.kA))
        // {

        // }

        //intake.runFSM();
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