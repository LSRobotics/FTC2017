package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by LBYPatrick on 10/27/2017.
 */

public class ServoControl {


    //private static Servo servoObj = null;
    public      double  servoPos    = 0;
    private     int     servoSwitch = -1;
    private     double  minPos      = 0;
    private     double  maxPos      = 0;
    public      double  maxSpeed    = 1.0;
    private     double  speedLevel  = 1.0;

    public void updateSpeedLimit(double speed){speedLevel = speed * maxSpeed;}

    public ServoControl(Servo servoObj, boolean forward, double min, double max) {
        minPos = min;
        maxPos = max;
        servoObj.setDirection((forward?Servo.Direction.FORWARD : Servo.Direction.REVERSE));
    }
    public void moveGlyphGrabber(Servo servoObj, boolean inward){

        if(inward) servoPos -= 0.005*speedLevel;
        else       servoPos += 0.005*speedLevel;

        //Out-of-limit detection & correction

        if(servoPos > maxPos)       servoPos = maxPos;
        else if (servoPos < minPos) servoPos = minPos;

        //Set the position
        servoObj.setPosition(servoPos);
    }
    public void moveJewelArm(Servo servoObj) {
            servoSwitch ++;

            switch (servoSwitch) {
                case 0:     servoPos = maxPos;break;
                case 1:     servoPos = (maxPos + minPos)/2;break;
                case 2:     servoPos = minPos; break;
                default:    servoPos = maxPos;servoSwitch = 0;break;
            }

            servoObj.setPosition(servoPos);
        }
}