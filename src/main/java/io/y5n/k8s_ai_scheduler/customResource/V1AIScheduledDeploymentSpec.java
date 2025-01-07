package io.y5n.k8s_ai_scheduler.customResource;

import com.google.gson.annotations.SerializedName;
import io.kubernetes.client.openapi.models.V1DeploymentSpec;
import io.swagger.annotations.ApiModelProperty;

import javax.annotation.Nullable;

public class V1AIScheduledDeploymentSpec extends V1DeploymentSpec {
    public static final String SERIALIZED_NAME_SELECTOR = "selector";
    @SerializedName("nodeSelectorAIModel")
    private String nodeSelectorAIModel;

    @Nullable
    @ApiModelProperty("LLM model to use to determine which nodes will be selected for creating pods")
    public String getNodeSelectorAIModel() {
        return this.nodeSelectorAIModel;
    }

    public void setNodeSelectorAIModel(String nodeSelectorAIModel) {
        this.nodeSelectorAIModel = nodeSelectorAIModel;
    }
}
