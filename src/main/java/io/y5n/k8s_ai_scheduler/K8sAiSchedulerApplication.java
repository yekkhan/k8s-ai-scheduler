package io.y5n.k8s_ai_scheduler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class K8sAiSchedulerApplication implements CommandLineRunner {

    private final K8sAiSchedulerController k8sAiSchedulerController;

    public K8sAiSchedulerApplication(K8sAiSchedulerController k8sAiSchedulerController) {
        this.k8sAiSchedulerController = k8sAiSchedulerController;
    }

    public static void main(String[] args) {
        SpringApplication.run(K8sAiSchedulerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        k8sAiSchedulerController.watch();
    }
}
