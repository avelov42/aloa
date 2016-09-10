package com.avelov.Center.TopologyPackage;

import com.avelov.Frontend.CellDrawers.CellDrawer;
import com.avelov.Frontend.CellDrawers.HexCellDrawer;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by mateusz on 18.04.16.
 */
public class HexFrontendTopology implements FrontendTopology {
    @Override
    public CellDrawer getCellDrawer()
    {
        return new HexCellDrawer(getBoundingRectSize());
    }

    @Override
    public Vector2 getBoundingRectSize()
    {
        return new Vector2(2, (float) Math.sqrt(3));
    }

    //http://imagebank.osa.org/getImage.xqy?img=cCF6ekAubGFyZ2Usb2wtMzEtMTYtMjQ2Mi1nMDAx

}
