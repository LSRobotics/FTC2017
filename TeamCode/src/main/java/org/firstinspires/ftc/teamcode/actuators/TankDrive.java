package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.LinearOp;

/**
 * Created by LBYPatrick on 10/23/2017.
 */

public class TankDrive extends LinearOp {
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;
    public double leftPower;
    public double rightPower;

    public double speed = 0.1;
    public String LDeviceName = null;
    public String RDeviceName = null;

    public void initMotors(String LName, String RName) {
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        leftMotor = hardwareMap.get(DcMotor.class, LDeviceName);
        rightMotor = hardwareMap.get(DcMotor.class, RDeviceName);

        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void initMotors() {
        initMotors(LDeviceName,RDeviceName);
    }
    public void move(float JleftY, float JrightX) {

        double drive = -JleftY;
        leftPower    = Range.clip((-JleftY) + JrightX, -1.0, 1.0) ;
        rightPower   = Range.clip((-JleftY) - JrightX, -1.0, 1.0) ;

        // Send calculated power to wheels
        leftPower *= speed;
        rightPower *= speed;
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
    }
}
