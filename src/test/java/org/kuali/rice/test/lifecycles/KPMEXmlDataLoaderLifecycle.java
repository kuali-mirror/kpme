package org.kuali.rice.test.lifecycles;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.lifecycle.BaseLifecycle;
import org.kuali.rice.kew.batch.KEWXmlDataLoader;


public class KPMEXmlDataLoaderLifecycle extends BaseLifecycle {
    private static final Logger LOG = Logger.getLogger(KEWXmlDataLoaderLifecycle.class);

    private String filename;

    /**
     * Specifies the XML resource to load.  The resource path should be in Spring resource notation.
     * @param resource the XML resource to load
     */
    public KPMEXmlDataLoaderLifecycle(String resource) {
        this.filename = resource;
    }

    public void start() throws Exception {
        String useKewXmlDataLoaderLifecycle = ConfigContext.getCurrentContextConfig().getProperty("use.kewXmlmlDataLoaderLifecycle");

        if (useKewXmlDataLoaderLifecycle != null && !Boolean.valueOf(useKewXmlDataLoaderLifecycle)) {
            LOG.debug("Skipping KEWXmlDataLoaderLifecycle due to property: use.kewXmlmlDataLoaderLifecycle=" + useKewXmlDataLoaderLifecycle);
            return;
        }

        LOG.info("################################");
        LOG.info("#");
        LOG.info("#  Begin Loading file '" + filename + "'");
        LOG.info("#");
        LOG.info("################################");
        loadData();
        super.start();
    }

    /**
     * Does the work of loading the data
     * @throws Exception
     */
    protected void loadData() throws Exception {
        KEWXmlDataLoader.loadXmlFile(filename);
    }
}
