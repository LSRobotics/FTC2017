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
        this.gamepad = gp;
    }

    private void updateControllerData() {
        current  = new DataContainer();
        this.current.DPadUp = this.gamepad.dpad_up;
        this.current.DPadDown = this.gamepad.dpad_down;
        this.current.DPadLeft = this.gamepad.dpad_left;
        this.current.DPadRight = this.gamepad.dpad_right;
        this.current.JLeftX = this.gamepad.left_stick_x;
        this.current.JLeftY = this.gamepad.left_stick_y;
        this.current.JRightX = this.gamepad.right_stick_x;
        this.current.JRightY = this.gamepad.right_stick_y;
        this.current.Cross = this.gamepad.a;
        this.current.Square = this.gamepad.x;
        this.current.Triangle = this.gamepad.y;
        this.current.Circle = this.gamepad.b;
        this.current.L1     = this.gamepad.left_bumper;
        this.current.R1     = this.gamepad.right_bumper;
        this.current.L2     = this.gamepad.left_trigger;
        this.current.R2     = this.gamepad.right_trigger;
    }

    private void updateControllerStatus() {
        this.DPadUp = this.current.DPadUp != this.previous.DPadUp;
        this.DPadDown = this.current.DPadDown != this.previous.DPadDown;
        this.DPadLeft = this.current.DPadLeft != this.previous.DPadLeft;
        this.DPadRight = this.current.DPadRight != this.previous.DPadRight;
        this.JLeftX = this.current.JLeftX != this.previous.JLeftX;
        this.JLeftY = this.current.JLeftY != this.previous.JLeftY;
        this.JRightX = this.current.JRightX != this.previous.JRightX;
        this.JRightY = this.current.JRightY != this.previous.JRightY;
        this.Cross = this.current.Cross != this.previous.Cross;
        this.Square = this.current.Square != this.previous.Square;
        this.Triangle = this.current.Triangle != this.previous.Triangle;
        this.Circle = this.current.Circle != this.previous.Circle;
        this.L1     = this.current.L1 != this.previous.L1;
        this.R1     = this.current.R1 != this.previous.R1;
        this.L2     = this.current.L2 != this.previous.L2;
        this.R2     = this.current.R2 != this.previous.R2;
    }

    public void updateStatus() {
            updateControllerData();
            updateControllerStatus();
            previous = current;
    }
}
