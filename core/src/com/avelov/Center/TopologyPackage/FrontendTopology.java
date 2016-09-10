package com.avelov.Center.TopologyPackage;

import com.avelov.Frontend.CellDrawers.CellDrawer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by mateusz on 18.04.16.
 */
public interface FrontendTopology
{
    CellDrawer getCellDrawer();
    Vector2 getBoundingRectSize();
}
