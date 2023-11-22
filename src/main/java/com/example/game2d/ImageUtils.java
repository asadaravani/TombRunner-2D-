package com.example.game2d;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.Node;

public class ImageUtils {

    public static boolean checkPixelPerfectCollision(Node character, Node object) {
        Image characterImage = getImageFromNode(character);
        Image objectImage = getImageFromNode(object);

        // Get the bounds of the nodes
        double characterMinX = character.getBoundsInParent().getMinX();
        double characterMinY = character.getBoundsInParent().getMinY();
        double objectMinX = object.getBoundsInParent().getMinX();
        double objectMinY = object.getBoundsInParent().getMinY();

        // Get the intersection bounds
        double intersectMinX = Math.max(characterMinX, objectMinX);
        double intersectMinY = Math.max(characterMinY, objectMinY);
        double intersectMaxX = Math.min(characterMinX + character.getBoundsInParent().getWidth(),
                objectMinX + object.getBoundsInParent().getWidth());
        double intersectMaxY = Math.min(characterMinY + character.getBoundsInParent().getHeight(),
                objectMinY + object.getBoundsInParent().getHeight());

        // Check for intersection
        if (intersectMinX < intersectMaxX && intersectMinY < intersectMaxY) {
            // Check for pixel-perfect collision in the intersection area
            return checkPixelPerfectCollision(characterImage, characterMinX, characterMinY,
                    objectImage, objectMinX, objectMinY, intersectMinX, intersectMinY, intersectMaxX, intersectMaxY);
        }

        return false;
    }

    private static Image getImageFromNode(Node node) {
        if (node instanceof ImageView imageView) {
            return imageView.getImage();
        }
        return null;
    }

    private static boolean checkPixelPerfectCollision(Image characterImage, double characterMinX, double characterMinY,
                                                      Image objectImage, double objectMinX, double objectMinY,
                                                      double intersectMinX, double intersectMinY,
                                                      double intersectMaxX, double intersectMaxY) {
        PixelReader characterReader = characterImage.getPixelReader();
        PixelReader objectReader = objectImage.getPixelReader();

        for (double x = intersectMinX; x < intersectMaxX; x++) {
            for (double y = intersectMinY; y < intersectMaxY; y++) {
                Color characterColor = characterReader.getColor((int) (x - characterMinX), (int) (y - characterMinY));
                Color objectColor = objectReader.getColor((int) (x - objectMinX), (int) (y - objectMinY));

                if (characterColor.getOpacity() > 0 && objectColor.getOpacity() > 0) {
                    // Collision detected at this pixel
                    return true;
                }
            }
        }

        return false;
    }
}
