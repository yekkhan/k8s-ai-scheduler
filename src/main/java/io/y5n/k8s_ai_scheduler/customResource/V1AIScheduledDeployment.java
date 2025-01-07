package io.y5n.k8s_ai_scheduler.customResource;

import com.google.gson.annotations.SerializedName;
import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.openapi.models.V1DeploymentSpec;
import io.kubernetes.client.openapi.models.V1DeploymentStatus;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.swagger.annotations.ApiModelProperty;

import javax.annotation.Nullable;
import java.util.Objects;

public class V1AIScheduledDeployment implements KubernetesObject {
    public static final String SERIALIZED_NAME_API_VERSION = "apiVersion";
    @SerializedName("apiVersion")
    private String apiVersion;
    public static final String SERIALIZED_NAME_KIND = "kind";
    @SerializedName("kind")
    private String kind;
    public static final String SERIALIZED_NAME_METADATA = "metadata";
    @SerializedName("metadata")
    private V1ObjectMeta metadata;
    public static final String SERIALIZED_NAME_SPEC = "spec";
    @SerializedName("spec")
    private V1AIScheduledDeploymentSpec spec;
    public static final String SERIALIZED_NAME_STATUS = "status";
    @SerializedName("status")
    private V1DeploymentStatus status;

    public V1AIScheduledDeployment() {
    }

    public V1AIScheduledDeployment apiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    @Nullable
    @ApiModelProperty("APIVersion defines the versioned schema of this representation of an object. " +
            "Servers should convert recognized schemas to the latest internal value, and may reject unrecognized values. " +
            "More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#resources")
    public String getApiVersion() {
        return this.apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public V1AIScheduledDeployment kind(String kind) {
        this.kind = kind;
        return this;
    }

    @Nullable
    @ApiModelProperty("Kind is a string value representing the REST resource this object represents. " +
            "Servers may infer this from the endpoint the client submits requests to. Cannot be updated. In CamelCase. " +
            "More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#types-kinds")
    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public V1AIScheduledDeployment metadata(V1ObjectMeta metadata) {
        this.metadata = metadata;
        return this;
    }

    @Nullable
    @ApiModelProperty("")
    public V1ObjectMeta getMetadata() {
        return this.metadata;
    }

    public void setMetadata(V1ObjectMeta metadata) {
        this.metadata = metadata;
    }

    public V1AIScheduledDeployment spec(V1AIScheduledDeploymentSpec spec) {
        this.spec = spec;
        return this;
    }

    @Nullable
    @ApiModelProperty("")
    public V1AIScheduledDeploymentSpec getSpec() {
        return this.spec;
    }

    public void setSpec(V1AIScheduledDeploymentSpec spec) {
        this.spec = spec;
    }

    public V1AIScheduledDeployment status(V1DeploymentStatus status) {
        this.status = status;
        return this;
    }

    @Nullable
    @ApiModelProperty("")
    public V1DeploymentStatus getStatus() {
        return this.status;
    }

    public void setStatus(V1DeploymentStatus status) {
        this.status = status;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            V1AIScheduledDeployment v1AIScheduledDeployment = (V1AIScheduledDeployment)o;
            return Objects.equals(this.apiVersion, v1AIScheduledDeployment.apiVersion) &&
                    Objects.equals(this.kind, v1AIScheduledDeployment.kind) &&
                    Objects.equals(this.metadata, v1AIScheduledDeployment.metadata) &&
                    Objects.equals(this.spec, v1AIScheduledDeployment.spec) &&
                    Objects.equals(this.status, v1AIScheduledDeployment.status);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.apiVersion, this.kind, this.metadata, this.spec, this.status});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class V1AIScheduledDeployment {\n");
        sb.append("    apiVersion: ").append(this.toIndentedString(this.apiVersion)).append("\n");
        sb.append("    kind: ").append(this.toIndentedString(this.kind)).append("\n");
        sb.append("    metadata: ").append(this.toIndentedString(this.metadata)).append("\n");
        sb.append("    spec: ").append(this.toIndentedString(this.spec)).append("\n");
        sb.append("    status: ").append(this.toIndentedString(this.status)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
