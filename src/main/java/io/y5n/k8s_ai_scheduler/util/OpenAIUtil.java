package io.y5n.k8s_ai_scheduler.util;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpenAIUtil {
    private OpenAIClient client;

    public OpenAIUtil() {
        this.client = OpenAIOkHttpClient.builder()
                .fromEnv()
                .build();
    }

    public String chat(String prompt, String aiModel) {
        // todo use parameter aiModel
//        ResponseFormatJsonSchema responseFormatJsonSchema = ResponseFormatJsonSchema.builder()
//                .jsonSchema(ResponseFormatJsonSchema.JsonSchema.builder().schema().build())
//                .build();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder().
                addMessage(ChatCompletionMessageParam.ofChatCompletionUserMessageParam(ChatCompletionUserMessageParam.builder()
                        .role(ChatCompletionUserMessageParam.Role.USER)
                        .content(ChatCompletionUserMessageParam.Content.ofTextContent(prompt))
                        .build()))
//                .responseFormat(responseFormatJsonSchema)
                .model(ChatModel.GPT_4O_MINI)
                .build();

        ChatCompletion chatCompletion = client.chat().completions().create(params);

        List<ChatCompletion.Choice> choices = chatCompletion.choices();
        for (ChatCompletion.Choice choice : choices) {
            return choice.message().content().get();
//            System.out.println("Choice content: " + choice.message().content().get());
        }

        return null;
    }
}
