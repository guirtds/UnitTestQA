package org.estudos.br;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ConsultaIBGETest {
    private static final String ESTADOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";
    private static final String DISTRITOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/distritos/";

    // Teste 1
    @Test
    @DisplayName("Teste para consulta única de um estado")
    public void testConsultarEstado() throws IOException {
        // Arrange
        String uf = "RJ"; // Define o estado a ser consultado

        // Act
        String resposta = ConsultaIBGE.consultarEstado(uf); // Chama o método a ser testado

        // Assert
        // Verifica se a resposta não está vazia
        assert !resposta.isEmpty();

        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(ESTADOS_API_URL + uf).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    // Teste 2
    @Test
    @DisplayName("Teste para consultar um distrito específico pelo seu ID")
    public void ConsultaUmDistritoPorID() throws IOException {

        int id = 310018; // Consulta o distrito de Pedra Azul

        String resposta = ConsultaIBGE.consultarEstado(String.valueOf(id));

        assert !resposta.isEmpty();

        HttpURLConnection connection = (HttpURLConnection) new URL(DISTRITOS_API_URL + id).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    // Teste 3
    @Test
    @DisplayName("Adquirindo o status do código")
    public void ConsultaDoStatusDoCodigoDoEstado() throws IOException {

        String estadoUf = "MG";

        HttpURLConnection connection = (HttpURLConnection) new URL("https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + estadoUf).openConnection();

        connection.setRequestMethod("GET");

        int statusCode = connection.getResponseCode();

        assertEquals(200, statusCode, "O status code da resposta não é 200.");
    }

    // Teste 4
    @Test
    public void ObterMunicipiosDeUmEstado() {
        try {

            String stateCode = "PE";

            URL url = new URL("https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + stateCode + "/municipios");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            connection.disconnect();
        } catch (IOException e) {
            fail("Erro ao obter informações sobre os municípios do estado de Pernambuco: " + e.getMessage());
        }
    }

}