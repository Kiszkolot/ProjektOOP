package Interfaces;


import Classes.Vector2D;

import java.awt.*;

public interface IMapElement {

    Vector2D getPosition();

    String toString();

    Color toColor();

    void addObserver(IPositonChangedObserver watcher);

    void removeObserver(IPositonChangedObserver watcher);

}