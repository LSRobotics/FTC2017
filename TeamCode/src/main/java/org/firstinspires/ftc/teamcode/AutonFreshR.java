package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.AutonHelper;
import org.firstinspires.ftc.teamcode.actuators.DcMotorControl;
import org.firstinspires.ftc.teamcode.actuators.DriveTrain;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 11/14/2017.
 */
@SuppressWarnings("StatementWithEmptyBody")
@Autonomous(name = "FRESH_FORWARD_RIGHT",group = "Freshman")
final public class AutonFreshR extends LinearOpMode {

    private AutonHelper autonControl;
    private DcMotorControl GIntake;

    // Declare OpMode members
    final private ElapsedTime stageTime = new ElapsedTime();

    private void initialize() {

        DcMotor BL = hardwareMap.dcMotor.get(Statics.FRESH_L_WHEEL);
        DcMotor BR = hardwareMap.dcMotor.get(Statics.FRESH_R_WHEEL);
        DcMotor GIntakeMotor = hardwareMap.dcMotor.get(Statics.FRESH_INTAKE);

        GIntake = new DcMotorControl(GIntakeMotor,false);
        DriveTrain tankWheel = new DriveTrain(BL, BR);

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



        //Move forward
        if(!autonControl.moveForward(0.33)) return;
        if(!wait(2.0)) return;

        //Move right
        if(!autonControl.turnRight(0.325)) return;
        if(!wait(2.0)) return;

        //Shoot the glyph out
        if(!autonControl.moveLiftDown(GIntake,2.0)) return;

        //Move a little back for not touching the glyph
        autonControl.moveBack(0.05);
    }
}
