package hr.card.management.api.domain.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.card.management.api.domain.annotations.KafkaSender;
import hr.card.management.api.domain.exceptions.KafkaSenderIllegalArgumentException;
import hr.card.management.api.infrastructure.model.CardRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaSenderAspect {

    public static final String PENDING = "PENDING";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Pointcut("@annotation(hr.card.management.api.domain.annotations.KafkaSender)")
    public void kafkaSenderPointcut() {
    }

    @AfterReturning(value = "kafkaSenderPointcut()", returning = "result")
    public void sendToKafka(JoinPoint joinPoint, Object result) {
        Method method = getMethod(joinPoint);
        KafkaSender kafkaSenderAnnotation = method.getAnnotation(KafkaSender.class);
        if (kafkaSenderAnnotation.topic().isEmpty()) {
            throw new KafkaSenderIllegalArgumentException("KafkaSender annotation must have a topic");
        }
        String topic = kafkaSenderAnnotation.topic();
        if (result instanceof CardRequest cardRequest && isPending((cardRequest))) {
            try {
                String jsonResult = objectMapper.writeValueAsString(result);
                log.info("Sending to Kafka topic '{}': {}", topic, jsonResult);
                kafkaTemplate.send(topic, jsonResult);
            } catch (JsonProcessingException e) {
                log.error("Error serializing object to JSON: {}", e.getMessage());
            }
        }
    }

    private Method getMethod(JoinPoint joinPoint) {
        return ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod();
    }

    private boolean isPending(CardRequest cardRequest) {
        return PENDING.equals(cardRequest.getStatus());
    }
}
