package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by LBYPatrick on 10/27/2017.
 */

final public class ServoControl {

    private static Servo servoOBJ = null;
    public double servoPos = 0;
    private int servoSwitch = -1;
    private double minPos = 0;
    private double maxPos = 0;

    public ServoControl(Servo device, boolean forward, double min, double max) {
        servoOBJ = device;
        minPos = min;
        maxPos = max;
        servoOBJ.setDirection((forward?Servo.Direction.FORWARD : Servo.Direction.REVERSE));
    }
    public void moveGlyphGrabber(boolean inward){

        if(inward) servoPos -= 0.01; else servoPos += 0.01;

        //Out-of-limit detection & correction

        if(servoPos > maxPos) servoPos = maxPos;
        else if (servoPos < minPos) servoPos = minPos;

        //Set the position
        servoOBJ.setPosition(servoPos);
    }
    public int moveJewelArm() {
            servoSwitch ++;
            switch (servoSwitch) {
                case 0:servoPos = maxPos;break;
                case 1:servoPos = (maxPos + minPos)/2;break;
                case 2:servoPos = minPos; break;
                default: servoPos = maxPos;servoSwitch = 0;break;
            }

            servoOBJ.setPosition(servoPos);
            return 0;
        }
}