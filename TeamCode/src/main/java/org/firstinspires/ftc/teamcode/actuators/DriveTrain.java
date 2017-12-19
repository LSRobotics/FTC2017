package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by LBYPatrick on 2017/11/3.
 */

final public class DriveTrain {


    public enum Wheels{
        FRONT_LEFT,
        FRONT_RIGHT,
        REAR_LEFT,
        REAR_RIGHT,
    }

    private              DcMotor FL                 = null;
    private              DcMotor FR                 = null;
    private              DcMotor RL                 = null;
    private              DcMotor RR                 = null;
    private              double  maxSpeed           = 1.0;
    private              double  speedLevel         = 1.0;
    private              double  frontLeftPower     = 0;
    private              double  frontRightPower    = 0;
    private              double  rearLeftPower      = 0;
    private              double  rearRightPower     = 0;
    private              boolean is4WD              = false;

    //Constructor for 4WD
    public DriveTrain(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor rearLeftMotor, DcMotor rearRightMotor) {

        this.FL = frontLeftMotor;
        this.FR = frontRightMotor;
        this.RL = rearLeftMotor;
        this.RR = rearRightMotor;

        this.FL.setDirection(DcMotor.Direction.REVERSE);
        this.RL.setDirection(DcMotor.Direction.REVERSE);
        this.FR.setDirection(DcMotor.Direction.FORWARD);
        this.RR.setDirection(DcMotor.Direction.FORWARD);

        this.is4WD = true;
    }

    //Constructor for 2WD
    public DriveTrain(DcMotor leftMotor, DcMotor rightMotor) {

        this.RL = leftMotor;
        this.RR = rightMotor;

        this.RL.setDirection(DcMotor.Direction.REVERSE);
        this.RR.setDirection(DcMotor.Direction.FORWARD);

        this.is4WD = false;
    }

    public void setClockSpeed(double speed) {this.maxSpeed = speed;}

    public void tankDrive(double forwardBack, double rotation) {

        rotation = -rotation; // FTC 2018 tuning

        //Calculate Adequate Power Level for motors
        this.rearLeftPower = Range.clip(forwardBack + rotation, -1.0, 1.0);
        this.rearRightPower = Range.clip(forwardBack - rotation, -1.0, 1.0);

        if (is4WD) {
            this.frontLeftPower = this.rearLeftPower;
            this.frontRightPower = this.rearRightPower;
        }

        this.rearLeftPower *= this.speedLevel;
        this.rearRightPower *= this.speedLevel;
        this.frontLeftPower *= this.speedLevel;
        this.frontRightPower *= this.speedLevel;


        //Pass calculated power level to motors
        this.RL.setPower(this.rearLeftPower);
        this.RR.setPower(this.rearRightPower);
        if(this.is4WD) {
            this.FL.setPower(this.frontLeftPower);
            this.FR.setPower(this.frontRightPower);
        }

    }

    public void mecanumDrive(double sideMove, double forwardBack, double rotation) {

        forwardBack = -forwardBack;
        sideMove = -sideMove;rotation *= -0.5; //Tuning for FTC 2018

        //A little Math from https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
        double r = Math.hypot(sideMove, forwardBack);
        double robotAngle = Math.atan2(forwardBack, sideMove) - Math.PI / 4;

        this.frontLeftPower = r * Math.cos(robotAngle) + rotation;
        this.frontRightPower = r * Math.sin(robotAngle) - rotation;
        this.rearLeftPower = r * Math.sin(robotAngle) + rotation;
        this.rearRightPower = r * Math.cos(robotAngle) - rotation;

        this.frontLeftPower *= 0.987;
        this.frontRightPower *= 0.987;
        this.rearRightPower *= 0.987;

        // Send calculated power to motors
        this.FL.setPower(this.frontLeftPower * speedLevel);
        this.FR.setPower(this.frontRightPower * speedLevel);
        this.RL.setPower(this.rearLeftPower * speedLevel);
        this.RR.setPower(this.rearRightPower * speedLevel);
    }

    public void updateSpeedLimit(double speed) {
        this.speedLevel = speed*this.maxSpeed;
    }

    public double getEncoderInfo(Wheels position) {
        switch (position) {
            case FRONT_LEFT  : return this.FR.getCurrentPosition();
            case FRONT_RIGHT : return this.FL.getCurrentPosition();
            case REAR_LEFT   : return this.RL.getCurrentPosition();
            case REAR_RIGHT  : return this.RR.getCurrentPosition();
            default          : return 666; // Actually won't happen because the enum has already limited the actual parameter
        }
    }

    public double getSpeed(Wheels position) {
        switch (position) {
            case FRONT_LEFT  : return this.FR.getPower();
            case FRONT_RIGHT : return this.FL.getPower();
            case REAR_LEFT   : return this.RL.getPower();
            case REAR_RIGHT  : return this.RR.getPower();
            default          : return 666; // Won't happen because of the enum in parameter
        }
    }

    public boolean get4WDStat() {return this.is4WD;}
}