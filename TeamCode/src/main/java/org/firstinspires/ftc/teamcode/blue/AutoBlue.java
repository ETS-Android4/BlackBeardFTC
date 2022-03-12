package org.firstinspires.ftc.teamcode.blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.controllers.ElevatorController;
import org.firstinspires.ftc.teamcode.controllers.Level;
import org.firstinspires.ftc.teamcode.controllers.MotorController;

@Autonomous
public class AutoBlue extends LinearOpMode {

    // runtime variable
    private ElapsedTime runtime = new ElapsedTime();

    //motors
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor carousel;
    DcMotor elevatorMotor;

    Servo box;

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
        box = hardwareMap.get(Servo.class, "box");
        elevatorMotor = hardwareMap.get(DcMotor.class, "Barrel");
        MotorController controller = new MotorController(motorFrontLeft,motorBackLeft,motorFrontRight,motorBackRight);
        ElevatorController elevator = new ElevatorController(elevatorMotor, box);
        debug("Status", "Initialized"); //update the status in the Telemetry.

        waitForStart();// Wait for the game to start

        debug("Status", "Started!");

        //elevator mission
        controller.forward(DRIVE_POWER, controller.cmToTick(100));
        elevator.goToLevel(Level.LEVEL3);
        elevator.flipBox();

        MotorController.sleep(500);

        elevator.retrieveElevator();
        controller.backward(DRIVE_POWER, controller.cmToTick(100));

        //carousel mission
        controller.left(DRIVE_POWER, controller.cmToTick(180)); //drive left to get to the target
        controller.runMotorForTime(carousel, CAROUSEL_POWER, 5000); //spin the carousel to drop the duck
        controller.forward(DRIVE_POWER, controller.cmToTick(40)); //drive forward
        controller.rotationRight(DRIVE_POWER, controller.cmToTick(60)); //TURN right
        controller.forward(DRIVE_POWER, controller.cmToTick(360)); //drive forward
        /*
        //old strategy
        controller.left(DRIVE_POWER, controller.cmToTick(180)); //drive left to get to the target
        controller.runMotorForTime(carousel, CAROUSEL_POWER, 5000); //spin the carousel to drop the duck
        controller.forward(DRIVE_POWER, controller.cmToTick(40)); //drive forward
        controller.rotationRight(DRIVE_POWER, controller.cmToTick(60)); //TURN right
        controller.forward(DRIVE_POWER, controller.cmToTick(360)); //drive forward
         */
    }

    private void debug(String caption, String body){
        telemetry.addData(caption, body);
        telemetry.update();
    }

}
