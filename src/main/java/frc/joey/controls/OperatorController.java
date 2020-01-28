package frc.joey.controls;

public class OperatorController extends Xbox
{
    private static final OperatorController instance = new OperatorController(0);

    private OperatorController(int port)
    {
        super(port);
        System.out.println(this.getClass().getName() + " : Started Constructor");

        System.out.println(this.getClass().getName() + " : Started Constructor");
    }

    public OperatorController getInstance()
    {
        return instance;
    }
}