package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by LBYPatrick on 2017/11/3.
 */

final public class DriveTrain {

    private     static   DcMotor FL                 = null;
    private     static   DcMotor FR                 = null;
    private     static   DcMotor RL                 = null;
    private     static   DcMotor RR                 = null;
    public               double  maxSpeed           = 1.0;
    private              double  speedLevel         = 1.0;
    private              double  frontLeftPower;
    private              double  frontRightPower;
    private              double  rearLeftPower;
    private              double  rearRightPower;
    private              boolean is4WD              = false;

    //Constructor for 4WD
    public DriveTrain(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor rearLeftMotor, DcMotor rearRightMotor) {

        FL = frontLeftMotor;FR = frontRightMotor;RL = rearLeftMotor;RR = rearRightMotor;

        FL.setDirection(DcMotor.Direction.REVERSE);
        RL.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.FORWARD);
        RR.setDirection(DcMotor.Direction.FORWARD);

        is4WD = true;
    }
    //Constructor for 2WD
    public DriveTrain(DcMotor leftMotor, DcMotor rightMotor) {

        FL = leftMotor;FR = rightMotor;

        FL.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.FORWARD);

        is4WD = false;
    }

    public void tankDrive(double forwardBack, double rotation) {

        forwardBack = -forwardBack;
        rotation = -rotation; // FTC 2018 tuning

        //Calculate Adequate Power Level for motors
        frontLeftPower = Range.clip(forwardBack + rotation, -1.0, 1.0);
        frontRightPower = Range.clip(forwardBack - rotation, -1.0, 1.0);
        frontLeftPower *= speedLevel;frontRightPower *= speedLevel;
        if (is4WD) {rearLeftPower = frontLeftPower; rearRightPower = frontRightPower;}

        //Pass calculated power level to motors
        FL.setPower(frontLeftPower);
        FR.setPower(frontRightPower);
        if(is4WD) {
            RL.setPower(rearLeftPower);
            RR.setPower(rearRightPower);
        }

    }

    public void mecanumDrive(double sideMove, double forwardBack, double rotation) {

        forwardBack = -forwardBack;
        sideMove = -sideMove;rotation *= -0.5; //Tuning for FTC 2018

        //A little Math from https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
        double r = Math.hypot(sideMove, forwardBack);
        double robotAngle = Math.atan2(forwardBack, sideMove) - Math.PI / 4;

        frontLeftPower = r * Math.cos(robotAngle) + rotation;
        frontRightPower = r * Math.sin(robotAngle) - rotation;
        rearLeftPower = r * Math.sin(robotAngle) + rotation;
        rearRightPower = r * Math.cos(robotAngle) - rotation;

        frontLeftPower *= 0.987; frontRightPower *= 0.987; rearRightPower *= 0.987;

        // Send calculated power to motors
        FL.setPower(frontLeftPower * speedLevel);
        FR.setPower(frontRightPower * speedLevel);
        RL.setPower(rearLeftPower);
        RR.setPower(rearRightPower * speedLevel);
    }

    public void updateSpeedLimit(double speed) {
        speedLevel = speed*maxSpeed;

        FL.setPower(frontLeftPower * speedLevel);
        FR.setPower(frontRightPower * speedLevel);
        if(is4WD){
            RL.setPower(rearLeftPower * speedLevel);
            RR.setPower(rearRightPower * speedLevel);
        }

    }

    public double getEncoderInfo(int motorPosition) {
        switch (motorPosition) {
            case 0  : return FR.getCurrentPosition();
            case 1  : return FL.getCurrentPosition();
            case 2  : return RL.getCurrentPosition();
            case 3  : return RR.getCurrentPosition();
            default : return 256;
        }
    }

    public double getSpeed(int motorPosition) {
        switch (motorPosition) {
            case 0  : return FR.getPower();
            case 1  : return FL.getPower();
            case 2  : return RL.getPower();
            case 3  : return RR.getPower();
            default : return 256;
        }
    }

    public boolean get4WDStat() {return is4WD;}
}