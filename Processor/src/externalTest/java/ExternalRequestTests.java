import com.chadrc.utils.http.ExternalRequest;
import com.chadrc.utils.http.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by chad on 2/4/17.
 */
public class ExternalRequestTests {
    private static final String TestApiUrl = "http://localhost:8000/";

    @Test
    public void testGetRequest() throws IOException {
        ExternalRequest request = new ExternalRequest(TestApiUrl);
        Response response = null;
        try {
            response = request.Get();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("OK", response.getData(ResponseData.class).getStatus());
    }
}
