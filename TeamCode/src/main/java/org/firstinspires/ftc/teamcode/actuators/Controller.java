package org.firstinspires.ftc.teamcode.actuators;

/**
 * Created by LBYPatrick on 10/20/2017.
 */

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * This Controller class is copied from GamepadB in LBYPatrick/2018Robot
 * (Which would be merged to LSRobotics/2018Robot when FRC 2018 ended)
 * Some small changes has been made due to different APIs in FTC
 */

final public class Controller {

    final private static int NUM_PRECISE_KEY = 6;
    final private static int NUM_NORMAL_KEY = 14;
    private boolean isDebug = false;

    //Indexes for the buttons
    final public static int jLeftX = 0,
            jLeftY = 1,
            jRightX = 2,
            jRightY = 3,
            LT = 4,
            RT = 5,
            jLeftDown = 6,
            jRightDown = 7,
            A = 8,
            B = 9,
            X = 10,
            Y = 11,
            LB = 12,
            RB = 13,
            back = 14,
            start = 15,
            dPadUp = 16,
            dPadDown = 17,
            dPadLeft = 18,
            dPadRight = 19;

    private ValueContainer previous = new ValueContainer();
    private Gamepad xGP;
    private boolean[] state = new boolean[NUM_NORMAL_KEY + NUM_PRECISE_KEY];


    public void setDebugMode(boolean value) {
        isDebug = value;
    }
    private class ValueContainer {

        public double[] preciseKey = new double[NUM_PRECISE_KEY];
        public boolean[] normalkey = new boolean[NUM_NORMAL_KEY];


        public ValueContainer() {
            for (int i = 0; i < NUM_PRECISE_KEY; ++i) { preciseKey[i] = 0; }
            for (int i = 0; i < NUM_NORMAL_KEY; ++i) { normalkey[i] = false; }
        }
    }

    public Controller(Gamepad gamepad) {
        xGP = gamepad;
        for(int i = 0; i < state.length; ++i) { state[i] = false; }
    }

    public boolean isKeyChanged(int key) {
        return state[key];
    }

    public boolean isKeyHeld(int key) {
        return getValue(key) > 0;
    }

    public boolean isGamepadChanged() {
        for(boolean i : state) {
            if(i) return true;
        }
        return false;
    }

    public double getValue(int key) {
        if(key < NUM_PRECISE_KEY) return previous.preciseKey[key];
        else return previous.normalkey[key-NUM_PRECISE_KEY]? 1 : 0;
    }

    public boolean isKeyToggled(int key) {
        return isKeyChanged(key) && (getValue(key) > 0);
    }

    public void updateStatus() {

        try {
            ValueContainer current = new ValueContainer();

            //data Collection
            current.preciseKey[jLeftX] = xGP.left_stick_x;
            current.preciseKey[jLeftY] = xGP.left_stick_y;
            current.preciseKey[jRightX] = xGP.right_stick_x;
            current.preciseKey[jRightY] = xGP.right_stick_y;
            current.preciseKey[LT] = xGP.left_trigger;
            current.preciseKey[RT] = xGP.right_trigger;
            current.normalkey[A - NUM_PRECISE_KEY] = xGP.a;
            current.normalkey[B - NUM_PRECISE_KEY] = xGP.b;
            current.normalkey[X - NUM_PRECISE_KEY] = xGP.x;
            current.normalkey[Y - NUM_PRECISE_KEY] = xGP.y;
            current.normalkey[LB - NUM_PRECISE_KEY] = xGP.left_bumper;
            current.normalkey[RB - NUM_PRECISE_KEY] = xGP.right_bumper;
            current.normalkey[back - NUM_PRECISE_KEY] = xGP.back;
            current.normalkey[start - NUM_PRECISE_KEY] = xGP.start;
            current.normalkey[jLeftDown - NUM_PRECISE_KEY] = xGP.left_stick_button;
            current.normalkey[jRightDown - NUM_PRECISE_KEY] = xGP.right_stick_button;

            //Dpad -- No POV in FTC, makes life easier...
            current.normalkey[dPadUp - NUM_PRECISE_KEY] = xGP.dpad_up;
            current.normalkey[dPadDown - NUM_PRECISE_KEY] = xGP.dpad_down;
            current.normalkey[dPadLeft - NUM_PRECISE_KEY] = xGP.dpad_left;
            current.normalkey[dPadRight - NUM_PRECISE_KEY] = xGP.dpad_right;
            //State Comparison

            for (int i = 0; i < NUM_PRECISE_KEY; ++i) {
                state[i] = current.preciseKey[i] != previous.preciseKey[i];
            }

            for (int i = 0; i < NUM_NORMAL_KEY; ++i) {
                state[i + NUM_PRECISE_KEY] = current.normalkey[i] != previous.normalkey[i];
            }

            previous = current;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
