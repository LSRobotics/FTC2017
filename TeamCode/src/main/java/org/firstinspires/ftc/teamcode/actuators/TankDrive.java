package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by LBYPatrick on 10/27/2017.
 */

final public class TankDrive {
    private DcMotor LM = null;
    private DcMotor RM = null;
    public double leftPower;
    public double rightPower;

    public double speed = 0.1;

    public TankDrive(DcMotor leftMotor, DcMotor rightMotor){
        LM = leftMotor;
        RM = rightMotor;
        LM.setDirection(DcMotor.Direction.FORWARD);
        RM.setDirection(DcMotor.Direction.REVERSE);
    }

    public void move(float JleftY, float JrightX) {

        leftPower = Range.clip((-JleftY) + JrightX, -1.0, 1.0);
        rightPower = Range.clip((-JleftY) - JrightX, -1.0, 1.0);

        // Send calculated power to wheels
        leftPower *= speed;
        rightPower *= speed;
        LM.setPower(leftPower);
        RM.setPower(rightPower);
    }
}