package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by LBYPatrick on 10/27/2017.
 */

final public class ServoControl {


    //private static Servo servoObj = null;
    private double  minPos;
    private double  maxPos;
    private double  speedLimit  = 0.03;
    private boolean isClockWise;
    final private Servo servo;


    public double getMaxPosition() {return maxPos;}

    public void updateSpeedLimit(double speed){this.speedLimit = speed;}
    
    public double getLimitedSpeed(double speed) {return speed*speedLimit;}
    
    public ServoControl(Servo servoObject, boolean isForward, double min, double max) {
        minPos = min;
        maxPos = max;
        servo = servoObject;
        servo.setDirection((isForward?Servo.Direction.FORWARD : Servo.Direction.REVERSE));
        isClockWise = isForward;
    }
    public void moveGlyphGrabber(boolean inward){
        
        final double currentPosBuffer = servo.getPosition();
        final double expectedPosition = inward? (currentPosBuffer - (speedLimit*speedLimit))
                                        : (currentPosBuffer + (speedLimit*speedLimit));

        if(expectedPosition > maxPos || expectedPosition < minPos) return; //Quit if out of range

        if(expectedPosition < currentPosBuffer) {
            servo.setDirection(isClockWise ? Servo.Direction.REVERSE : Servo.Direction.FORWARD);
            servo.setPosition(-expectedPosition);
            servo.setDirection(isClockWise ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);
        }
        servo.setPosition(expectedPosition);

    }

    public double getPosition() {
        return servo.getPosition();
    }

    public void move(double position) {
        double currentPosBuffer = servo.getPosition();

        if(position < currentPosBuffer) {
            servo.setDirection(isClockWise ? Servo.Direction.REVERSE : Servo.Direction.FORWARD);
            servo.setPosition(-position);
            servo.setDirection(isClockWise ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);
        }
        servo.setPosition(position);
    }
}