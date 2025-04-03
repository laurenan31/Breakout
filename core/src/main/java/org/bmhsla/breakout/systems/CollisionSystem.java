package org.bmhsla.breakout.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.sun.tools.javac.code.TypeTag;
import org.bmhsla.breakout.components.*;
import org.bmhsla.breakout.factories.EntityFactory;

public class CollisionSystem extends EntitySystem {

    private Sound noise;
    private Sound jump;

    public CollisionSystem() {
        noise = Gdx.audio.newSound(Gdx.files.internal("noise.wav"));
        jump = Gdx.audio.newSound(Gdx.files.internal("Jump.wav"));
    }


    @Override
    public void update(float deltaTime) {

        Entity paddle = null;
        for(Entity entity: getEngine().getEntitiesFor(Family.all(TypeTagComponent.class).get())) {
            TypeTagComponent ttc = entity.getComponent(TypeTagComponent.class);
            if(ttc.tag.equals("paddle")) {
                paddle = entity;
                break;
            }
        }
        if(paddle == null) {
            Gdx.app.error("CollisionSystem", "Paddle not found");
            return;
        }

        PositionComponent paddlePos = paddle.getComponent(PositionComponent.class);
        SizeComponent paddleSize = paddle.getComponent(SizeComponent.class);


        for(Entity entity: getEngine().getEntitiesFor(Family.all(TypeTagComponent.class, PositionComponent.class, SizeComponent.class, DirectionComponent.class, SpeedComponent.class).get())) {
            TypeTagComponent typeTagComponent = entity.getComponent(TypeTagComponent.class);
            if(typeTagComponent.tag.equals("ball")) {
                PositionComponent ballPosition = entity.getComponent(PositionComponent.class);
                SizeComponent ballSize = entity.getComponent(SizeComponent.class);
                DirectionComponent ballDirection = entity.getComponent(DirectionComponent.class);
                SpeedComponent ballSpeed = entity.getComponent(SpeedComponent.class);

                // move ball
                ballPosition.position.x += ballDirection.direction.x * ballSpeed.speed * deltaTime;
                ballPosition.position.y += ballDirection.direction.y * ballSpeed.speed * deltaTime;

                // remove ball that falls below paddle


                // bounce off the left wall
                if(ballPosition.position.x <= 0) {
                    ballPosition.position.x = 0;
                    ballDirection.direction.x *= -1;

                    jump.play();
                }

                // bounce off the right wall
                if(ballPosition.position.x + ballSize.size.x >= Gdx.graphics.getWidth()) {
                    ballPosition.position.x = Gdx.graphics.getWidth() - ballSize.size.x;
                    ballDirection.direction.x *= -1;

                    jump.play();
                }

                // bounce off the top wall
                if(ballPosition.position.y + ballSize.size.y >= Gdx.graphics.getHeight()) {
                    ballPosition.position.y = Gdx.graphics.getHeight() - ballSize.size.y;
                    ballDirection.direction.y *= -1;

                    jump.play();
                }

                // bounce off paddle
                boolean overlapsX = ballPosition.position.x + ballSize.size.x > paddlePos.position.x && ballPosition.position.x < paddlePos.position.x + paddleSize.size.x;
                boolean overlapsY = ballPosition.position.y <= paddlePos.position.y + paddleSize.size.y && ballPosition.position.y + ballSize.size.y > paddlePos.position.y;


                if (overlapsX && overlapsY && ballDirection.direction.y < 0) {
                    ballPosition.position.y = paddlePos.position.y + paddleSize.size.y;
                    ballDirection.direction.y *= -1;

                    noise.play();

                    float minWidth = 20f;
                    paddleSize.size.x = Math.max(paddleSize.size.x-10, minWidth);

                    ballSpeed.speed *= 1.05f;

                    EntityFactory entityFactory = new EntityFactory();
                    getEngine().addEntity(entityFactory.createBallEntity());

                }

                // ball and brick collision
                Rectangle ballRect = new Rectangle(ballPosition.position.x, ballPosition.position.y, ballSize.size.x, ballSize.size.y);
                ImmutableArray<Entity> bricks = getEngine().getEntitiesFor(Family.all(TypeTagComponent.class, PositionComponent.class, SizeComponent.class).get());

                for(Entity brick: bricks) {
                    TypeTagComponent tag = brick.getComponent(TypeTagComponent.class);
                    if(!tag.tag.equals("brick")) {
                        continue;
                    }

                    PositionComponent brickPos = brick.getComponent(PositionComponent.class);
                    SizeComponent brickSize = brick.getComponent(SizeComponent.class);

                    Rectangle brickRect = new Rectangle(brickPos.position.x, brickPos.position.y, brickSize.size.x, brickSize.size.y);

                    if(ballRect.overlaps(brickRect)) {
                        ballDirection.direction.y *= -1;
                        getEngine().removeEntity(brick);
                        noise.play();
                        break;
                    }

                }

                
            }

        }
    }
}
