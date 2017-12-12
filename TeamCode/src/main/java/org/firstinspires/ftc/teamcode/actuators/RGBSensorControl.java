package org.firstinspires.ftc.teamcode.actuators;


import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import android.graphics.Color;
/**
 * Created by LBYPatrick on 12/6/2017.
 */

final public class RGBSensorControl {

    private final ColorSensor device;
    public int redVal = 0;
    public int blueVal = 0;
    public int greenVal = 0;

    public RGBSensorControl (ColorSensor colorSensorObject) {this.device = colorSensorObject;}

    public int getBallColor() {
        int returnVal = 0;

        this.device.enableLed(true);
        if (this.device.blue() > this.device.red()) returnVal = 0;
        else if (this.device.red() > this.device.blue()) returnVal = 1;
        else returnVal = 2;
        this.device.enableLed(false);
        return returnVal;
    }

    public void updateColorData() {
        this.device.enableLed(true);
        this.redVal = device.red();
        this.greenVal = device.green();
        this.blueVal = device.blue();
        this.device.enableLed(false);
    }

    public void shutdown() {
        device.enableLed(false);
    }

    public void shutdown() {
        device.enableLed(false);
    }
}
