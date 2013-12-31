package kloSpringServer;

import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.startup.AbstractTilesInitializer;

public class TilesInitializer extends AbstractTilesInitializer {
    public TilesInitializer() {
    }

    @Override
    protected AbstractTilesContainerFactory createContainerFactory(ApplicationContext context) {
        return new TilesContainerFactory();
    }
}
