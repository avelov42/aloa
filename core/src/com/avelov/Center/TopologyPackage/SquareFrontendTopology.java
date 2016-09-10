package com.avelov.Center.TopologyPackage;

import com.avelov.Frontend.CellDrawers.CellDrawer;
import com.avelov.Frontend.CellDrawers.SquareCellDrawer;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by mateusz on 18.04.16.
 */
public class SquareFrontendTopology implements FrontendTopology
{

    @Override
    public CellDrawer getCellDrawer()
    {
        return new SquareCellDrawer(getBoundingRectSize());
    }

    @Override
    public Vector2 getBoundingRectSize()
    {
        return new Vector2(1, 1);
    }
}
