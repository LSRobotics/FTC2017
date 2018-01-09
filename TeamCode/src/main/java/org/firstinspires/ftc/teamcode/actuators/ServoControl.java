package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by LBYPatrick on 10/27/2017.
 */

final public class ServoControl {


    //private static Servo servoObj = null;
    private double  servoPos    = 0;
    private int     servoSwitch = -1;
    private double  minPos      = 0;
    private double  maxPos      = 0;
    private double  maxSpeed    = 1.0;
    private double  speedLevel  = 1.0;
    private boolean isClockWise = true;
    private double sensitivity  = 0.03;
    final private Servo servoObj;


    public double getMaxPosition() {return maxPos;}

    public void setClockSpeed(double speed) {this.maxSpeed = speed;}

    public void updateSpeedLimit(double speed){this.speedLevel = speed * this.maxSpeed;}

    public ServoControl(Servo servoObject, boolean forward, double min, double max) {
        this.minPos = min;
        this.maxPos = max;
        this.servoObj = servoObject;
        this.servoObj.setDirection((forward?Servo.Direction.FORWARD : Servo.Direction.REVERSE));
        this.isClockWise = forward;
    }
    public void moveGlyphGrabber(boolean inward){

        double currentPosBuffer = this.servoObj.getPosition();
        double expectedPosition = inward? (currentPosBuffer - (this.sensitivity*this.speedLevel))
                                        : (currentPosBuffer + (this.sensitivity*this.speedLevel));

        if(expectedPosition > this.maxPos || expectedPosition < this.minPos) return; //Quit if out of range

        if(expectedPosition < currentPosBuffer) {
            this.servoObj.setDirection(this.isClockWise ? Servo.Direction.REVERSE : Servo.Direction.FORWARD);
            this.servoObj.setPosition(-expectedPosition);
            this.servoObj.setDirection(this.isClockWise ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);
        }
        this.servoObj.setPosition(expectedPosition);

        this.servoPos = expectedPosition;
    }

    public double getPosition() {
        return this.servoPos;
    }

    public void setPosition(double position) {
        double currentPosBuffer = this.servoObj.getPosition();

        if(position < currentPosBuffer) {
            this.servoObj.setDirection(this.isClockWise ? Servo.Direction.REVERSE : Servo.Direction.FORWARD);
            this.servoObj.setPosition(-position);
            this.servoObj.setDirection(this.isClockWise ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);
        }
        this.servoObj.setPosition(position);
        this.servoPos = position;
    }
}