package io.y5n.k8s_ai_scheduler.reconciler;

import com.alibaba.fastjson2.JSONObject;
import io.kubernetes.client.Metrics;
import io.kubernetes.client.custom.NodeMetricsList;
import io.kubernetes.client.extended.controller.reconciler.Reconciler;
import io.kubernetes.client.extended.controller.reconciler.Request;
import io.kubernetes.client.extended.controller.reconciler.Result;
import io.kubernetes.client.informer.SharedIndexInformer;
import io.kubernetes.client.informer.cache.Lister;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.*;
import io.y5n.k8s_ai_scheduler.customResource.V1AIScheduledDeployment;
import io.y5n.k8s_ai_scheduler.customResource.V1AIScheduledDeploymentSpec;
import io.y5n.k8s_ai_scheduler.util.OpenAIUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;

@Component
public class AIScheduledDeploymentReconciler implements Reconciler {
    private Metrics metricsApi;
    private AppsV1Api appsV1Api;
    private Lister<V1AIScheduledDeployment> aiScheduledDeploymentLister;
    private OpenAIUtil openAIUtil;

    private String prompt = "You are a Kubernetes scheduling expert. " +
            "Based on the following inputs, determine which nodes can host each pod and provide the results in JSON format " +
            "without markdown: " +
            "{'key': <node-label-key>, 'values': ['<node-label-value>']}" +
            "Inputs: " +
            "- Node resource levels: ${nodes_metrics} " +
            "- Pod resource requests: ${requested_pod_resources} " +
            "- Number of replicas needed: ${num_of_replicas}. " +
            "Make sure to return the 'key' and 'values' that match nodes capable of hosting the requested pods.";




    public AIScheduledDeploymentReconciler(Metrics metricsApi,
                                           AppsV1Api appsV1Api,
                                           SharedIndexInformer<V1AIScheduledDeployment> aiScheduledDeploymentSharedIndexInformer,
                                           OpenAIUtil openAIUtil) {
        this.metricsApi = metricsApi;
        this.appsV1Api = appsV1Api;
        this.aiScheduledDeploymentLister = new Lister<>(aiScheduledDeploymentSharedIndexInformer.getIndexer());
        this.openAIUtil = openAIUtil;
    }


    @Override
    public Result reconcile(Request request) {
        // get node metrics
        String nodesMetricsString = null;
        try {
            NodeMetricsList nodeMetricsList = metricsApi.getNodeMetrics();
            nodesMetricsString = nodeMetricsList.toString();

            V1AIScheduledDeployment v1AIScheduledDeployment = aiScheduledDeploymentLister.get(request.getName());
            V1AIScheduledDeploymentSpec v1AIScheduledDeploymentSpec = v1AIScheduledDeployment.getSpec();
            String aiModel = v1AIScheduledDeploymentSpec.getNodeSelectorAIModel();
            if (!StringUtils.hasLength(aiModel)) {
                aiModel = "gpt-4o-mini";
            }

            //todo get result from openai and create node affinity
            String resultString = openAIUtil.chat(prompt, aiModel);
            JSONObject result = JSONObject.parseObject(resultString);

            V1NodeSelectorRequirement v1NodeSelectorRequirement = new V1NodeSelectorRequirement();
            v1NodeSelectorRequirement.setKey((result.getString("key")));
            v1NodeSelectorRequirement.setOperator("In");
            // set result nodes here
            v1NodeSelectorRequirement.setValues(result.getJSONArray("values").toJavaList(String.class));

            V1NodeSelectorTerm v1NodeSelectorTerm = new V1NodeSelectorTerm();
            v1NodeSelectorTerm.setMatchExpressions(Collections.singletonList(v1NodeSelectorRequirement));

            V1PreferredSchedulingTerm v1PreferredSchedulingTerm = new V1PreferredSchedulingTerm();
            v1PreferredSchedulingTerm.setPreference(v1NodeSelectorTerm);

            V1NodeAffinity v1NodeAffinity = new V1NodeAffinityBuilder()
                    .addToPreferredDuringSchedulingIgnoredDuringExecution(v1PreferredSchedulingTerm)
                    .build();

            V1Affinity v1Affinity = new V1Affinity();
            v1Affinity.setNodeAffinity(v1NodeAffinity);

            v1AIScheduledDeploymentSpec.getTemplate().getSpec().setAffinity(v1Affinity);

            //create deployment
            V1DeploymentBuilder builder = new V1DeploymentBuilder();
            V1Deployment v1Deployment = builder
                    .withMetadata(v1AIScheduledDeployment.getMetadata())
                    .withSpec(v1AIScheduledDeploymentSpec)
                    .build();

            appsV1Api.createNamespacedDeployment(request.getNamespace(), v1Deployment, null, null,
                    null, null);
        } catch (ApiException apiException) {
            System.out.println(apiException.getMessage());
        }

        return null;
    }
}
