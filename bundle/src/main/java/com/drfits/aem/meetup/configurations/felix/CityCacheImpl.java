package com.drfits.aem.meetup.configurations.felix;

import com.drfits.aem.meetup.configurations.api.CityCache;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Evgeniy Fitsner <drfits@drfits.com> on 12/18/16.
 */
@Component(
        metatype = true,
        immediate = true
)
@Service
@Properties({
        @Property(
                name = CityCacheImpl.ENABLE_CACHE,
                label = "Enable Cache",
                description = "Checked if cache should be enabled",
                boolValue = true
        ),
        @Property(
                name = CityCacheImpl.CACHE_HEAP,
                label = "Heap Size",
                description = "Cache Heap Size in mb. Default is " + CityCacheImpl.DEFAULT_CACHE_HEAP + " mb",
                longValue = CityCacheImpl.DEFAULT_CACHE_HEAP
        ),
        @Property(
                name = CityCacheImpl.CACHE_OFFHEAP,
                label = "OffHeap Size",
                description = "Cache OffHeap Size in mb. Default is " + CityCacheImpl.DEFAULT_CACHE_OFFHEAP + " mb",
                longValue = CityCacheImpl.DEFAULT_CACHE_OFFHEAP
        )
})
public class CityCacheImpl implements CityCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityCacheImpl.class);

    static final long DEFAULT_CACHE_HEAP = 10L;
    static final long DEFAULT_CACHE_OFFHEAP = 10L;

    static final String ENABLE_CACHE = "cache.enable";
    static final String CACHE_HEAP = "cache.heap";
    static final String CACHE_OFFHEAP = "cache.offheap";

    @Property(
            label = "Enabled Cache Areas",
            description = "Specify areas which will be enabled for cache",
            options = {
                    @PropertyOption(name = "1", value = "Heap"),
                    @PropertyOption(name = "2", value = "Heap+OffHeap")
            },
            value = "1"
    )
    private static final String ENABLED_CACHE_AREAS = "cache.enabled.areas";

    @Property(
            unbounded = PropertyUnbounded.ARRAY,
            label = "Initial cities",
            description = "Default initial cache values"
    )
    private static final String INITIAL_CITIES = "cache.initial.values";

    private Boolean enabled;
    private Long heap;
    private Long offHeap;
    private String[] initialCitiesProp;
    private String areas;

    @Activate
    @Modified
    protected void init(ComponentContext componentCtx, BundleContext bundleCtx, Map<String, ?> properties) {
        enabled = PropertiesUtil.toBoolean(componentCtx.getProperties().get(ENABLE_CACHE), true);
        heap = PropertiesUtil.toLong(componentCtx.getProperties().get(CACHE_HEAP), DEFAULT_CACHE_HEAP);
        heap = PropertiesUtil.toLong(componentCtx.getProperties().get(CACHE_HEAP), DEFAULT_CACHE_HEAP);
        offHeap = PropertiesUtil.toLong(componentCtx.getProperties().get(CACHE_OFFHEAP), DEFAULT_CACHE_OFFHEAP);
        initialCitiesProp = PropertiesUtil.toStringArray(componentCtx.getProperties().get(INITIAL_CITIES));
        areas = PropertiesUtil.toString(componentCtx.getProperties().get(ENABLED_CACHE_AREAS), "1");
        printConfig();
    }

    private void printConfig() {
        LOGGER.info("/*********************** CityCacheImpl configurations **************************/");
        LOGGER.info("Enabled: {}", enabled);
        LOGGER.info("heap: {} mb", heap);
        LOGGER.info("offHeap: {} mb", offHeap);
        LOGGER.info("areas: {}", areas);
        LOGGER.info("initialCitiesProp: {}", Arrays.toString(initialCitiesProp));
        LOGGER.info("/*************************************************/");
    }

    @Deactivate
    protected void deactivate() {
        LOGGER.info("CityCacheImpl deactivated");
    }

}
