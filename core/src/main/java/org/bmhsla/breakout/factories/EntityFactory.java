package org.bmhsla.breakout.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import org.bmhsla.breakout.components.*;

public class EntityFactory {

    private Color[] brickColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.PURPLE};

    public Entity createBrick(float x, float y, float width, float height) {
        Entity brick = new Entity();
        TypeTagComponent typeTag = new TypeTagComponent();
        typeTag.tag = "brick";
        brick.add(typeTag);

        brick.add(new PositionComponent(x, y));

        brick.add(new SizeComponent(width, height));

        RenderComponent renderComponent = new RenderComponent();
        renderComponent.color = brickColors[MathUtils.random(brickColors.length-1)];
        brick.add(renderComponent);

        return brick;
    }

    public Entity createPaddleEntity() {
        Entity paddle = new Entity();

        TypeTagComponent typeTag = new TypeTagComponent();
        typeTag.tag = "paddle";
        paddle.add(typeTag);

        PositionComponent positionComponent = new PositionComponent();
        positionComponent.position.x = MathUtils.random(0, Gdx.graphics.getWidth());
        positionComponent.position.y = 10;
        paddle.add(positionComponent);

        SizeComponent sizeComponent = new SizeComponent();
        sizeComponent.size.x = 100;
        sizeComponent.size.y = 20;
        paddle.add(sizeComponent);

        SpeedComponent speedComponent = new SpeedComponent();
        speedComponent.speed = 500;
        paddle.add(speedComponent);

        paddle.add(new RenderComponent());

        return paddle;
    }

    public Entity createBallEntity() {
        Entity ball = new Entity();
        TypeTagComponent typeTag = new TypeTagComponent();
        typeTag.tag = "ball";
        ball.add(typeTag);
        PositionComponent positionComponent = new PositionComponent();
        positionComponent.position.x = MathUtils.random(0, Gdx.graphics.getWidth());
        positionComponent.position.y = MathUtils.random(Gdx.graphics.getHeight() / 2f, Gdx.graphics.getHeight());
        ball.add(positionComponent);

        SizeComponent sizeComponent = new SizeComponent();
        sizeComponent.size.x = 10;
        sizeComponent.size.y = 20;
        ball.add(sizeComponent);

        SpeedComponent speedComponent = new SpeedComponent();
        speedComponent.speed = 500;
        ball.add(speedComponent);
        ball.add(new RenderComponent());

        DirectionComponent directionComponent = new DirectionComponent();
        directionComponent.direction.x = (MathUtils.random(-1f, 1f));
        directionComponent.direction.y = (MathUtils.random(-1f, 1f));
        ball.add(directionComponent);

        RenderComponent renderComponent = new RenderComponent();
        renderComponent.shape = RenderComponent.ShapeKind.CIRCLE;
        ball.add(renderComponent);

        return ball;
    }
}
