package org.kuali.kpme.core.sys;


import org.kuali.rice.krad.bo.ModuleConfiguration;
import org.kuali.rice.krad.datadictionary.DataDictionaryException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KpmeModuleConfiguration extends ModuleConfiguration {
    @Override
    public void setDataDictionaryPackages(List<String> dataDictionaryPackages) {
        this.trimList(dataDictionaryPackages);
        //resolve individual xml files

        List<String> ddFiles = new ArrayList<String>();
        for (String ddPackage : dataDictionaryPackages) {
            if (ddPackage.contains("*")) {
                try {
                    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                    Resource[] resources = resolver.getResources(ddPackage);
                    for (Resource resource : resources) {
                        String prefix = "classpath:";
                        if (resource instanceof FileSystemResource) {
                            ddFiles.add("file:" + ((FileSystemResource)resource).getPath());
                        } else if (resource instanceof ClassPathResource) {
                            ddFiles.add("classpath:" + ((ClassPathResource)resource).getPath());
                        } else {
                            throw new DataDictionaryException("DD Resource " + resource.getFile().getPath() + " not found");
                        }
                    }
                } catch (IOException e) {
                    throw new DataDictionaryException("DD Resource " + ddPackage + " not found");
                }
            } else {
                ddFiles.add(ddPackage);
            }
        }
        this.dataDictionaryPackages = ddFiles;
    }
}
