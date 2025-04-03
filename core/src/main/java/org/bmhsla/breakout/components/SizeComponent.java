package org.bmhsla.breakout.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class SizeComponent implements Component {
    public Vector2 size;

    public SizeComponent() {
        size = new Vector2();
    }

    public SizeComponent(float width, float height) {
        size = new Vector2(width, height);
    }
}
