/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.api.task;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

@XmlRootElement(name = Task.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = Task.Constants.TYPE_NAME, propOrder = {
        Task.Elements.TK_TASK_ID,
        Task.Elements.TASK,
        Task.Elements.USER_PRINCIPAL_ID,
        Task.Elements.WORK_AREA,
        Task.Elements.DESCRIPTION,
        Task.Elements.ADMINISTRATIVE_DESCRIPTION,
        Task.Elements.ACTIVE,
        Task.Elements.ID,
        Task.Elements.CREATE_TIME,
        Task.Elements.EFFECTIVE_LOCAL_DATE,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class Task
        extends AbstractDataTransferObject
        implements TaskContract
{

    @XmlElement(name = Elements.TK_TASK_ID, required = false)
    private final String tkTaskId;
    @XmlElement(name = Elements.TASK, required = false)
    private final Long task;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.WORK_AREA, required = false)
    private final Long workArea;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.ADMINISTRATIVE_DESCRIPTION, required = false)
    private final String administrativeDescription;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime createTime;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private final LocalDate effectiveLocalDate;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private Task() {
        this.tkTaskId = null;
        this.task = null;
        this.userPrincipalId = null;
        this.workArea = null;
        this.description = null;
        this.administrativeDescription = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.createTime = null;
        this.effectiveLocalDate = null;
    }

    private Task(Builder builder) {
        this.tkTaskId = builder.getTkTaskId();
        this.task = builder.getTask();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.workArea = builder.getWorkArea();
        this.description = builder.getDescription();
        this.administrativeDescription = builder.getAdministrativeDescription();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.createTime = builder.getCreateTime();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
    }

    @Override
    public String getTkTaskId() {
        return this.tkTaskId;
    }

    @Override
    public Long getTask() {
        return this.task;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }

    @Override
    public Long getWorkArea() {
        return this.workArea;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getAdministrativeDescription() {
        return this.administrativeDescription;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    @Override
    public String getObjectId() {
        return this.objectId;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public DateTime getCreateTime() {
        return this.createTime;
    }

    @Override
    public LocalDate getEffectiveLocalDate() {
        return this.effectiveLocalDate;
    }


    /**
     * A builder which can be used to construct {@link Task} instances.  Enforces the constraints of the {@link TaskContract}.
     *
     */
    public final static class Builder
            implements Serializable, TaskContract, ModelBuilder
    {

        private String tkTaskId;
        private Long task;
        private String userPrincipalId;
        private Long workArea;
        private String description;
        private String administrativeDescription;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private DateTime createTime;
        private LocalDate effectiveLocalDate;

        private Builder(Long task) {
            setTask(task);
        }

        public static Builder create(Long task) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(task);
        }

        public static Builder create(TaskContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create(contract.getTask());
            builder.setTkTaskId(contract.getTkTaskId());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setWorkArea(contract.getWorkArea());
            builder.setDescription(contract.getDescription());
            builder.setAdministrativeDescription(contract.getAdministrativeDescription());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setCreateTime(contract.getCreateTime());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            return builder;
        }

        public Task build() {
            return new Task(this);
        }

        @Override
        public String getTkTaskId() {
            return this.tkTaskId;
        }

        @Override
        public Long getTask() {
            return this.task;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        @Override
        public Long getWorkArea() {
            return this.workArea;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getAdministrativeDescription() {
            return this.administrativeDescription;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        @Override
        public boolean isActive() {
            return this.active;
        }

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public DateTime getCreateTime() {
            return this.createTime;
        }

        @Override
        public LocalDate getEffectiveLocalDate() {
            return this.effectiveLocalDate;
        }

        public void setTkTaskId(String tkTaskId) {
            this.tkTaskId = tkTaskId;
        }

        public void setTask(Long task) {
            if (task == null) {
                throw new IllegalArgumentException("task is null");
            }
            this.task = task;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            this.userPrincipalId = userPrincipalId;
        }

        public void setWorkArea(Long workArea) {
            this.workArea = workArea;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setAdministrativeDescription(String administrativeDescription) {
            this.administrativeDescription = administrativeDescription;
        }

        public void setVersionNumber(Long versionNumber) {
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setCreateTime(DateTime createTime) {
            this.createTime = createTime;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
            this.effectiveLocalDate = effectiveLocalDate;
        }

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "task";
        final static String TYPE_NAME = "TaskType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String TK_TASK_ID = "tkTaskId";
        final static String TASK = "task";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String WORK_AREA = "workArea";
        final static String DESCRIPTION = "description";
        final static String ADMINISTRATIVE_DESCRIPTION = "administrativeDescription";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String CREATE_TIME = "createTime";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";

    }

}