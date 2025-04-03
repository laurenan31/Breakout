package org.bmhsla.breakout;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import org.bmhsla.breakout.factories.EntityFactory;
import org.bmhsla.breakout.systems.CollisionSystem;
import org.bmhsla.breakout.systems.InputSystem;
import org.bmhsla.breakout.systems.RenderSystem;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameHandler extends ApplicationAdapter {
    private Engine engine;

    @Override
    public void create() {
        engine = new Engine();

        engine.addSystem(new RenderSystem());
        engine.addSystem(new InputSystem());
        engine.addSystem(new CollisionSystem());

        EntityFactory entityFactory = new EntityFactory();
        engine.addEntity(entityFactory.createPaddleEntity());
        engine.addEntity(entityFactory.createBallEntity());


//        int rows = 5;
//        int cols = 10;
//        float brickWidth = 60;
//        float brickHeight = 20;
//        float startX = 50;
//        float startY = 400;
//
//        for(int row = 0; row < rows; row++) {
//            for(int col = 0; col < cols; col++) {
//                float x = startX + col * (brickWidth + 5);
//                float y = startY - row * (brickHeight + 5);
//                engine.addEntity(entityFactory.createBrick(x, y, brickWidth, brickHeight));
//
//            }
//        }
        int rows = 5;    // Number of rows remains literal
        int cols = 10;   // Number of columns remains literal

        float spacing = 5;  // spacing between bricks

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

// dynamically calculate brick dimensions based on screen size
        float brickWidth = (screenWidth - (cols + 1) * spacing) / cols;
        float brickHeight = (screenHeight * 0.3f - (rows + 1) * spacing) / rows;  // occupy 30% of screen height

        float startX = spacing;  // start just after initial spacing from left edge
        float startY = screenHeight - spacing - brickHeight;  // start near top of screen, moving downward

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                float x = startX + col * (brickWidth + spacing);
                float y = startY - row * (brickHeight + spacing);
                engine.addEntity(entityFactory.createBrick(x, y, brickWidth, brickHeight));
            }
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        engine.update(Gdx.graphics.getDeltaTime());
    }

}
