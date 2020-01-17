/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.elliot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This sample program shows how to control a motor using a joystick. In the
 * operator control part of the program, the joystick is read and the value is
 * written to the motor.
 *
 * <p>Joystick analog values range from -1 to 1 and speed controller inputs also
 * range from -1 to 1 making it easy to work together.
 */
public class ShooterTest
{

  private WPI_TalonSRX masterMotor;
  private WPI_TalonSRX slaveMotor;
  private Joystick xbox;
  private int speed = 0;
 

  public ShooterTest()
  {
    masterMotor = new WPI_TalonSRX(0);
    slaveMotor = new WPI_TalonSRX(1);
    masterMotor.configFactoryDefault();
    slaveMotor.configFactoryDefault();
    slaveMotor.follow(masterMotor);

    xbox = new Joystick(0);
  }

  public void teleopPeriodic() 
  {
    boolean isLeftButtonPressed = xbox.getRawButtonPressed(5);
    boolean isRightButtonPressed = xbox.getRawButtonPressed(6);
    boolean isBackButtonPressed = xbox.getRawButtonPressed(7);
    boolean isStartButtonPressed = xbox.getRawButtonPressed(8);
    boolean isAButtonPressed = xbox.getRawButtonPressed(1);

    if (isAButtonPressed)
        speed = 0;
    else if (isLeftButtonPressed && speed > -100)
        speed = speed - 10;
    else if (isRightButtonPressed && speed < 100)
        speed = speed + 10;
    else if (isBackButtonPressed && speed > -100)
        speed = speed - 1;
    else if (isStartButtonPressed && speed < 100)
        speed = speed + 1;

    masterMotor.set(ControlMode.PercentOutput, speed / 100.0);
    System.out.println(speed);
  }
}
