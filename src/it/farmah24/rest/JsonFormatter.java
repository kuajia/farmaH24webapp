package it.farmah24.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.core.MediaType;

@Provider
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class JsonFormatter implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public JsonFormatter() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

}
