package com.example.game2d;

import javafx.scene.image.Image;

public class GameObject {
    private Image image;
    private double x;
    private double y;

    public GameObject(Image image, double x, double y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
