package org.estudos.br;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MockTest {

    @Mock
    private HttpURLConnection connectionMock;

    private static final String JSON_RESPONSE = "{\"id\":35,\"sigla\":\"SP\",\"nome\":\"São Paulo\"," +
            "\"regiao\":{\"id\":3,\"sigla\":\"SE\",\"nome\":\"Sudeste\"}}";
    @BeforeEach
    public void setup() throws IOException {

        MockitoAnnotations.openMocks(this);

        InputStream inputStream = new ByteArrayInputStream(JSON_RESPONSE.getBytes());
        when(connectionMock.getInputStream()).thenReturn(inputStream);
    }

    @Test
    @DisplayName("Utilizando o Mock para consulta")
    public void testConsultarEstadoComMock() throws IOException {

        String estadoUf = "SP";

        String response = ConsultaIBGE.consultarEstado(estadoUf);

        assertEquals(JSON_RESPONSE, response, "Não corresponde ao JSON esperado.");
    }

}
