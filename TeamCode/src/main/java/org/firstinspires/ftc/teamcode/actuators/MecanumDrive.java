package org.firstinspires.ftc.teamcode.actuators;

/**
 * Created by LBYPatrick on 2017/10/18.
 */

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.LinearOp;

public class MecanumDrive extends LinearOp{

    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;
    public double leftFrontPower;
    public double rightFrontPower;
    public double leftBackPower;
    public double rightBackPower;
    public double speed = 0.1;
    public String FLDeviceName;
    public String FRDeviceName;
    public String BLDeviceName;
    public String BRDeviceName;

    public void initMotors() {
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        leftFront = hardwareMap.get(DcMotor.class, FLDeviceName);
        rightFront = hardwareMap.get(DcMotor.class, FRDeviceName);
        leftBack = hardwareMap.get(DcMotor.class, BLDeviceName);
        rightBack = hardwareMap.get(DcMotor.class, BRDeviceName);

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
    }

    public void move(float leftX, float leftY, float rightX, float rightY) {
        //A little Math from https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example

        double r = Math.hypot(leftX, leftY);
        double robotAngle = Math.atan2(leftY, leftX) - Math.PI / 4;

        // Setup a variable for each drive wheel to save power level for telemetry

        leftFrontPower = r * Math.cos(robotAngle) + rightX;
        rightFrontPower = r * Math.sin(robotAngle) - rightX;
        leftBackPower = r * Math.sin(robotAngle) + rightX;
        rightBackPower = r * Math.cos(robotAngle) - rightX;

        // Send calculated power to wheels
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftBack.setPower(leftBackPower);
        rightBack.setPower(rightBackPower);
    }
}
