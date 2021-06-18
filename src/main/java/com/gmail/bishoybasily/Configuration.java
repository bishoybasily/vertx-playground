package com.gmail.bishoybasily;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.regex.Pattern;

/**
 * @author bishoybasily
 * @since 2021-05-14
 */
@Data
@Accessors(chain = true)
public class Configuration {

    private Service http;
    private Service stomp;

    @Data
    @Accessors(chain = true)
    public static class Service {

        private Integer port;

        @JsonCreator
        public Service(@JsonProperty("port") Object port) {
            this.port = value(port).toInteger();
        }

    }

    private static Value value(Object raw) {
        var matcher = Pattern.compile("\\{([^}]*)\\}").matcher(raw.toString());
        if (matcher.find()) {
            var tokens = matcher.group(1).split(":");
            var var = tokens[0];
            var def = tokens[1];
            var val = System.getenv(var);
            if ("".equals(val) || val == null)
                return new Value(def);
            return new Value(val);
        }
        return new Value(raw);
    }

}

@Data
@RequiredArgsConstructor
class Value {

    private final Object obj;

    public Integer toInteger() {
        return Integer.parseInt(toString());
    }

    @Override
    public String toString() {
        return obj.toString();
    }

}