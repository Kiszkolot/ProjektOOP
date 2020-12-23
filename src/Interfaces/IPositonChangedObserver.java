package Interfaces;

import Classes.Animal;
import Classes.Vector2D;

public interface IPositonChangedObserver {
    boolean positionChanged(Vector2D forst, Vector2D second, Animal pet);
}
