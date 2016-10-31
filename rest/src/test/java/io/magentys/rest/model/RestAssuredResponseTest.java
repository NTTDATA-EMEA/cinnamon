package io.magentys.rest.model;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static io.magentys.rest.model.RestAssuredResponse.constructFrom;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class RestAssuredResponseTest {

    @Mock
    private Response mockResponse;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnStatusOK() {
        when(mockResponse.statusCode()).thenReturn(200);
        RestResponse<String> response = new RestAssuredResponse<>(mockResponse, "test");
        assertThat(response.ok(), is(true));
    }

    @Test
    public void shouldReturnStatusFailed() {
        when(mockResponse.statusCode()).thenReturn(404);
        RestResponse<String> response = new RestAssuredResponse<>(mockResponse, "test");
        assertThat(response.failed(), is(true));
    }

    @Test
    public void shouldConstructResponseAndReturnData() {
        Dummy data = new Dummy();
        data.setId(12345);
        data.setName("John Doe");
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.as(Dummy.class)).thenReturn(data);
        RestResponse<Dummy> response = constructFrom(mockResponse, () -> mockResponse.as(Dummy.class));
        assertThat(response.data(), equalTo(data));
    }

    @Test
    public void shouldConstructResponseAndReturnNullIfHttpErrorCode() {
        when(mockResponse.statusCode()).thenReturn(404);
        RestResponse<Dummy> response = constructFrom(mockResponse, () -> mockResponse.as(Dummy.class));
        assertThat(response.data(), equalTo(null));
    }

    @Test
    public void shouldConstructResponseAndReturnNullIfNullContent() {
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.as(Dummy.class)).thenReturn(null);
        RestResponse<Dummy> response = constructFrom(mockResponse, () -> mockResponse.as(Dummy.class));
        assertThat(response.data(), equalTo(null));
    }

    static class Dummy {

        private Integer id;
        private String name;

        void setId(Integer id) {
            this.id = id;
        }

        void setName(String name) {
            this.name = name;
        }
    }
}