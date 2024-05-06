package unclassified;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Json1 {

    @AllArgsConstructor
    class Event {
        String a;
        String b;
    }

    public void writeListToJsonArray() throws IOException {
        final List<Event> list = new ArrayList<>(2);
        list.add(new Event("a1","a2"));
        list.add(new Event("b1","b2"));

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(out, list);

        final byte[] data = out.toByteArray();
        System.out.println(new String(data));
    }
}
