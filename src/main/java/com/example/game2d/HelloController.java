package com.example.game2d;

import java.net.URL;
import java.util.*;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class HelloController {
    @FXML
    public Rectangle backRectangle;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView backgroundImgVw;

    @FXML
    private ImageView charImageView;

    @FXML
    private Rectangle grassRectangle;

    @FXML
    private Pane pane;

    @FXML
    private static Label lblGameOver;

    @FXML
    private Label lblScore;
    @FXML
    private Group groupBack;

    @FXML
    private Group cactusGroup;
    private static Timeline scoreTimeline;
    private static Timeline cloudTimeline;
    private static AnimationTimer gameLoop;

    private final double screenWidth = 600;
    private final double cactusSpeed = 2;
    private final double cloudSpeed = 0.030;
    private long elapsedTime = 0;
    private static boolean isGameOver = false;
    private boolean is3seconds = false;
    Cactus cactus;
    Character character;
    Background background;
    @FXML
    void initialize() {
        cactus = new Cactus(pane, cactusGroup);
        character = new Character(charImageView);
        background = new Background(groupBack, backgroundImgVw, pane);
        Character.walk();
        startScoreTimer();

    }

    private void startScoreTimer() {
        scoreTimeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            elapsedTime += 1; // Increment elapsed time by 1 millisecond

            // Calculate minutes, seconds, and milliseconds
            long minutes = (elapsedTime / 60000) % 60;
            long seconds = (elapsedTime / 1000) % 60;
            long milliseconds = elapsedTime % 1000;

            // Extract the first digit of milliseconds
            long firstDigitMilliseconds = milliseconds / 100;
            lblScore.setText((String.format("%02d%02d%01d", minutes, seconds, firstDigitMilliseconds)));
            if(seconds > 3){
                is3seconds = true;
            }
        }));

        scoreTimeline.setCycleCount(Animation.INDEFINITE);
        scoreTimeline.play();

    }
}
