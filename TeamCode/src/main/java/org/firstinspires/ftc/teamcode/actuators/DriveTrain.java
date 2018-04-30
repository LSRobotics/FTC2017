package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by LBYPatrick on 2017/11/3.
 */

final public class DriveTrain {

    /**
     * The positions of the wheels.
     */
    public enum Wheels{
        FRONT_LEFT,
        FRONT_RIGHT,
        REAR_LEFT,
        REAR_RIGHT,
    }

    private              MotorControl FL                 = null;
    private              MotorControl FR                 = null;
    private              MotorControl BL = null;
    private              MotorControl BR = null;
    private              double  maxSpeed           = 1.0;
    private              double  speedLevel         = 1.0;
    private              double  frontLeftPower     = 0;
    private              double  frontRightPower    = 0;
    private              double  rearLeftPower      = 0;
    private              double  rearRightPower     = 0;
    private              boolean is4WD              = false;
    private              boolean isMecanum          = false;
    private              int wheelMode = 1; //OMNI_WHEEL as default

    /**
     * Drive Mode, can be Tank, Omni or Mecanum.
     */
    public static class WheelMode {
        final public static int TANK_WHEEL = 0;
        final public static int OMNI_WHEEL = 1;
        final public static int MECANUM_WHEEL = 2;
    }

    /**
     * Constructor for 4WD Mode using MotorControl class.
     * @param frontLeftMotor front left motor.
     * @param frontRightMotor front right motor.
     * @param rearLeftMotor back left motor.
     * @param rearRightMotor back right motor.
     */
    public DriveTrain(MotorControl frontLeftMotor, MotorControl frontRightMotor, MotorControl rearLeftMotor, MotorControl rearRightMotor) {

        FL = frontLeftMotor;
        FR = frontRightMotor;
        BL = rearLeftMotor;
        BR = rearRightMotor;

        FL.setReverse(true);
        BL.setReverse(true);

        is4WD = true;
    }

    /**
     * Constructor for 4WD Mode using the built-in DcMotor Class.
     * @param frontLeftMotor front left motor.
     * @param frontRightMotor front right motor.
     * @param rearLeftMotor back left motor.
     * @param rearRightMotor back right motor.
     */
    public DriveTrain(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor rearLeftMotor, DcMotor rearRightMotor) {
        this(new MotorControl(frontLeftMotor), new MotorControl(frontRightMotor), new MotorControl(rearLeftMotor),new MotorControl(rearRightMotor));
    }

    /**
     * Constructor for 2WD using DcMotor
     * @param leftMotor
     * @param rightMotor
     */
    public DriveTrain(DcMotor leftMotor, DcMotor rightMotor) {
        this(new MotorControl(leftMotor), new MotorControl(rightMotor));
    }


    /**
     * Constructor for 2WD using MotorControl
     * @param leftMotor
     * @param rightMotor
     */
    //Constructor for 2WD
    public DriveTrain(MotorControl leftMotor, MotorControl rightMotor) {

        BL = leftMotor;
        BR = rightMotor;

        is4WD = false;
    }

    /**
     * Set wheel mode.
     * @param wheelMode To set which mode it is. There's DriveTrain.WheelMode for you to pass in.
     */
    public void setWheelMode(int wheelMode) {
        if(wheelMode >= 0 && wheelMode <= 2) {this.wheelMode = wheelMode;}
    }

    /**
     * Drives the robot.
     * @param sideMove
     * @param forwardBack
     * @param rotation
     */
    public void drive(double sideMove, double forwardBack, double rotation) {
        switch(wheelMode) {
            case WheelMode.TANK_WHEEL:
                tankDrive(forwardBack,rotation);
                break;

            case WheelMode.OMNI_WHEEL:
                omniDrive(sideMove,forwardBack,rotation);
                break;
            case WheelMode.MECANUM_WHEEL:
                mecanumDrive(sideMove,forwardBack,rotation);
        }
    }

    /**
     * Tank Drive
     * @param forwardBack
     * @param rotation
     */
    public void tankDrive(double forwardBack, double rotation) {

        rotation = -rotation; // FTC 2018 tuning

        //Calculate Adequate Power Level for motors
        rearLeftPower = Range.clip(forwardBack + rotation, -1.0, 1.0);
        rearRightPower = Range.clip(forwardBack - rotation, -1.0, 1.0);

        if (is4WD) {
            frontLeftPower = rearLeftPower;
            frontRightPower = rearRightPower;
        }
        //Pass calculated power level to motors
        BL.move(rearLeftPower);
        BR.move(-rearRightPower);
        if(is4WD) {
            FL.move(frontLeftPower);
            FR.move(-frontRightPower);
        }

    }

    /**
     * Omni Drive
     * @param sideMove
     * @param forwardBack
     * @param rotation
     */
    //From http://ftckey.com/programming/advanced-programming/
    public void omniDrive(double sideMove, double forwardBack, double rotation) {

        sideMove = -sideMove;

        FR.move(getLimited(forwardBack+sideMove-rotation));
        FL.move(getLimited(forwardBack-sideMove+rotation));
        BR.move(getLimited(forwardBack-sideMove-rotation));
        BL.move(getLimited(forwardBack+sideMove+rotation));
    }

    private double getLimited(double value) {
        if(value < -1) return -1;
        else if(value > 1) return 1;
        else return value;
    }

    /**
     * Mecanum Drive
     * @param sideMove
     * @param forwardBack
     * @param rotation
     */
    public void mecanumDrive(double sideMove, double forwardBack, double rotation) {

        //A little Math from https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
        final double r = Math.hypot(sideMove, forwardBack);
        final double robotAngle = Math.atan2(forwardBack, sideMove) - Math.PI / 4;

        frontLeftPower = r * Math.cos(robotAngle) + rotation;
        frontRightPower = r * Math.sin(robotAngle) - rotation;
        rearLeftPower = r * Math.sin(robotAngle) + rotation;
        rearRightPower = r * Math.cos(robotAngle) - rotation;

        // Send calculated power to motors
        FL.move(frontLeftPower);
        FR.move(frontRightPower);
        BL.move(rearLeftPower);
        BR.move(rearRightPower);
    }

    /**
     * Updates speed limit.
     * @param speed needs to be greater than 0, otherwise the robot would move backwards or stop moving if it is 0
     */
    public void updateSpeedLimit(double speed) {

        if(is4WD) {
            FL.updateSpeedLimit(speed);
            FR.updateSpeedLimit(speed);
        }
        BL.updateSpeedLimit(speed);
        BR.updateSpeedLimit(speed);
    }

    /**
     * Gets motor encoders' readings.
     * @param position the position of the Wheel
     * @return the motor encoder reading of the specified wheel.
     */
    public double getEncoderInfo(Wheels position) {
        switch (position) {
            case FRONT_LEFT  : return FR.getCurrentPosition();
            case FRONT_RIGHT : return FL.getCurrentPosition();
            case REAR_LEFT   : return BL.getCurrentPosition();
            case REAR_RIGHT  : return BR.getCurrentPosition();
            default          : return 666; // Actually won't happen because the enum has already limited the actual parameter
        }
    }

    /**
     * Gets motors' speeds.
     * @param position the position of the Wheel
     * @return the motor power reading of the specified wheel.
     */
    public double getSpeed(Wheels position) {
        switch (position) {
            case FRONT_LEFT  : return FR.getPower();
            case FRONT_RIGHT : return FL.getPower();
            case REAR_LEFT   : return BL.getPower();
            case REAR_RIGHT  : return BR.getPower();
            default          : return 666; // Won't happen because of the enum in parameter
        }
    }

    /**
     *
     * @return whether the DriveTrain is 4WD.
     */
    public boolean get4WDStat() {return is4WD;}
}