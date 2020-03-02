package frc.jwood.components.motor;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MySparkMax extends Motor
{
    private CANSparkMax motor;

    public MySparkMax(int port)
    {
        motor = new CANSparkMax(port, MotorType.kBrushless);
        motor.restoreFactoryDefaults();
    }

    @Override
    public void setInverted(boolean isInverted)
    {
        motor.setInverted(isInverted);
    }

    public void setReverseSoftLimitEnabled(boolean isEnabled)
    {
        
    }

    public void setReverseSoftLimitThreshold(int threshold)
    {
        
    }

    public void setReverseHardLimitEnabled(boolean isEnabled, boolean isNormallyOpen)
    {
        
    }

    public void setForwardSoftLimitEnabled(boolean isEnabled)
    {

    }

    public void setForwardSoftLimitThreshold(int threshold)
    {
       
    }

    public void setForwardHardLimitEnabled(boolean isEnabled, boolean isNormallyOpen)
    {
        
    }

    public void setNeutralMode(MyNeutralMode mode)
    {
        
    }

    public void setStatorCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {

    }

    public void setSupplyCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {

    }

    public void setOpenLoopRamp(double seconds)
    {

    }

    public CANSparkMax getSuper()
    {
        return motor;
    }
}