/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.sys;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.metadata.ConnectionRepository;
import org.apache.ojb.broker.metadata.DescriptorRepository;
import org.apache.ojb.broker.metadata.MetadataManager;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.core.api.util.ClassLoaderUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.DefaultResourceLoader;

public class SpringBasedOJBRepositoryLoader implements InitializingBean {

    private static final Logger LOG = Logger.getLogger(SpringBasedOJBRepositoryLoader.class);
    private static final String CLASSPATH_RESOURCE_PREFIX = "classpath:";

    private List<String> pathLocations;

    public void afterPropertiesSet() throws Exception {

	for (String pathLocation : pathLocations) {
	    loadRepositoryDescriptor(pathLocation);
	}

    }

    public void loadRepositoryDescriptor(String ojbRepositoryFilePath) {
	LOG.info("Begin loading OJB Metadata for: " + ojbRepositoryFilePath);
	DefaultResourceLoader resourceLoader = new DefaultResourceLoader(ClassLoaderUtils.getDefaultClassLoader());
	InputStream is = null;
	try {
	    is = resourceLoader.getResource(CLASSPATH_RESOURCE_PREFIX + ojbRepositoryFilePath).getInputStream();
	    ConnectionRepository cr = MetadataManager.getInstance().readConnectionRepository(is);
	    MetadataManager.getInstance().mergeConnectionRepository(cr);

	    is = resourceLoader.getResource(CLASSPATH_RESOURCE_PREFIX + ojbRepositoryFilePath).getInputStream();
	    DescriptorRepository dr = MetadataManager.getInstance().readDescriptorRepository(is);
	    MetadataManager.getInstance().mergeDescriptorRepository(dr);

	    if (LOG.isDebugEnabled()) {
		LOG.debug("--------------------------------------------------------------------------");
		LOG.debug("Merging repository descriptor: " + ojbRepositoryFilePath);
		LOG.debug("--------------------------------------------------------------------------");
	    }
	} catch (IOException ioe) {
	    if (is != null) {
		try {
		    is.close();
		} catch (IOException e) {
		    LOG.info("Failed to close InputStream on OJB repository path " + ojbRepositoryFilePath, e);
		}
	    }
	    throw new RiceRuntimeException(ioe);
	} finally {
	    if (is != null) {
		try {
		    is.close();
		} catch (IOException e) {
		    LOG.info("Failed to close InputStream on OJB repository path " + ojbRepositoryFilePath, e);
		}
	    }
	}
	LOG.info("Finished loading OJB Metadata for: " + ojbRepositoryFilePath);
    }

    public List<String> getPathLocations() {
	return pathLocations;
    }

    public void setPathLocations(List<String> pathLocations) {
	this.pathLocations = pathLocations;
    }

}
