package io.github.deergate;

import java.io.IOException;
import java.io.Reader;

public interface JsonDeserialize<T> {

    T deserialize(Reader reader,Class<T> cls) throws IOException;

}
