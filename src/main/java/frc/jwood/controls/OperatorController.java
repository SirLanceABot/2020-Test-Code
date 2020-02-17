package frc.jwood.controls;

import frc.jwood.robot.Port;

public class OperatorController extends Xbox
{
    private static final OperatorController instance = new OperatorController(Port.Controller.OPERATOR);

    private OperatorController(int port)
    {
        super(port);
        System.out.println(this.getClass().getName() + " : Started Constructor");

        System.out.println(this.getClass().getName() + " : Started Constructor");
    }

    public static OperatorController getInstance()
    {
        return instance;
    }
}