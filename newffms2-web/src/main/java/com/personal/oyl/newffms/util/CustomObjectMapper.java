package com.personal.oyl.newffms.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

import com.personal.oyl.newffms.account.domain.AccountAuditType;
import com.personal.oyl.newffms.account.domain.AccountType;
import com.personal.oyl.newffms.consumption.domain.ConsumptionType;
import com.personal.oyl.newffms.incoming.domain.IncomingType;


public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {  
        CustomSerializerFactory factory = new CustomSerializerFactory();
        
        factory.addGenericMapping(Date.class, new JsonSerializer<Date>() {  
            @Override
            public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException,
                    JsonProcessingException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                jsonGenerator.writeString(sdf.format(value));
            }  
        });  
        
        factory.addGenericMapping(ConsumptionType.class, new JsonSerializer<ConsumptionType>() {

            @Override
            public void serialize(ConsumptionType value, JsonGenerator jsonGenerator, SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                jsonGenerator.writeString(value.getDesc());
            }
            
        });
        
        factory.addGenericMapping(IncomingType.class, new JsonSerializer<IncomingType>() {

            @Override
            public void serialize(IncomingType value, JsonGenerator jsonGenerator, SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                jsonGenerator.writeString(value.getDesc());
            }
            
        });
        
        factory.addGenericMapping(AccountType.class, new JsonSerializer<AccountType>() {

            @Override
            public void serialize(AccountType value, JsonGenerator jsonGenerator, SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                jsonGenerator.writeString(value.getDesc());
            }
            
        });
        
        factory.addGenericMapping(AccountAuditType.class, new JsonSerializer<AccountAuditType>() {

            @Override
            public void serialize(AccountAuditType value, JsonGenerator jsonGenerator, SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                jsonGenerator.writeString(value.getDesc());
            }
            
        });
        
        this.setSerializerFactory(factory);  
    }  
}
