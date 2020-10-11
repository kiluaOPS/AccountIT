package com.kilproj.AccountIT.tools;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    protected LocalDateDeserializer() {
        super();
    }

    @Override
    public LocalDate deserialize(org.codehaus.jackson.JsonParser jsonParser, org.codehaus.jackson.map.DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        System.out.println("THIS IS MY DESERIALIZER PERSONALLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        return LocalDate.parse(jsonParser.readValueAs(String.class));
    }
}