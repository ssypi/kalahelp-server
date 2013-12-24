package kloSpringServer;

import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.startup.AbstractTilesInitializer;

/**
* Created with IntelliJ IDEA.
* User: kala
* Date: 21.12.2013
* Time: 4:21
*/
public class TilesInitializer extends AbstractTilesInitializer {
    public TilesInitializer() {
    }

    @Override
    protected AbstractTilesContainerFactory createContainerFactory(ApplicationContext context) {
        return new TilesContainerFactory();
    }
}
