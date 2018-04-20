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

class TeleOp10 implements Runnable{

     private DriveTrain dt;
     private MotorControl lift;
     private ServoControl grabberLeft;
    private ServoControl grabberRight;
     private Controller gp1;
    private Controller gp2;

     private ElapsedTime runtime;
     private LinearOpMode opMode;
     private Telemetry telemetry;

     private int driveMode = 2;
     private int wheelMode = 1;
     private boolean       isSNP = false;
    private boolean isRSNP = false;
    private boolean isDriveOnly;
    boolean isTeleOpEnded = false;
    private boolean isForceUpdate = false;
    private boolean isGrabberClosed = false;
    private boolean isVisualizing = false;
    public static class DriveMode {
        final public static int OneStick = 0;
        final static int TwoStick = 1;
        final public static int NFSControl = 2;
        final static int ArrowKey = 3;

    }

    public TeleOp10(HardwareMap hwMap,Gamepad gamepad1,LinearOpMode opMode,Telemetry telemetry,boolean isDriveOnly) {
        dt = new DriveTrain(hwMap.dcMotor.get(Statics.SOPH_FL_WHEEL),
                hwMap.dcMotor.get(Statics.SOPH_FR_WHEEL),
                hwMap.dcMotor.get(Statics.SOPH_BL_WHEEL),
                hwMap.dcMotor.get(Statics.SOPH_BR_WHEEL));

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

            lift.updateSpeedLimit(0.25);
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

    private void speedControl() {
        if(gp1.isKeyToggled(Controller.RB)) {

            isSNP = !isSNP;
            dt.updateSpeedLimit(isSNP? 0.6 : 1.0);
        }

        if(gp2.isKeyToggled(Controller.RB) && !isDriveOnly) {

            isRSNP = !isRSNP;
            lift.updateSpeedLimit(isRSNP ? 0.15 : 0.25);
        }
    }

    private void grabberControl() {
        if(gp2.isKeyChanged(Controller.B)) {
            isGrabberClosed = !isGrabberClosed;
            if(!isGrabberClosed) {grabberLeft.move(Statics.GGRABBERL_OPEN);grabberRight.move(Statics.GGRABBERR_OPEN);}
            else {grabberLeft.move(Statics.GGRABBERL_CLOSE);grabberRight.move(Statics.GGRABBERR_CLOSE);}
        }
    }

    private void liftControl() {

            lift.moveWithButton(
                    lift.getCurrentPosition() > -900? gp2.isKeyHeld(Controller.dPadDown) : false
                    ,lift.getCurrentPosition() < 850? gp2.isKeyHeld(Controller.dPadUp) : false);
    }

    private void driveControl() {
        switch (driveMode) {
            case DriveMode.OneStick:
                if(isForceUpdate ||
                        gp1.isKeysChanged(
                                Controller.jLeftX,
                                Controller.jLeftY,
                                Controller.jRightX
                        )) {

                    //Holonomic drive if the wheel mode is not tank drive (then it would be either mecanum or omni)
                    if(wheelMode != 0) {
                        dt.drive(gp1.getValue(Controller.jLeftX),
                                -gp1.getValue(Controller.jLeftY),
                                gp1.getValue(Controller.jRightX));
                    }
                    else { //Tank Drive
                        dt.tankDrive(-gp1.getValue(Controller.jLeftY),
                                gp1.getValue(Controller.jLeftX));
                    }
                }
                break;
            case DriveMode.TwoStick:
                if(isForceUpdate
                        || gp1.isKeysChanged(
                                Controller.jLeftY,
                                Controller.jRightX,
                                Controller.dPadLeft,
                                Controller.dPadRight)) {

                    dt.drive(gp1.getValue(Controller.dPadRight)-gp1.getValue(Controller.dPadLeft),
                            -gp1.getValue(Controller.jLeftY),
                            gp1.getValue(Controller.jRightX));
                }
                break;
            case DriveMode.NFSControl:
                if(isForceUpdate || gp1.isKeysChanged(
                            Controller.RT,
                            Controller.LT,
                            Controller.jLeftX,
                            Controller.dPadLeft,
                            Controller.dPadRight)) {

                    dt.drive(gp1.getValue(Controller.dPadRight)-gp1.getValue(Controller.dPadLeft),
                            gp1.getValue(Controller.RT)-gp1.getValue(Controller.LT),
                            gp1.getValue(Controller.jLeftX));
                }
                break;
            case DriveMode.ArrowKey:
                if(isForceUpdate
                    || gp1.isKeysChanged(
                        Controller.dPadUp,
                        Controller.dPadDown,
                        Controller.dPadLeft,
                        Controller.dPadRight,
                        Controller.jLeftX)) {

                    dt.drive(gp1.getValue(Controller.dPadLeft) - gp1.getValue(Controller.dPadRight),
                            gp1.getValue(Controller.dPadUp)-gp1.getValue(Controller.dPadDown),
                            gp1.getValue(Controller.jLeftX));
                }
            default : break;
        }
    }

    private void showData() {
        telemetry.addData("Status           ", "Run Time: " + runtime.toString());
        telemetry.addData("L encoder: ", dt.getEncoderInfo(DriveTrain.Wheels.REAR_LEFT));
        telemetry.addData("RR encoder: ", dt.getEncoderInfo(DriveTrain.Wheels.REAR_RIGHT));
        telemetry.addData("RL Wheel:        ", dt.getSpeed(DriveTrain.Wheels.REAR_LEFT));
        telemetry.addData("RR Wheel:        ", dt.getSpeed(DriveTrain.Wheels.REAR_RIGHT));
        telemetry.addData("Lift Encoder", lift.getCurrentPosition());

        if(!isDriveOnly) {
            telemetry.addData("GGrabbers:       ", grabberLeft.getPosition());
        }

        telemetry.update();
    }
}
