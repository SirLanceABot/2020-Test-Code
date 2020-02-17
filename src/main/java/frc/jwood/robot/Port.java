package frc.jwood.robot;

public class Port
{
    public class Motor
    {
        public static final int DRIVETRAIN_FRONT_LEFT = 1;

        public static final int INTAKE_ROLLER = 12;
    }

    public class Pneumatic
    {
        public static final int SHIFTER_RETRACT = 0;
        public static final int SHIFTER_EXTEND = 1;
    }

    public class Sensor
    {
        public static final int SHUTTLE_1 = 0;
        public static final int SHUTTLE_2 = 1;
    }

    public class Controller
    {
        public static final int DRIVER = 0;
        public static final int OPERATOR = 1;
    }
}