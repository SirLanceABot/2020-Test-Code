package frc.maxwell.intake;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController;

/**
 * Class for controlling the intake's roller to intake or eject power cells
 * @author Elliot Measel and Ishaan Gupta
 */
public class Roller
{
    // motor controller id's
    private static final int MASTER_MOTOR_ID = 1;
    // private static final int SLAVE_MOTOR_ID = 1;

    // pid controller constants
    private static final double kP = 0.00005; 
    private static final double kI = 0.0000004;
    private static final double kD = 0; 
    private static final double kIz = 0; 
    private static final double kFF = 0; 
    private static final double kMaxOutput = 1; 
    private static final double kMinOutput = -1;
    private static final double maxRPM = 3000;

    // initializing the motors
    private static CANSparkMax masterMotor = new CANSparkMax(MASTER_MOTOR_ID, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    // private static CANSparkMax slaveMotor = new CANSparkMax(SLAVE_MOTOR_ID, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);

    // initializing the encoder and pid controller
    private static CANEncoder encoder = masterMotor.getEncoder();
    private CANPIDController pidController = new CANPIDController(masterMotor);

    // creating the one instance of the Roller cass
    private static Roller instance = new Roller();

    /**
     * Private constructor for Roller class
     */
    private Roller()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        masterMotor.restoreFactoryDefaults();
        // slaveMotor.restoreFactoryDefaults();
        //slaveMotor.follow(masterMotor);
        encoder.setPosition(0);

        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        pidController.setOutputRange(kMinOutput, kMaxOutput);
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    /**
     * Protected method to return the one instance of the Roller
     * @return the Roller
     */
    protected static Roller getInstance()
    {
        return instance;
    } 

    /**
     * Public method to make the roller suck balls
     */
    public void intake()
    {
        setSpeed(1.0);
    }

    /**
     * Public method to make the roller spit out balls
     */
    public void eject()
    {
        setSpeed(-1.0);
    }

    /**
     * Public method to stop the roller
     */
    public void stop()
    {
        setSpeed(0.0);
    }

    /**
     * Public method to get value on encoder
     * @return encoder position
     */
    public double getEncoderValue()
    {
        return encoder.getPosition();
    }

    /**
     * getter method for the amerage of the motor
     * @return the amps the motor is currently drawing
     */
    public double getAmps()
    {
        return masterMotor.getOutputCurrent();
    }

    /**
     * Public method to reset the encoder to 0
     */
    public void resetEncoderValue()
    {
        encoder.setPosition(0.0);
    }

    /**
     * Public method to get velocity of encoder
     * @return velocity of encoder
     */
    public double getEncoderVelocity()
    {
        return encoder.getVelocity();
    }

    /**
     * Private method to set the speed of the roller
     * @param speed value from -1 to 1
     */
    private void setSpeed(double speed)
    {
        double rpmSpeed = speed * maxRPM;
        pidController.setReference(rpmSpeed, ControlType.kVelocity);
    }
}