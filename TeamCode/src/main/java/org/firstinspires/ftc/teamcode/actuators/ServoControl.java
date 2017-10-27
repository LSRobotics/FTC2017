package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by LBYPatrick on 10/27/2017.
 */

final public class ServoControl {

    private static Servo servoOBJ = null;
    public double servoPos = 0;
    public int servoSwitch = 0;
    public double speed = 1.0;
    public boolean toFORWARD = true;
    public double minPos = 0;
    public double maxPos = 0;

    public ServoControl(Servo device, boolean forward, double min, double max) {
        servoOBJ = device;
        toFORWARD = forward;
        minPos = min;
        maxPos = max;
        servoOBJ.setDirection(forward?Servo.Direction.FORWARD : Servo.Direction.REVERSE);
    }

    public int move_jewelArm(boolean plus, boolean minus) {
        //Right trigger ==> Plus; Left trigger ==> Minus

        if (plus == minus) return 1;
        else {

            if(plus) servoSwitch += 1; else servoSwitch -= 1;

            if (servoSwitch > 2) servoSwitch = 2;
            else if (servoSwitch < 0) servoSwitch = 0;

            switch (servoSwitch) {
                case 0:
                    servoPos = maxPos;
                    break;
                case 1:
                    servoPos = (maxPos + minPos)/2;
                case 2:
                    servoPos = minPos;
            }

            //servoPos += ((plus? 1:0) * 0.01 * speed); // Determines whether going forward

            //Out-of-Range Detection
            //if (servoPos < minPos) servoPos = minPos;
            //else if (servoPos > maxPos) servoPos = maxPos;

            servoOBJ.setPosition(servoPos);
            return 0;
        }
    }
}
