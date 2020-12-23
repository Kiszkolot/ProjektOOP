package Classes;

import java.util.Random;

public class Vector2D {
    final int x;
    final int y;

    public Vector2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return (String.format("(%d,%d)", this.x, this.y));
    }

    public boolean precedes(Vector2D other) {
        return other.x >= this.x && other.y >= this.y;
    }

    public boolean follows(Vector2D other) {
        return other.x <= this.x && other.y <= this.y;
    }

    public Vector2D randomPositionAround(){
        int x = new Random().nextInt((2)+1)-1;
        int y = new Random().nextInt((2)+1)-1;
        Vector2D additional = new Vector2D(x,y);
        return additional.add(this);
    }

    public Vector2D upperRight(Vector2D other) {
        int m = Math.max(this.x, other.x);
        int n = Math.max(this.y, other.y);
        return new Vector2D(m, n);
    }

    public Vector2D lowerLeft(Vector2D other) {
        int m = Math.min(this.x, other.x);
        int n = Math.min(this.y, other.y);
        return new Vector2D(m, n);
    }

    public Vector2D add(Vector2D other) {
        int m, n;
        m = this.x + other.x;
        n = this.y + other.y;
        return new Vector2D(m, n);
    }

    public Vector2D subtract(Vector2D other) {
        int m, n;
        m = this.x - other.x;
        n = this.y - other.y;
        return new Vector2D(m, n);
    }

    public Vector2D opposite() {
        return new Vector2D((-1) * this.x, (-1) * this.y);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash += this.getX()*31;
        hash += this.getY()*13;
        return hash;
    }

}

