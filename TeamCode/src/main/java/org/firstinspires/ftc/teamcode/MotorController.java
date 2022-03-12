package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class MotorController {

    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;

    public void sleep(long Time) {
        try {Thread.sleep(Time);} catch (InterruptedException e) {e.printStackTrace();}
    }

    private void resetPosition() {
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(100);
    }

    /*
        This method tells the encoder to start counting ticks.
        Note:
        You have to use `DcMotor#setTargetPosition(int)` with the ticks parameter for the method before using this method, Otherwise it will throw a `TargetPositionNotSetException`.
     */
    public void runToPosition(){
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void MotorsStop() {
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);

        resetPosition();
    }

    public MotorController(DcMotor motorFrontLeft,
                           DcMotor motorBackLeft,
                           DcMotor motorFrontRight,
                           DcMotor motorBackRight){
        this.motorFrontLeft = motorFrontLeft;
        this.motorBackLeft = motorBackLeft;
        this.motorFrontRight = motorFrontRight;
        this.motorBackRight = motorBackRight;
    }

    public void left(double power, int encoderTicks){
        motorBackRight.setTargetPosition(-encoderTicks);
        motorBackLeft.setTargetPosition(encoderTicks);
        motorFrontLeft.setTargetPosition(-encoderTicks);
        motorFrontRight.setTargetPosition(encoderTicks);

        motorBackRight.setPower(-power);
        motorBackLeft.setPower(power);
        motorFrontLeft.setPower(-power);
        motorFrontRight.setPower(power);
        runToPosition();
        waitForMotors();
        MotorsStop();
    }

    public void right(double power, int encoderTicks){
        motorBackRight.setTargetPosition(encoderTicks);
        motorBackLeft.setTargetPosition(-encoderTicks);
        motorFrontLeft.setTargetPosition(encoderTicks);
        motorFrontRight.setTargetPosition(-encoderTicks);

        motorBackRight.setPower(power);
        motorBackLeft.setPower(-power);
        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(-power);
        runToPosition();
        waitForMotors();
        MotorsStop();
    }

    public void forward(double power, int encoderTicks){
        motorBackRight.setTargetPosition(encoderTicks);
        motorBackLeft.setTargetPosition(encoderTicks);
        motorFrontLeft.setTargetPosition(encoderTicks);
        motorFrontRight.setTargetPosition(encoderTicks);

        motorBackRight.setPower(power);
        motorBackLeft.setPower(power);
        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(power);
        runToPosition();
        waitForMotors();
        MotorsStop();
    }

    public void waitForMotors(){
        while(motorBackLeft.isBusy() && motorFrontLeft.isBusy() && motorFrontRight.isBusy()){}
    }

    public void runMotorForTime(DcMotor motor,double power, long timeInMillis){
        motor.setPower(power);
        try {Thread.sleep(timeInMillis);} catch (InterruptedException e) {e.printStackTrace();}
        motor.setPower(0);
    }

}