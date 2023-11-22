package com.example.game2d;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.*;

public class Background {
    private final String[] cloudImagePath = {
            "/Images/cloud1.png",
            "/Images/cloud2.png",
            "/Images/cloud3.png",
            "/Images/cloud4.png"
    };
    private Pane pane;
    private Group groupForClouds;
    private ImageView smogeImg;
    private Timeline cloudAnimation;
    private double lastCloudSpawnX = - CLOUD_SPACING;
    private static final double CLOUD_SPACING = 20.0;
    public Background(Group groupBack, ImageView backgroundImgVw, Pane pane){
        groupForClouds = groupBack;
        smogeImg = backgroundImgVw;
        this.pane = pane;
        setBackImage();
        initializeCloudAnimation();
    }
    private void setBackImage(){
        Image backgroundImg = new Image(Objects.requireNonNull(getClass().getResource("/Images/cloudLayerB1.png")).toExternalForm());
        smogeImg.setImage(backgroundImg);
        smogeImg.setOpacity(0.7);
    }
    private Image getRandomCloud(){
        //System.out.println("Inside getRandom");
        Random random = new Random();
        String randomCloudPath = cloudImagePath[random.nextInt(cloudImagePath.length)];
        return new Image(Objects.requireNonNull(getClass().getResource(randomCloudPath)).toExternalForm());
    }
    private void initializeCloudAnimation() {
        //System.out.println("inside initialize");
        cloudAnimation = new Timeline(
                new KeyFrame(Duration.millis(30), event -> moveClouds())
        );
        cloudAnimation.setCycleCount(Timeline.INDEFINITE);
        cloudAnimation.play();
    }

    private void moveClouds() {
        List<Node> cloudsToRemove = new ArrayList<>();

        for (Node cloud : groupForClouds.getChildren()) {
            cloud.setLayoutX(cloud.getLayoutX() - 1); // Adjust the speed as needed

            // Check if the cloud is out of the screen
            if (cloud.getLayoutX() + cloud.getBoundsInLocal().getWidth() < 0) {
                cloudsToRemove.add(cloud);
            }
        }

        // Remove clouds outside the screen
        groupForClouds.getChildren().removeAll(cloudsToRemove);

        // If there are no clouds or the last one is far enough, spawn a new cloud
        if (groupForClouds.getChildren().isEmpty() ||
                (pane.getWidth() - lastCloudSpawnX >= CLOUD_SPACING)) {
            spawnNewCloud();
            lastCloudSpawnX = pane.getWidth(); // Update the last spawned cloud's X coordinate
        }
    }
    private void spawnNewCloud() {
        //System.out.println("Spawning new cloud. lastCloudSpawnX: " + lastCloudSpawnX);
        Image cloudImage = getRandomCloud();
        ImageView cloudImageView = new ImageView(cloudImage);
        cloudImageView.setLayoutX(pane.getWidth()); // Start the cloud just outside the screen
        cloudImageView.setLayoutY(Math.random() * (pane.getHeight() - cloudImage.getHeight()-95));

        groupForClouds.getChildren().add(cloudImageView);
    }
}

