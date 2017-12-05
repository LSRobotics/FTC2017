package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by LBYPatrick on 2017/11/3.
 */

final public class DriveTrain {

    private              DcMotor FL                 = null;
    private              DcMotor FR                 = null;
    private              DcMotor RL                 = null;
    private              DcMotor RR                 = null;
    public               double  maxSpeed           = 1.0;
    private              double  speedLevel         = 1.0;
    private              double  frontLeftPower;
    private              double  frontRightPower;
    private              double  rearLeftPower;
    private              double  rearRightPower;
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

        this.FL = leftMotor;
        this.FR = rightMotor;

        this.FL.setDirection(DcMotor.Direction.REVERSE);
        this.FR.setDirection(DcMotor.Direction.FORWARD);

        this.is4WD = false;
    }

    public void tankDrive(double forwardBack, double rotation) {

        rotation = -rotation; // FTC 2018 tuning

        //Calculate Adequate Power Level for motors
        this.frontLeftPower = Range.clip(forwardBack + rotation, -1.0, 1.0);
        this.frontRightPower = Range.clip(forwardBack - rotation, -1.0, 1.0);
        this.frontLeftPower *= this.speedLevel;
        this.frontRightPower *= this.speedLevel;
        if (is4WD) {
            this.rearLeftPower = this.frontLeftPower;
            this.rearRightPower = this.frontRightPower;
        }

        //Pass calculated power level to motors
        this.FL.setPower(this.frontLeftPower);
        this.FR.setPower(this.frontRightPower);
        if(this.is4WD) {
            this.RL.setPower(this.rearLeftPower);
            this.RR.setPower(this.rearRightPower);
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

        this.frontLeftPower *= 0.987; this.frontRightPower *= 0.987; this.rearRightPower *= 0.987;

        // Send calculated power to motors
        this.FL.setPower(this.frontLeftPower * speedLevel);
        this.FR.setPower(this.frontRightPower * speedLevel);
        this.RL.setPower(this.rearLeftPower * speedLevel);
        this.RR.setPower(this.rearRightPower * speedLevel);
    }

    public void updateSpeedLimit(double speed) {
        this.speedLevel = speed*this.maxSpeed;

        this.FL.setPower(this.frontLeftPower * speedLevel);
        this.FR.setPower(this.frontRightPower * speedLevel);
        if(this.is4WD){
            this.RL.setPower(this.rearLeftPower * speedLevel);
            this.RR.setPower(this.rearRightPower * speedLevel);
        }

    }

    public double getEncoderInfo(int motorPosition) {
        switch (motorPosition) {
            case 0  : return this.FR.getCurrentPosition();
            case 1  : return this.FL.getCurrentPosition();
            case 2  : return this.RL.getCurrentPosition();
            case 3  : return this.RR.getCurrentPosition();
            default : return 256;
        }
    }

    public double getSpeed(int motorPosition) {
        switch (motorPosition) {
            case 0  : return this.FR.getPower();
            case 1  : return this.FL.getPower();
            case 2  : return this.RL.getPower();
            case 3  : return this.RR.getPower();
            default : return 256;
        }
    }

    public boolean get4WDStat() {return this.is4WD;}
}