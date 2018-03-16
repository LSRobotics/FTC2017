package org.firstinspires.ftc.teamcode.actuators;

/**
 * Created by LBYPatrick on 10/20/2017.
 */

import com.qualcomm.robotcore.hardware.Gamepad;

final public class GamepadControl {

    private Gamepad gamepad;

    //Status
    public boolean DPadUp   = false,
            DPadDown    = false,
            DPadLeft    = false,
            DPadRight   = false,
            Triangle    = false,
            Cross       = false,
            Square      = false,
            Circle      = false,
            L1          = false,
            R1          = false,
            L2          = false,
            R2          = false,
            JLeftX      = false,
            JLeftY      = false,
            JRightX     = false,
            JRightY     = false;

    public class DataContainer {
        public float L2 = 0,
                     R2 = 0,
                 JLeftX = 0,
                 JLeftY = 0,
                JRightX = 0,
                JRightY = 0;

        public boolean DPadUp= false,
                       DPadDown= false,
                       DPadLeft= false,
                       DPadRight= false,
                       Triangle= false,
                       Cross= false,
                       Square= false,
                       Circle= false,
                       L1= false,
                       R1= false;

    }


    private DataContainer previous = new DataContainer();
    public DataContainer current = new DataContainer();

    public GamepadControl(Gamepad gp) {
        gamepad = gp;
    }

    public void updateStatus() {

        //Fetch Data
        current  = new DataContainer();
        current.DPadUp = gamepad.dpad_up;
        current.DPadDown = gamepad.dpad_down;
        current.DPadLeft = gamepad.dpad_left;
        current.DPadRight = gamepad.dpad_right;
        current.JLeftX = gamepad.left_stick_x;
        current.JLeftY = gamepad.left_stick_y;
        current.JRightX = gamepad.right_stick_x;
        current.JRightY = gamepad.right_stick_y;
        current.Cross = gamepad.a;
        current.Square = gamepad.x;
        current.Triangle = gamepad.y;
        current.Circle = gamepad.b;
        current.L1     = gamepad.left_bumper;
        current.R1     = gamepad.right_bumper;
        current.L2     = gamepad.left_trigger;
        current.R2     = gamepad.right_trigger;

        //Data Comparison
        DPadUp = current.DPadUp != previous.DPadUp;
        DPadDown = current.DPadDown != previous.DPadDown;
        DPadLeft = current.DPadLeft != previous.DPadLeft;
        DPadRight = current.DPadRight != previous.DPadRight;
        JLeftX = current.JLeftX != previous.JLeftX;
        JLeftY = current.JLeftY != previous.JLeftY;
        JRightX = current.JRightX != previous.JRightX;
        JRightY = current.JRightY != previous.JRightY;
        Cross = current.Cross != previous.Cross;
        Square = current.Square != previous.Square;
        Triangle = current.Triangle != previous.Triangle;
        Circle = current.Circle != previous.Circle;
        L1     = current.L1 != previous.L1;
        R1     = current.R1 != previous.R1;
        L2     = current.L2 != previous.L2;
        R2     = current.R2 != previous.R2;

        previous = current;

    }

}
