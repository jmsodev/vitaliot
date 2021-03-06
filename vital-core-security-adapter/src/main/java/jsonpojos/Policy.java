
package jsonpojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "name",
    "active",
    "description",
    "applicationName",
    "actionValues",
    "resources",
    "subject",
    "lastModifiedBy",
    "lastModifiedDate",
    "createdBy",
    "creationDate"
})
public class Policy {

    @JsonProperty("name")
    private String name;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("description")
    private String description;
    @JsonProperty("applicationName")
    private String applicationName;
    @JsonProperty("actionValues")
    private ActionValues_ actionValues;
    @JsonProperty("resources")
    private List<String> resources = new ArrayList<String>();
    @JsonProperty("subject")
    private Subject_ subject;
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;
    @JsonProperty("lastModifiedDate")
    private String lastModifiedDate;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("creationDate")
    private String creationDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Policy withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The active
     */
    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    /**
     * 
     * @param active
     *     The active
     */
    @JsonProperty("active")
    public void setActive(Boolean active) {
        this.active = active;
    }

    public Policy withActive(Boolean active) {
        this.active = active;
        return this;
    }

    /**
     * 
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Policy withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * 
     * @return
     *     The applicationName
     */
    @JsonProperty("applicationName")
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * 
     * @param applicationName
     *     The applicationName
     */
    @JsonProperty("applicationName")
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Policy withApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    /**
     * 
     * @return
     *     The actionValues
     */
    @JsonProperty("actionValues")
    public ActionValues_ getActionValues() {
        return actionValues;
    }

    /**
     * 
     * @param actionValues
     *     The actionValues
     */
    @JsonProperty("actionValues")
    public void setActionValues(ActionValues_ actionValues) {
        this.actionValues = actionValues;
    }

    public Policy withActionValues(ActionValues_ actionValues) {
        this.actionValues = actionValues;
        return this;
    }

    /**
     * 
     * @return
     *     The resources
     */
    @JsonProperty("resources")
    public List<String> getResources() {
        return resources;
    }

    /**
     * 
     * @param resources
     *     The resources
     */
    @JsonProperty("resources")
    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public Policy withResources(List<String> resources) {
        this.resources = resources;
        return this;
    }

    /**
     * 
     * @return
     *     The subject
     */
    @JsonProperty("subject")
    public Subject_ getSubject() {
        return subject;
    }

    /**
     * 
     * @param subject
     *     The subject
     */
    @JsonProperty("subject")
    public void setSubject(Subject_ subject) {
        this.subject = subject;
    }

    public Policy withSubject(Subject_ subject) {
        this.subject = subject;
        return this;
    }

    /**
     * 
     * @return
     *     The lastModifiedBy
     */
    @JsonProperty("lastModifiedBy")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * 
     * @param lastModifiedBy
     *     The lastModifiedBy
     */
    @JsonProperty("lastModifiedBy")
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Policy withLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    /**
     * 
     * @return
     *     The lastModifiedDate
     */
    @JsonProperty("lastModifiedDate")
    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * 
     * @param lastModifiedDate
     *     The lastModifiedDate
     */
    @JsonProperty("lastModifiedDate")
    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Policy withLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    /**
     * 
     * @return
     *     The createdBy
     */
    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 
     * @param createdBy
     *     The createdBy
     */
    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Policy withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * 
     * @return
     *     The creationDate
     */
    @JsonProperty("creationDate")
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * 
     * @param creationDate
     *     The creationDate
     */
    @JsonProperty("creationDate")
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Policy withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Policy withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(active).append(description).append(applicationName).append(actionValues).append(resources).append(subject).append(lastModifiedBy).append(lastModifiedDate).append(createdBy).append(creationDate).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Policy) == false) {
            return false;
        }
        Policy rhs = ((Policy) other);
        return new EqualsBuilder().append(name, rhs.name).append(active, rhs.active).append(description, rhs.description).append(applicationName, rhs.applicationName).append(actionValues, rhs.actionValues).append(resources, rhs.resources).append(subject, rhs.subject).append(lastModifiedBy, rhs.lastModifiedBy).append(lastModifiedDate, rhs.lastModifiedDate).append(createdBy, rhs.createdBy).append(creationDate, rhs.creationDate).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
