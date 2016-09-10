package com.avelov.Frontend;

import com.avelov.Aloa;
import com.avelov.Backend.Cell.Cell;
import com.avelov.Center.BoardHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;

import java.util.Iterator;

import com.avelov.Frontend.CellDrawers.CellDrawer;
import com.avelov.Pair;


/**
 * Renders cells and brush shadow.
 */
public class BoardRenderer {
    private static final int viewportX = 80;
    private BoardHandler handler;
    private ShapeRenderer render;
    private CellDrawer cellDrawer;
    private OrthographicCamera camera;

    private GestureDetector gestureDetector;

    public BoardRenderer(BoardHandler handler, CellDrawer cellDrawer) {
        this.handler = handler;
        this.cellDrawer = cellDrawer;
        render = Aloa.assets.shape;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Should be called after every resize of the window.
     *
     * @param width  New width in pixels.
     * @param height New height in pixels.
     */
    public void resize(int width, int height) {
        float viewportY = viewportX * (height / (float) width);
        camera = new OrthographicCamera(viewportX, viewportY);
        camera.lookAt(0, 0, 0);
        camera.translate(viewportX / 2, viewportY / 2, 0);
        camera.update();
    }

    /**
     * Transforms screen coordinates to virtual. Assumes that (0, 0) is in top left.
     *
     * @param screen Position of the point on the screen.
     * @return Position of the point in virtual coordinate system.
     */
    public Vector3 screenToVirtual(Vector3 screen) {
        return camera.unproject(screen);
    }

    public int getCellsCountOnY() {
        Vector3 bottom = new Vector3(0, Gdx.graphics.getHeight(), 0);
        Vector3 top = new Vector3(Gdx.graphics.getWidth(), 0, 0);
        bottom = screenToVirtual(bottom);
        top = screenToVirtual(top);
        return (int) ((top.y - bottom.y) / cellDrawer.getSizeY());
    }

    public void zoom(float val) {
        camera.zoom *= 1 - val / 100;
        camera.update();
        //render.setProjectionMatrix(camera.combined);
    }

    public void translate(float x, float y) {
        float cellSize = -camera.unproject(new Vector3(0, 0, 0)).x + camera.unproject(new Vector3(1, 0, 0)).x;
        camera.translate(x * cellSize, y * cellSize);
        camera.update();
        //render.setProjectionMatrix(camera.combined);
    }

    public void render() {

        Cell currentCell;
        Vector3 currentCoord, topLeft, bottomRight;
        topLeft = camera.unproject(new Vector3(0, 0, 0));
        bottomRight = camera.unproject(new Vector3(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0));
        Iterator<Pair<Cell, Vector3>> cells = handler.getCells(topLeft, bottomRight);


        render.setProjectionMatrix(camera.combined);
        while (cells.hasNext()) {
            Pair<Cell, Vector3> p = cells.next();
            currentCell = p.first;
            currentCoord = p.second;

            if (currentCell != null) {
                cellDrawer.setPosition(currentCoord.x - cellDrawer.getSizeX() / 2, currentCoord.y - cellDrawer.getSizeY() / 2);
                cellDrawer.draw(currentCell, camera.zoom, false);

            }
        }
    }

    /*
    public void renderBrush(Cell cell, Iterator<Vector3> coords, boolean transparent)
    {
        Vector3 cc;
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        render.setProjectionMatrix(camera.combined);

        ArrayList<Vector3> coords2 = new ArrayList<>();
        float minX, minY, maxX, maxY;
        minX = minY = Float.MAX_VALUE;
        maxX = maxY = Float.MIN_VALUE;
        while(coords.hasNext())
        {
            cc = coords.next();
            minX = Math.min(cc.x, minX);
            minY = Math.min(cc.y, minY);
            maxX = Math.max(cc.x, maxX);
            maxY = Math.max(cc.y, maxY);
            coords2.add(cc);
        }
        final float pad = 2;
        minX -= pad;
        minY -= pad;
        maxX += pad;
        maxY += pad;

        System.out.printf("%f %f %f %f\n", minX, minY, maxX, maxY);
        if(!transparent)
        {
            render.setColor(0, 0, 0, 0.42f);
            render.rect(minX, minY, maxX - minX, maxY-minY);
        }
        for(Vector3 currentCoord2 : coords2)
        {
            cellDrawer.setPosition(currentCoord2.x - cellDrawer.getSizeX() / 2, currentCoord2.y - cellDrawer.getSizeY() / 2);
            //@todo supply here cell boardSize instead of zoom & test it


            cellDrawer.draw(cell, camera.zoom, transparent);
        }
    }
    */

    public void renderBrush(Cell cell, Iterator<Vector3> coords, boolean ghost) {
        Vector3 currentCoord;
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        render.setProjectionMatrix(camera.combined);
        while (coords.hasNext()) {
            currentCoord = coords.next();
            //System.out.println(" ->" + currentCoord);
            cellDrawer.setPosition(currentCoord.x - cellDrawer.getSizeX() / 2, currentCoord.y - cellDrawer.getSizeY() / 2);
            //@todo supply here cell boardSize instead of zoom & test it
            cellDrawer.draw(cell, camera.zoom, ghost);
        }
    }
}
