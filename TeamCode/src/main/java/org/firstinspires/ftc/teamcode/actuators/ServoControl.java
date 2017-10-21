package org.firstinspires.ftc.teamcode.actuators;

import org.firstinspires.ftc.teamcode.LinearOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by LBYPatrick on 10/19/2017.
 */

public class ServoControl extends LinearOp {
    private Servo servoOBJ = null;
    private double servoPos = 0;
    public double speed = 0.1;
    public String deviceName;
    public boolean toFORWARD = true;

    public void initMotors() {
        servoOBJ = hardwareMap.get(Servo.class, deviceName);
        if(toFORWARD) servoOBJ.setDirection(Servo.Direction.FORWARD);
        else servoOBJ.setDirection(Servo.Direction.REVERSE);
    }
    public int move(double plus, double minus) {
        //Right trigger ==> Plus; Left trigger ==> Minus

        if ((plus - minus) == 0) return 1;
        else {
            servoPos += ((plus - minus)* speed); // Determines whether going forward

            //Out-of-Range Detection
            if (servoPos < -1) servoPos = -1;
            else if (servoPos > 1) servoPos = 1;

            servoOBJ.setPosition(servoPos);
            return 0;
        }
    }
}
