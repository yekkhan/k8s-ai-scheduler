package io.y5n.k8s_ai_scheduler.config;

import io.kubernetes.client.Metrics;
import io.kubernetes.client.extended.controller.Controller;
import io.kubernetes.client.extended.controller.builder.ControllerBuilder;
import io.kubernetes.client.extended.controller.builder.DefaultControllerBuilder;
import io.kubernetes.client.informer.SharedIndexInformer;
import io.kubernetes.client.informer.SharedInformerFactory;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.generic.GenericKubernetesApi;
import io.y5n.k8s_ai_scheduler.customResource.V1AIScheduledDeployment;
import io.y5n.k8s_ai_scheduler.customResource.V1AIScheduledDeploymentList;
import io.y5n.k8s_ai_scheduler.reconciler.AIScheduledDeploymentReconciler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AppConfig {
    @Bean
    public ApiClient apiClient() throws IOException {
        return Config.defaultClient();
    }

    @Bean
    public SharedInformerFactory sharedInformerFactory(ApiClient apiClient) {
        return new SharedInformerFactory(apiClient);
    }

//    @Bean
//    public SharedIndexInformer<V1Node> nodeSharedIndexInformer(SharedInformerFactory sharedInformerFactory,
//                                                               ApiClient apiClient) {
//        GenericKubernetesApi<V1Node, V1NodeList> genericApi =
//                new GenericKubernetesApi<>(V1Node.class, V1NodeList.class, "", "v1",
//                        "nodes", apiClient);
//        return sharedInformerFactory.sharedIndexInformerFor(genericApi, V1Node.class, 60 * 1000L);
//    }
//
//    @Bean
//    public SharedIndexInformer<V1Pod> podSharedIndexInformer(SharedInformerFactory sharedInformerFactory,
//                                                             ApiClient apiClient) {
//        GenericKubernetesApi<V1Pod, V1PodList> genericApi =
//                new GenericKubernetesApi<>(V1Pod.class, V1PodList.class, "", "v1",
//                        "pods", apiClient);
//        return sharedInformerFactory.sharedIndexInformerFor(genericApi, V1Pod.class, 60 * 1000L);
//    }

    @Bean
    public SharedIndexInformer<V1AIScheduledDeployment> aiScheduledDeploymentSharedIndexInformer(SharedInformerFactory sharedInformerFactory,
                                                             ApiClient apiClient) {
        GenericKubernetesApi<V1AIScheduledDeployment, V1AIScheduledDeploymentList> genericApi =
                new GenericKubernetesApi<>(V1AIScheduledDeployment.class, V1AIScheduledDeploymentList.class, "", "v1",
                        "aiScheduledDeployments", apiClient);

        return sharedInformerFactory.sharedIndexInformerFor(genericApi, V1AIScheduledDeployment.class, 60 * 1000L);
    }

    @Bean
    public Controller aiScheduledDeploymentController(
            SharedInformerFactory sharedInformerFactory, AIScheduledDeploymentReconciler reconciler) {
        DefaultControllerBuilder builder = ControllerBuilder.defaultBuilder(sharedInformerFactory);

        builder.withReconciler(reconciler);
        return builder.build();
    }

    @Bean
    public Metrics metrics(ApiClient apiClient) {
        return new Metrics(apiClient);
    }

    @Bean
    public AppsV1Api appsV1Api(ApiClient apiClient) {
        return new AppsV1Api(apiClient);
    }
}
