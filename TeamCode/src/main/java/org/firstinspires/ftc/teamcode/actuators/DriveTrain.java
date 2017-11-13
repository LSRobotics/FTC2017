package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by LBYPatrick on 2017/11/3.
 */

public class DriveTrain {

    public      static   DcMotor FL                 = null;
    public      static   DcMotor FR                 = null;
    public      static   DcMotor RL                 = null;
    public      static   DcMotor RR                 = null;
    public               double  maxSpeed           = 1.0;
    private              double  speedLevel         = 1.0;
    public               double  frontLeftPower;
    public               double  frontRightPower;
    public               double  rearLeftPower;
    public               double  rearRightPower;
    private              boolean is4WD              = false;

    public void updateSpeedLimit(double speed) {
        speedLevel = speed*maxSpeed;

        FL.setPower(frontLeftPower * speedLevel);
        FR.setPower(frontRightPower * speedLevel);
        if(is4WD){
            RL.setPower(rearLeftPower * speedLevel);
            RR.setPower(rearRightPower * speedLevel);
        }

    }
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

    final public void tankDrive(double joystickLeftY, double joystickRightX) {


        //Calculate Adequate Power Level for motors
        frontLeftPower = Range.clip((-joystickLeftY) + joystickRightX, -1.0, 1.0);
        frontRightPower = Range.clip((-joystickLeftY) - joystickRightX, -1.0, 1.0);
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

    final public void mecanumDrive(double joystickLeftX, double joystickLeftY, double joystickRightX) {

        joystickLeftX = -joystickLeftX;joystickRightX *= -0.5; //Tuning for FTC 2018

        //A little Math from https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
        double r = Math.hypot(joystickLeftX, joystickLeftY);
        double robotAngle = Math.atan2(joystickLeftY, joystickLeftX) - Math.PI / 4;

        frontLeftPower = r * Math.cos(robotAngle) + joystickRightX;
        frontRightPower = r * Math.sin(robotAngle) - joystickRightX;
        rearLeftPower = r * Math.sin(robotAngle) + joystickRightX;
        rearRightPower = r * Math.cos(robotAngle) - joystickRightX;

        frontLeftPower *= 0.987; frontRightPower *= 0.987; rearRightPower *= 0.987;

        // Send calculated power to motors
        FL.setPower(frontLeftPower * speedLevel);
        FR.setPower(frontRightPower * speedLevel);
        RL.setPower(rearLeftPower);
        RR.setPower(rearRightPower * speedLevel);
    }
}
