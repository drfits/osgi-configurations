package com.drfits.aem.meetup.configurations.osgi;

import com.drfits.aem.meetup.configurations.api.CityCache;
import com.drfits.aem.meetup.configurations.osgi.config.BaseCacheConfig;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by Evgeniy Fitsner <drfits@drfits.com> on 12/18/16.
 */
@Component(
        immediate = true,
        service = {
                CityCache.class
        }
)
@Designate(ocd = BaseCacheConfig.class)
public class CityCacheBaseImpl implements CityCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityCacheBaseImpl.class);

    private BaseCacheConfig cacheConfig;

    @Activate
    @Modified
    protected void init(ComponentContext componentCtx, BundleContext bundleCtx, BaseCacheConfig cacheConfig) {
        this.cacheConfig = cacheConfig;
        if (cacheConfig != null) {
            printConfig();
        }
    }

    private void printConfig() {
        LOGGER.info("/*********************** CityCacheBaseImpl configurations **************************/");
        LOGGER.info("Enabled: {}", cacheConfig.cache_enabled());
        LOGGER.info("heap: {} mb", cacheConfig.heap());
        LOGGER.info("offHeap: {} mb", cacheConfig.offheap());
        LOGGER.info("areas: {}", cacheConfig.areas());
        LOGGER.info("initialCitiesProp: {}", Arrays.toString(cacheConfig.initialCities()));
        LOGGER.info("/*************************************************/");
    }

    @Deactivate
    protected void deactivate() {
        LOGGER.info("CityCacheImpl deactivated");
    }
}
