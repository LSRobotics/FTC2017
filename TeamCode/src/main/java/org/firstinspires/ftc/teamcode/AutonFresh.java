package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.*;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 11/14/2017.
 */
@SuppressWarnings("StatementWithEmptyBody")
@Autonomous(name = "FRESH_FORWARD",group = "Freshman")
final public class AutonFresh extends LinearOpMode {

    private     AutonHelper     autonControl;
    private     MotorControl    intakes;
    private     MotorControl    lift;


    // Declare OpMode members
    final private ElapsedTime stageTime = new ElapsedTime();

    private void initialize() {

        MotorControl leftWheel = new MotorControl(hardwareMap.dcMotor.get(Statics.FRESH_L_WHEEL));
        MotorControl rightWheel = new MotorControl(hardwareMap.dcMotor.get(Statics.FRESH_R_WHEEL));
        intakes = new MotorControl(hardwareMap.dcMotor.get(Statics.FRESH_INTAKE),false);
        lift    = new MotorControl(hardwareMap.dcMotor.get(Statics.GLYPH_LIFT),false);

        DriveTrain tankWheel = new DriveTrain(leftWheel,rightWheel);

        autonControl = new AutonHelper(this, tankWheel,false);

    }

    private boolean wait(double seconds) {
        stageTime.reset();
        while(opModeIsActive()&&stageTime.seconds() <= seconds);
        return opModeIsActive();

    }

    @Override
    public void runOpMode() {

        initialize();
        waitForStart();

        //Take the glyph in
        if(!autonControl.runMotor(intakes,1,0)) return;
        if(!wait(1.5)) return;

        //Move forward
        if(!autonControl.drive(1,0,0.328)) return;
        if(!wait(2.0)) return;

        //Shoot the glyph out
        if(!autonControl.runMotor(intakes,-1,0)) return;

        //Move a little back for not touching the glyph
        autonControl.drive(-1, 0, 0.05);
    }
}
