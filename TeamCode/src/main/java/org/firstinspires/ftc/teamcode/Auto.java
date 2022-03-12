package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous
public class Auto extends LinearOpMode {

    // runtime variable
    private ElapsedTime runtime = new ElapsedTime();

    //motors
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor carousel;

    //finals
    private static final double DRIVE_POWER = 1D;
    private static final double CAROUSEL_POWER = 0.6D;

    @Override
    public void runOpMode() {
        //get all the required motors.
        motorFrontLeft = hardwareMap.dcMotor.get("fl");
        motorBackLeft = hardwareMap.dcMotor.get("bl");
        motorFrontRight = hardwareMap.dcMotor.get("fr");
        motorBackRight = hardwareMap.dcMotor.get("br");
        carousel = hardwareMap.get(DcMotor.class, "carusle");
        MotorController controller = new MotorController(motorFrontLeft,motorBackLeft,motorFrontRight,motorBackRight);
        debug("Status", "Initialized"); //update the status in the Telemetry.

        waitForStart();// Wait for the game to start

        debug("Status", "Started!");

        controller.right(DRIVE_POWER, 5000); //drive right to get to the target
        controller.runMotorForTime(carousel, CAROUSEL_POWER, 5000); //spin the carousel to drop the duck
        controller.forward(DRIVE_POWER, 1000); //drive forward
        //controller.turnLeft(DRIVE_POWER, 2000); //TURN left
        controller.forward(DRIVE_POWER, 7000); //drive forward
    }

    private void debug(String caption, String body){
        telemetry.addData(caption, body);
        telemetry.update();
    }

}
