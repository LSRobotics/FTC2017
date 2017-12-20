package org.firstinspires.ftc.teamcode.actuators;


import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import android.graphics.Color;

import java.util.Queue;

/**
 * Created by LBYPatrick on 12/6/2017.
 */

final public class RGBSensorControl {

    public enum Ball {
        BLUE,
        RED,
        UNKNOWN
    }

    private final ColorSensor device;
    public int redVal = 0;
    public int blueVal = 0;
    public int greenVal = 0;
    private float [] hsvValue = new float[3];

    public RGBSensorControl (ColorSensor colorSensorObject) {this.device = colorSensorObject;}

    public Ball getBallColor() {
        Ball returnVal = Ball.UNKNOWN;

        //Update color sensor data
        updateColorData();

        Color.RGBToHSV((redVal * 255) / 800, (greenVal * 255) / 800, (blueVal * 255) / 800, hsvValue);

        if(hsvValue[1] > 0.6) {
            if (hsvValue[0] >= 210 && hsvValue[0] <= 275) returnVal = Ball.BLUE;
            else if (hsvValue[0] >= 330 || hsvValue[0] <= 40) returnVal = Ball.RED;
        }
        return returnVal;
    }

    public void updateColorData() {
        this.device.enableLed(true);
        this.redVal = device.red();
        this.greenVal = device.green();
        this.blueVal = device.blue();
        this.device.enableLed(false);
    }
}
