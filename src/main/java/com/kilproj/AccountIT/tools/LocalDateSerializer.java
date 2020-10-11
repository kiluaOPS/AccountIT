package com.kilproj.AccountIT.tools;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    public LocalDateSerializer() {
    }

    @Override
    public void serialize(LocalDate localDate, org.codehaus.jackson.JsonGenerator jsonGenerator, org.codehaus.jackson.map.SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(localDate.format(DateTimeFormatter.ofPattern("dd-mm-yyyy")));
    }

}