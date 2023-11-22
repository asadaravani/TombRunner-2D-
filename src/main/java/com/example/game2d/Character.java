package com.example.game2d;

import java.util.*;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Character extends ImageView{

    private static ImageView charImageView;
    private static Timeline walkTimeLine;
    private static Timeline runTimeLine;
    private static Timeline whileJumpingTimeline;
    private static PauseTransition slideTransition;
    private static PauseTransition jumpTransition;
    private static int walkFrame =0;
    private static int runFrame =0;
    private static boolean isRunning = false;
    private static final String[] walkingImgPath = {
            "/Images/character_femalePerson_walk0.png",
            "/Images/character_femalePerson_walk1.png",
            "/Images/character_femalePerson_walk2.png",
            "/Images/character_femalePerson_walk3.png",
            "/Images/character_femalePerson_walk4.png",
            "/Images/character_femalePerson_walk5.png",
            "/Images/character_femalePerson_walk6.png",
            "/Images/character_femalePerson_walk7.png"
    };
    private static final  String[] runningImgPath = {
            "/Images/character_femalePerson_run0.png",
            "/Images/character_femalePerson_run1.png",
            "/Images/character_femalePerson_run2.png"
    };
    public Character(ImageView charImageView){
        this.charImageView = charImageView;
    }

    public static ImageView getCharImageView() {
        return charImageView;
    }

    public static void walk(){
        isRunning = false;
        stopAllTimelines();
        walkTimeLine = new Timeline(new KeyFrame(Duration.millis(200), event -> {
            String imagePath = walkingImgPath[walkFrame];
            Image walkingImage = new Image(Objects.requireNonNull(Character.class.getResource(imagePath)).toExternalForm());
            charImageView.setImage(walkingImage);

            walkFrame = (walkFrame + 1) % walkingImgPath.length;
        }));

        walkTimeLine.setCycleCount(Timeline.INDEFINITE);

        walkTimeLine.play();
    }
    public static void run(){
        isRunning = true;
        stopAllTimelines();
        runTimeLine = new Timeline(new KeyFrame(Duration.millis(150), event -> {
            String imagePath = runningImgPath[runFrame];
            Image walkingImage = new Image(Objects.requireNonNull(Character.class.getResource(imagePath)).toExternalForm());
            charImageView.setImage(walkingImage);

            runFrame = (runFrame + 1) % runningImgPath.length;
        }));

        runTimeLine.setCycleCount(Timeline.INDEFINITE);
        runTimeLine.play();
    }
    public static void jump() {
        stopAllTimelines();

        double jumpDuration = 1200; // Adjust this value to control the jump duration in milliseconds
        double jumpHeight = -70;
        boolean wasRunning = isRunning;// Adjust this value to control the jump height
        // Set the jump image
        Image jumpImg = new Image(Objects.requireNonNull(Character.class.getResource("/Images/character_femalePerson_run0.png")).toExternalForm());
        charImageView.setImage(jumpImg);

        jumpTransition = new PauseTransition(Duration.millis(jumpDuration));
        jumpTransition.setOnFinished(event -> {
            // Restore the walking or running animation after the jump
            if (wasRunning) {
                run();
               // System.out.println("inside run");
            } else {
                walk();
                //System.out.println("inside walk");
            }
        });

        // Create a timeline for adjusting the character's Y position (simulating jumping)
        whileJumpingTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(charImageView.translateYProperty(), 0)),
                new KeyFrame(Duration.millis(jumpDuration / 4), new KeyValue(charImageView.translateYProperty(), jumpHeight)),
                new KeyFrame(Duration.millis(jumpDuration / 2), new KeyValue(charImageView.translateYProperty(), 0))
        );

        // Play the jump timeline and the pause transition
        whileJumpingTimeline.setCycleCount(1);
        whileJumpingTimeline.play();
        jumpTransition.play();
    }
    public static void slide(){
        stopAllTimelines();
        slideTransition = new PauseTransition(Duration.millis(1000));
        Image slideImg = new Image(Objects.requireNonNull(Character.class.getResource("/Images/character_femalePerson_slide.png")).toExternalForm());
        charImageView.setImage(slideImg);
        slideTransition.setOnFinished(event -> {
            if (isRunning) {
                run();
            } else {
                walk();
            }
        });
        slideTransition.play();
    }
    public static void stopAllTimelines(){
        if (walkTimeLine != null) {
            walkTimeLine.stop();
        }
        if (runTimeLine != null) {
            runTimeLine.stop();
        }
        if (jumpTransition != null) {
            jumpTransition.stop();
        }

    }
    public static boolean isRunning() {
        return isRunning;
    }

}
