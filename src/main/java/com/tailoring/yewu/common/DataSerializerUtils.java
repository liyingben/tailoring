package com.tailoring.yewu.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DataSerializerUtils extends JsonSerializer<Double> {

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        if(value!=null && !"".equals(value)) {
            gen.writeString(String.format("%.2f",value));
        }else {//这个分支不要忘记了，否则将不输出这个属性的值
            gen.writeString(value+"");
        }
    }
}
