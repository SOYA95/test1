package winterschoolone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import winterschoolone.config.kafka.KafkaProcessor;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    
    @Autowired
    DeliveryRepository deliverRepository;
    
    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRefunded_(@Payload Refunded refunded){

    	if(refunded.isMe()){
    		
    		Delivery delivery = new Delivery();
    	
    		delivery.setId(refunded.getId());
    		deliverRepository.delete(delivery);

            
        }
    }

}
