package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.actuators.*;
import org.firstinspires.ftc.teamcode.databases.Statics;

public class TeleOp10 implements Runnable{

     DriveTrain dt;
     MotorControl lift;
     ServoControl grabberLeft,
                        grabberRight;
     Controller gp1, gp2;

     ElapsedTime runtime;
     LinearOpMode opMode;
     Telemetry telemetry;

     int driveMode = 2;
     int wheelMode = 1;
     boolean       isSNP = false,
                   isRSNP = false,
                   isDriveOnly,
                   isTeleOpEnded = false,
                   isForceUpdate = false,
                   isGrabberClosed = false,
                   isVisualizing = false;
    public static class DriveMode {
        final public static int OneStick = 0,
                                TwoStick = 1,
                                NFSControl = 2;

    }

    public TeleOp10(HardwareMap hwMap,Gamepad gamepad1,LinearOpMode opMode,Telemetry telemetry,boolean isDriveOnly) {
        dt = new DriveTrain(hwMap.dcMotor.get(Statics.SOPH_FL_WHEEL),
                hwMap.dcMotor.get(Statics.SOPH_RL_WHEEL),
                hwMap.dcMotor.get(Statics.SOPH_RR_WHEEL),
                hwMap.dcMotor.get(Statics.SOPH_FR_WHEEL));

        this.opMode = opMode;
        this.telemetry = telemetry;
        //jArmObj = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.jewel);
        //jArm = new ServoControl(jArmObj, true, 0.13, 0.7);

        gp1 = new Controller(gamepad1);

        gp2 = gp1;

        this.isDriveOnly = isDriveOnly;

        if(!isDriveOnly) {
            grabberLeft = new ServoControl(hwMap.get(Servo.class, Statics.SOPH_LEFT_GLYPH_GRABBER),
                    false,
                    -1,
                    1);

            grabberRight = new ServoControl(hwMap.get(Servo.class, Statics.SOPH_RIGHT_GLYPH_GRABBER),
                    true,
                    -1,
                    1);

            lift = new MotorControl(
                    hwMap.get(DcMotor.class, Statics.GLYPH_LIFT)
                    , false);
        }
        runtime = new ElapsedTime();
    }

    public void setDriveMode(int driveMode) {
        if(driveMode < 3 && driveMode > -1) {
            this.driveMode = driveMode;
        }
    }

    public void setWheelMode(int wheeMode) {
        dt.setWheelMode(wheeMode);
    }

    public void useSecondGamepad(Controller another) {
        gp2 = another;
    }

    public void setVisualizing(boolean value) {
        isVisualizing = value;
    }

    public void run() {

        runtime.reset();

        while(opMode.opModeIsActive()) {
            gp1.updateStatus();
            if(gp2 != gp1) gp2.updateStatus();

            speedControl();
            driveControl();
            if(!isDriveOnly) {
                liftControl();
                grabberControl();
            }
            isForceUpdate = false;

            if(isVisualizing) {
                showData();
            }
        }
    }

    void speedControl() {
        if(gp1.isKeyToggled(Controller.RB)) {

            isSNP = !isSNP;
            dt.updateSpeedLimit(isSNP? 0.6 : 1.0);
        }

        if(gp2.isKeyToggled(Controller.RB) && !isDriveOnly) {

            isRSNP = !isRSNP;
            lift.updateSpeedLimit(isRSNP ? 0.6 : 1.0);
        }
    }

    void grabberControl() {
        if(gp2.isKeyChanged(Controller.B)) {
            isGrabberClosed = !isGrabberClosed;
            if(!isGrabberClosed) {grabberLeft.move(Statics.GGRABBERL_OPEN);grabberRight.move(Statics.GGRABBERR_OPEN);}
            else {grabberLeft.move(Statics.GGRABBERL_CLOSE);grabberRight.move(Statics.GGRABBERR_CLOSE);}
        }
    }

    void liftControl() {
        if(isForceUpdate || gp2.isKeyChanged(Controller.dPadUp) || gp2.isKeyChanged(Controller.dPadDown)) {
            lift.moveWithButton(gp2.isKeyHeld(Controller.dPadUp),gp2.isKeyHeld(Controller.dPadDown));
        }
    }

    void driveControl() {
        switch (driveMode) {
            case DriveMode.OneStick:
                if(isForceUpdate
                        || gp1.isKeyChanged(Controller.jRightX)
                        || gp1.isKeyChanged(Controller.jRightY)
                        || gp1.isKeyChanged(Controller.jLeftX)) {

                    if(wheelMode != 0) {
                        dt.drive(
                                gp1.getValue(Controller.jLeftX),
                                -gp1.getValue(Controller.jLeftY),
                                gp1.getValue(Controller.jRightX));
                    }
                    else { //Tank Drive
                        dt.tankDrive(
                                -gp1.getValue(Controller.jLeftY),
                                gp1.getValue(Controller.jLeftX));
                    }
                }
                break;
            case DriveMode.TwoStick:
                if(isForceUpdate
                        || gp1.isKeyChanged(Controller.jLeftY)
                        || gp1.isKeyChanged(Controller.jRightX)
                        || gp1.isKeyChanged(Controller.dPadLeft)
                        || gp1.isKeyChanged(Controller.dPadRight)) {

                    dt.drive(
                            gp1.getValue(Controller.dPadRight)-gp1.getValue(Controller.dPadLeft),
                            -gp1.getValue(Controller.jLeftY),
                            gp1.getValue(Controller.jRightX));
                }
                break;
            case DriveMode.NFSControl:
                if(isForceUpdate
                        || gp1.isKeyChanged(Controller.RT)
                        || gp1.isKeyChanged(Controller.LT)
                        || gp1.isKeyChanged(Controller.jLeftX)
                        || gp1.isKeyChanged(Controller.dPadLeft)
                        || gp1.isKeyChanged(Controller.dPadRight)) {
                    dt.drive(
                            gp1.getValue(Controller.dPadRight)-gp1.getValue(Controller.dPadLeft),
                            gp1.getValue(Controller.RT)-gp1.getValue(Controller.LT),
                            gp1.getValue(Controller.jLeftX));
                }
                break;
            default : break;
        }
    }

    public DriveTrain getDriveTrain() { return dt; }

    public MotorControl getLift() { return lift; }

    public ServoControl getGrabber(boolean isLeft) {
        return isLeft? grabberLeft : grabberRight;
    }

    public void showData() {
        telemetry.addData("Status           ", "Run Time: " + runtime.toString());
        telemetry.addData("RL encoder: ", dt.getEncoderInfo(DriveTrain.Wheels.REAR_LEFT));
        telemetry.addData("RR encoder: ", dt.getEncoderInfo(DriveTrain.Wheels.REAR_RIGHT));
        telemetry.addData("RL Wheel:        ", dt.getSpeed(DriveTrain.Wheels.REAR_LEFT));
        telemetry.addData("RR Wheel:        ", dt.getSpeed(DriveTrain.Wheels.REAR_RIGHT));

        if(!isDriveOnly) {
            telemetry.addData("GGrabbers:       ", grabberLeft.getPosition());
        }

        telemetry.update();
    }
}
