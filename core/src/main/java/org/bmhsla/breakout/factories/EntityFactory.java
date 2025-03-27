package org.bmhsla.breakout.factories;

import com.badlogic.ashley.core.Entity;
import org.bmhsla.breakout.components.PositionComponent;
import org.bmhsla.breakout.components.RenderComponent;
import org.bmhsla.breakout.components.SizeComponent;
import org.bmhsla.breakout.components.SpeedComponent;

public class EntityFactory {

    public Entity createPaddleEntity() {
        Entity paddle = new Entity();

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.position.x = 10;
        positionComponent.position.y = 10;
        paddle.add(positionComponent);

        SizeComponent sizeComponent = new SizeComponent();
        sizeComponent.size.x = 100;
        sizeComponent.size.y = 20;
        paddle.add(sizeComponent);

        SpeedComponent speedComponent = new SpeedComponent();
        speedComponent.speed = 200;
        paddle.add(speedComponent);

        paddle.add(new RenderComponent());

        return paddle;
    }
}
