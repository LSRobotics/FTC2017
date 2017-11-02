package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by LBYPatrick on 10/27/2017.
 */

final public class ServoControl {

    private static Servo servoOBJ = null;
    public double servoPos = 0;
    private double servoTempPos = 0;
    private boolean toOriginalDirection = true;
    public int servoSwitch = -1;
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
    public void move_glyphGrabber(){

        if(toOriginalDirection) servoTempPos += 0.001;
        if(!toOriginalDirection) servoTempPos -= 0.001;

        if(servoTempPos >= maxPos) {servoPos = maxPos; toOriginalDirection = false;}
        if(servoTempPos <= minPos) {servoPos = minPos; toOriginalDirection = true;}

    }
    public int move_jewelArm() {
            servoSwitch ++;
            switch (servoSwitch) {
                case 0:servoPos = maxPos;break;
                case 1:servoPos = (maxPos + minPos)/2;break;
                case 2:servoPos = minPos;break;
                default:servoPos = maxPos;servoSwitch = -1;break;
            }

            servoOBJ.setPosition(servoPos);
            return 0;
        }
}