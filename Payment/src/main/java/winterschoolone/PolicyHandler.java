package winterschoolone;

import winterschoolone.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    
    @Autowired
    PaymentRepository paymentRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_(@Payload Ordered ordered){

    	if(ordered.isMe()){
            System.out.println("##### listener  : " + ordered.toJson());
            
            Payment payment = new Payment();
            payment.setMenuId(ordered.getMenuId());
            payment.setOrderId(ordered.getId());
            payment.setQty(ordered.getQty());
            payment.setUserId(ordered.getUserId());
            
            paymentRepository.save(payment);
            
        }
    }
    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelled_(@Payload OrderCancelled orderCancelled){

    	if(orderCancelled.isMe()){
    		
    		Payment payment = new Payment();
    		System.out.println("##### id : " + orderCancelled.getId());
    		payment.setId(orderCancelled.getId());
    		paymentRepository.delete(payment);
//            System.out.println("##### listener  : " + orderCancelled.toJson());
//            
//            List<Payment> list = paymentRepository.findByOrderId(orderCancelled.getId());
//            
//            for(Payment payment : list){
//            	payment.setCancelYn("Y");
//                // view 객체에 이벤트의 eventDirectValue 를 set 함
//                // view 레파지 토리에 save
//            	
//            }
            
        }
    }

}
