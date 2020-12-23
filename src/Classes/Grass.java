package Classes;

import Interfaces.IMapElement;
import Interfaces.IPositonChangedObserver;

import java.awt.*;

public class Grass implements IMapElement {
    private  Vector2D position;

    public Grass(Vector2D position){
        this.position = position;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Color toColor() {
        return new Color(67, 222, 31);
    }

    @Override
    public void addObserver(IPositonChangedObserver watcher) {
    }

    @Override
    public void removeObserver(IPositonChangedObserver watcher) {

    }

    @Override
    public String toString(){
        return "*";
    }


}
