package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.controllers.MotorController.sleep;
import static org.firstinspires.ftc.teamcode.controllers.MotorController.tick;

public class ElevatorController {

    DcMotor elevatorMotor;
    Servo box;

    private int currentLevel = 0;

    private static final double ELEVATOR_POWER = 1;
    private static final double KP = 0.1;
    private static int ticksToLevel1 = 0;
    private static int ticksToLevel2 = 0;
    private static int ticksToLevel3 = 0;
    private static final double flipBoxPosition = 0;

    public int cmToTick(double cm){
        return (int)(tick * cm);
    }

    public ElevatorController(DcMotor elevatorMotor, Servo box){
        this.elevatorMotor = elevatorMotor;
        this.box = box;
        //2000 is just a pseudo number, replace it with the real number.
        ticksToLevel1 = cmToTick(2000);
        ticksToLevel2 = cmToTick(2000);
        ticksToLevel3 = cmToTick(2000);
    }

    private void resetPosition() {
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(100);
    }

    public void goToLevel(Level level){
        if(level == Level.LEVEL1) {
            goToLevelTicks(ticksToLevel1);
            currentLevel = elevatorMotor.getCurrentPosition();
        }
        else if(level == Level.LEVEL2) {
            goToLevelTicks(ticksToLevel2);
            currentLevel = elevatorMotor.getCurrentPosition();
        }
        else if(level == Level.LEVEL3) {
            goToLevelTicks(ticksToLevel3);
            currentLevel = elevatorMotor.getCurrentPosition();
        }
        else
            throw new EnumConstantNotPresentException(Level.class, "How??? There are cases for all Level options... How???");
    }

    public void retrieveElevator(){
        goToLevelTicksPower(currentLevel, -ELEVATOR_POWER);
    }

    public void flipBox(){
        box.setPosition(flipBoxPosition);
    }

    private void goToLevelTicks(int ticksToLevel){
        goToLevelTicksPower(ticksToLevel, ELEVATOR_POWER);
    }

    private void goToLevelTicksPower(int ticksToLevel, double power){
        elevatorMotor.setTargetPosition(ticksToLevel);
        elevatorMotor.setPower(power);

        runToPosition();
        /*
           Some PID control to make the box always relative to the ground.
           (This is probably a very very very very bad implementation)
         */
        while(elevatorMotor.isBusy()){
            box.setPosition((ticksToLevel - elevatorMotor.getCurrentPosition()) * KP);
        }
        MotorsStop();
    }

    private void runToPosition(){
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void MotorsStop() {
        elevatorMotor.setPower(0);

        resetPosition();
    }

}
