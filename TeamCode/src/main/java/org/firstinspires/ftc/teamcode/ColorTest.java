package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.actuators.*;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 12/7/2017.
 */
@TeleOp (name="Color Sensor Test",group="test")


public class ColorTest extends LinearOpMode {

    private RGBSensorControl armColorSensor;

    private void initialize() {
        ColorSensor ArmCSDevice = hardwareMap.colorSensor.get(Statics.ARM_COLOR);
        armColorSensor = new RGBSensorControl(ArmCSDevice);
    }

    @Override
    public void runOpMode(){

        String ballColor = null;

        initialize();
        waitForStart();

        while(opModeIsActive()){

            switch(armColorSensor.getBallColor()) {
                case 0 : ballColor = "Blue";
                         break;
                case 1 : ballColor = "Red";
                         break;
                case 2 : ballColor = "Unknown";
                         break;
            }

            armColorSensor.updateColorData();
            telemetry.addData("RGB:", "R:" + armColorSensor.redVal + " G:" + armColorSensor.greenVal + " B:" + armColorSensor.blueVal);

            telemetry.addData("Ball color",ballColor);
            telemetry.update();
        }
    }
}
