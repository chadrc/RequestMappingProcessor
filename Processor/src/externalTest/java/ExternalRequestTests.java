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
        Response response = request.Get();
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("OK", response.getData(ResponseData.class).getStatus());
    }

    @Test
    public void testPostRequest() throws IOException {
        ExternalRequest request = new ExternalRequest(TestApiUrl);
        Response response = request.Post(new RequestData("My Message"));
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("My Message", response.getData(ResponseData.class).getMessage());
    }
}
