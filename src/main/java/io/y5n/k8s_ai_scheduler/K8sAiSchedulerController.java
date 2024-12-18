package io.y5n.k8s_ai_scheduler;

import io.kubernetes.client.informer.cache.Controller;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class K8sAiSchedulerController {
    private final ApiClient client;
    private final CoreV1Api coreV1Api;

    public K8sAiSchedulerController() throws IOException {
        super();
        this.client = Config.defaultClient();
        this.coreV1Api = new CoreV1Api(client);
    }

    public void watch() {
        // the Controller uses the Informer to observe events and then calls the Reconciler to take corrective actions
        //todo
//        0. init reconciler, informer and controller
//            a. write manifest, generate object class using generator
//            b. create CR reconciler that implements reconciler interface
        //    c. create controller by using controller builder
        //    d. instantiate informer bean
//        1.watches the creation of Custom Resource object
//        2.get node's resource info (something like top node)
//        3.get the request and limit which are defined in the Custom Resource object
//        4.pass the prompt and the data from 2 and 3 to gpt
//        5.schedule pod creation on recommended node(result from gpt)
           // a. add nodeSelector label to pod object


    }
}
