package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by LBYPatrick on 10/27/2017.
 */

final public class ServoControl {


    //private static Servo servoObj = null;
    private     double  servoPos    = 0;
    private     int     servoSwitch = -1;
    private     double  minPos      = 0;
    private     double  maxPos      = 0;
    private     double  maxSpeed    = 1.0;
    private     double  speedLevel  = 1.0;
    private final Servo   servoObj;


    public double getMaxPos() {return maxPos;}

    public void setClockSpeed(double speed) {this.maxSpeed = speed;}

    public void updateSpeedLimit(double speed){this.speedLevel = speed * this.maxSpeed;}

    public ServoControl(Servo servoObject, boolean forward, double min, double max) {
        this.minPos = min;
        this.maxPos = max;
        this.servoObj = servoObject;
        this.servoObj.setDirection((forward?Servo.Direction.FORWARD : Servo.Direction.REVERSE));
    }
    public void moveGlyphGrabber(boolean inward){

        if(inward) this.servoPos -= 0.03*this.speedLevel;
        else       this.servoPos += 0.03*this.speedLevel;

        //Out-of-limit detection & correction

        if(this.servoPos > this.maxPos)       this.servoPos = this.maxPos;
        else if (this.servoPos < this.minPos) this.servoPos = this.minPos;

        //Set the position
        this.servoObj.setPosition(this.servoPos);
    }
    public void moveJewelArm(Servo servoObj) {
            this.servoSwitch ++;

            switch (this.servoSwitch) {
                case 0:
                    this.servoPos = this.maxPos;
                    break;
                case 1:
                    this.servoPos = (this.maxPos + this.minPos)/2;
                    break;
                case 2:
                    this.servoPos = this.minPos;
                    break;
                default:
                    this.servoPos = this.maxPos;
                    this.servoSwitch = 0;break;
            }

            this.servoObj.setPosition(this.servoPos);
        }

    public double getPos() {
        return this.servoPos;
    }
    public void setPos(double position) {this.servoObj.setPosition(position);}
}