package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {


    private String port;
    private String memoryLimit;
    private String cfInstanceIndex;
    private String cfInstanceAddr;

    public EnvController(@Value("${PORT:NOT SET}") String port,
                         @Value("${MEMORY_LIMIT:NOT SET}") String memoryLimit,
                         @Value("${CF_INSTANCE_INDEX:NOT SET}") String cfInstanceIndex,
                         @Value("${CF_INSTANCE_ADDR:NOT SET}") String cfInstanceAddr) {
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = cfInstanceIndex;
        this.cfInstanceAddr = cfInstanceAddr;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        return new HashMap<String, String>(){{
            put("PORT", port);
            put("MEMORY_LIMIT", memoryLimit);
            put("CF_INSTANCE_INDEX", cfInstanceIndex);
            put("CF_INSTANCE_ADDR", cfInstanceAddr);
        }};
    }
}
