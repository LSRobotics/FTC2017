package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by LBYPatrick on 10/27/2017.
 */

final public class MecanumDrive {

    private static DcMotor FL = null;
    private static DcMotor FR = null;
    private static DcMotor BL = null;
    private static DcMotor BR = null;
    public double leftFrontPower;
    public double rightFrontPower;
    public double leftBackPower;
    public double rightBackPower;
    public double speed = 1.0;

    public MecanumDrive(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor){
        FL = frontLeftMotor;
        FR = frontRightMotor;
        BL = backLeftMotor;
        BR = backRightMotor;

        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);

    }

    final public void move(double leftX, double leftY, double rightX) {

        leftX = -leftX; //Essential for FTC 2018
        rightX *= -0.5;

        //A little Math from https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
        double r = Math.hypot(leftX, leftY);
        double robotAngle = Math.atan2(leftY, leftX) - Math.PI / 4;


        // Setup a variable for each drive wheel to save power level for telemetry

        leftFrontPower = r * Math.cos(robotAngle) + rightX;
        rightFrontPower = r * Math.sin(robotAngle) - rightX;
        leftBackPower = r * Math.sin(robotAngle) + rightX;
        rightBackPower = r * Math.cos(robotAngle) - rightX;

        leftFrontPower *= speed * 0.987;
        rightFrontPower *= speed * 0.987;
        leftBackPower *= speed;
        rightBackPower *= speed * 0.987;
        // Send calculated power to wheels
        FL.setPower(leftFrontPower);
        FR.setPower(rightFrontPower);
        BL.setPower(leftBackPower);
        BR.setPower(rightBackPower);
    }
}