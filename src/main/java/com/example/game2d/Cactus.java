package com.example.game2d;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.*;

public class Cactus extends ImageView {
    private static final Random random = new Random();
    private static final String[] cactusImagePath = {
            "/Cactuses/bush1.png",
            "/Cactuses/bush2.png",
            "/Cactuses/bush3.png",
            "/Cactuses/bush4.png",
            "/Cactuses/cactus1.png",
            "/Cactuses/fence.png"
    };
    private Pane paneToGetSizes;
    private double screenPositionX = 600;
    private double screenPositionY = 220;
    private Group cactusGroup;
    private double speed = 9; // Adjust the speed as needed
    private Timeline timeline;// Adjust the speed as needed

    private double spawnInterval = 400;
    //private double scaleFactorCactus = 0.75 ;
    private double cactusWidth = 45;
    private double cactusHeight = 45;
    public Cactus(Pane pane, Group cactusGroup) {
        paneToGetSizes = pane;
        this.cactusGroup = cactusGroup;
        initializeCacti();
    }
    private Image getRandomImage() {
        return new Image(Objects.requireNonNull(
                Cactus.class.getResourceAsStream(cactusImagePath[
                        random.nextInt(cactusImagePath.length)])));
    }
    private void initializeCacti() {

        for (int i = 0; i < 5; i++) { // You can adjust the number of cacti as needed
            Image randomImage = getRandomImage();
            ImageView cactus = new ImageView(randomImage);

            cactus.setFitWidth(cactusWidth);
            cactus.setFitHeight(cactusHeight
            );

            // Set initial position off-screen
            cactus.setX(screenPositionX + i * spawnInterval + 450);
            cactus.setY(220-cactus.getFitHeight());

            // Add the cactus to the group
            cactusGroup.getChildren().add(cactus);

            // Make the cactus initially invisible
            cactus.setVisible(false);

            // Add a sequential delay between cacti appearances (adjust the duration as needed)
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 1.5), e -> {
                cactus.setVisible(true); // Make the cactus visible after the delay
                // You can add other animations or effects here
            });

            timeline = new Timeline(keyFrame);
            timeline.play();
        }

        // Create a timeline for cactus movement
        timeline = new Timeline(
                new KeyFrame(Duration.millis(30), event -> moveCacti()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void moveCacti() {
        // Move existing cacti
        for (int i = cactusGroup.getChildren().size() - 1; i >= 0; i--) {
            ImageView cactus = (ImageView) cactusGroup.getChildren().get(i);
            cactus.setX(cactus.getX() - speed);

            // Check if cactus is out of the screen
            if (cactus.getX() + cactus.getImage().getWidth() < 0) {
                // Remove the cactus from the group
                cactusGroup.getChildren().remove(cactus);
            }
        }

        // Add new cactus if needed
        double lastCactusX = getLastCactusX();
        if (lastCactusX < paneToGetSizes.getWidth() - spawnInterval +450) {
            Image randomImage = getRandomImage();
            ImageView newCactus = new ImageView(randomImage);
            newCactus.setFitWidth(cactusWidth);
            newCactus.setFitHeight(cactusHeight);
            // Set initial position off-screen
            newCactus.setX(lastCactusX + spawnInterval);
            newCactus.setY(220-newCactus.getFitHeight());

            // Add the new cactus to the group
            cactusGroup.getChildren().add(newCactus);

            // Make the new cactus initially invisible
            newCactus.setVisible(false);

            // Add a sequential delay for the new cactus appearance
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.5), e -> {
                newCactus.setVisible(true); // Make the new cactus visible after the delay
                // You can add other animations or effects here
            });

            new Timeline(keyFrame).play();
        }
    }

    private double getLastCactusX() {
        if (cactusGroup.getChildren().isEmpty()) {
            return screenPositionX;
        } else {
            ImageView lastCactus = (ImageView) cactusGroup.getChildren().get(cactusGroup.getChildren().size() - 1);
            return lastCactus.getX() + lastCactus.getImage().getWidth();
        }
    }

}
