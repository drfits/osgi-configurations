package com.drfits.aem.meetup.configurations.osgi.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

/**
 * Created by Evgeniy Fitsner <drfits@drfits.com> on 12/18/16.
 */
@ObjectClassDefinition(
        name = "Common Cache Configuration (Meetup)"
)
public @interface BaseCacheConfig {

    long DEFAULT_CACHE_HEAP = 10L;
    long DEFAULT_CACHE_OFFHEAP = 10L;

    @AttributeDefinition(
            name = "Enable Cache",
            description = "Checked if cache should be enabled"
    )
    boolean cache_enabled() default true;

    @AttributeDefinition(
            name = "Heap Size",
            description = "Cache Heap Size in mb. Default is " + DEFAULT_CACHE_HEAP + " mb"
    )
    long heap() default DEFAULT_CACHE_HEAP;

    @AttributeDefinition(
            name = "OffHeap Size",
            description = "Cache OffHeap Size in mb. Default is " + DEFAULT_CACHE_OFFHEAP + " mb"
    )
    long offheap() default DEFAULT_CACHE_OFFHEAP;

    @AttributeDefinition(
            name = "Enabled Cache Areas",
            description = "Specify areas which will be enabled for cache",
            options = {
                    @Option(label = "Heap", value = "1"),
                    @Option(label = "Heap+OffHeap", value = "2"),
                    @Option(label = "Heap+OffHeap+Disk", value = "3")
            },
            defaultValue = {"2"}
    )
    String areas();

    @AttributeDefinition(
            name = "Initial cities",
            description = "Default initial cache values"
    )
    String[] initialCities();
}
