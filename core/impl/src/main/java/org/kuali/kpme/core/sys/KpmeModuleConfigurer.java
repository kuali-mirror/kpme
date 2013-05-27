package org.kuali.kpme.core.sys;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.config.ConfigurationException;
import org.kuali.rice.core.api.config.module.RunMode;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.framework.config.module.ModuleConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KpmeModuleConfigurer extends ModuleConfigurer {

    public KpmeModuleConfigurer() {
        setValidRunModes(Arrays.asList(RunMode.LOCAL));
    }

    public KpmeModuleConfigurer(String moduleName) {
        setModuleName(moduleName);
        setValidRunModes(Arrays.asList(RunMode.LOCAL));
    }

    @Override
    public List<String> getAdditionalSpringFiles() {
        final String files = ConfigContext.getCurrentContextConfig().getProperty("kpme." + getModuleName() + ".additionalSpringFiles");
        return files == null ? Collections.<String>emptyList() : parseFileList(files);
    }

    @Override
    public RunMode getRunMode() {
        return RunMode.LOCAL;
    }

    protected String getDefaultConfigPackagePath() {
        return "classpath:org/kuali/kpme/" + getModuleName().toLowerCase() + "/config/";
    }

    private List<String> parseFileList(String files) {
        final List<String> parsedFiles = new ArrayList<String>();
        for (String file : Arrays.asList(files.split(","))) {
            final String trimmedFile = file.trim();
            if (!trimmedFile.isEmpty()) {
                parsedFiles.add(trimmedFile);
            }
        }

        return parsedFiles;
    }
}
