package com.denfop.integration.neiresources;

import neresources.compatibility.CompatBase;
import neresources.utils.ReflectionHelper;

public class NeiInit {
    public void init() {
        CompatBase compat = (CompatBase) ReflectionHelper.initialize(NeiResourcesIntegration.class);
        NeiResourcesIntegration compat1 = new NeiResourcesIntegration();
        compat1.init();
    }
}
