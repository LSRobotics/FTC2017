package org.firstinspires.ftc.teamcode.actuators;

/**
 * Created by LBYPatrick on 2017/10/18.
 */

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.LinearOp;
import org.firstinspires.ftc.teamcode.Statics;

public class MecanumDrive extends LinearOp{

    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;
    public double leftFrontPower;
    public double rightFrontPower;
    public double leftBackPower;
    public double rightBackPower;



    public void initMotors() {
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        leftFront = hardwareMap.get(DcMotor.class, Statics.MecanumWheel.Front.left);
        rightFront = hardwareMap.get(DcMotor.class, Statics.MecanumWheel.Front.right);
        leftBack = hardwareMap.get(DcMotor.class, Statics.MecanumWheel.Back.left);
        rightBack = hardwareMap.get(DcMotor.class, Statics.MecanumWheel.Back.right);

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
    }

    public void mecanumControl() {
        //A little Math from https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example

        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;

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
