package br.com.cruzeirodosul.airquality.backend.service;

import br.com.cruzeirodosul.airquality.backend.model.Localizacao;
import br.com.cruzeirodosul.airquality.backend.model.Medicao;
import br.com.cruzeirodosul.airquality.backend.model.Poluente;
import br.com.cruzeirodosul.airquality.backend.repository.MedicaoRepository;
import br.com.cruzeirodosul.airquality.backend.repository.PoluenteRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.time.OffsetDateTime;
import java.util.regex.Pattern;

@Service
public class DataCollectionService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PoluenteRepository poluenteRepository;

    @Autowired
    private MedicaoRepository medicaoRepository;

    @Value("${API_URL}")
    private String apiUrl;

    @Value("${EXTERNAL_API_KEY}")
    private String apiKey;

    @Transactional
    public void coletarESalvarDadosAtuais(Localizacao localizacao) throws Exception {
        String nomeCidadeFormatado = formatarNomeCidadeParaApi(localizacao.getNomeCidade());
        System.out.println("Coletando dados atuais para: " + nomeCidadeFormatado);

        String fullUrl = String.format("%s/feed/%s/?token=%s", apiUrl, nomeCidadeFormatado, apiKey);
        String jsonResponse = restTemplate.getForObject(fullUrl, String.class);
        JsonNode root = objectMapper.readTree(jsonResponse);

        if (!"ok".equals(root.path("status").asText())) {
            throw new RuntimeException("API externa retornou erro para a cidade: " + localizacao.getNomeCidade());
        }

        JsonNode data = root.path("data");
        int aqiAtual = data.path("aqi").asInt(-1);

        if (aqiAtual == -1) {
            System.err.println("AQI não encontrado para a cidade: " + localizacao.getNomeCidade());
            return;
        }

        String poluenteDominanteNome = data.path("dominentpol").asText(null);

        if (poluenteDominanteNome == null || poluenteDominanteNome.isBlank()) {
            System.err.println("Poluente dominante não encontrado na resposta da API para: " + localizacao.getNomeCidade());
            return;
        }

        Poluente poluenteDominante = poluenteRepository.findByNome(poluenteDominanteNome)
                .orElseGet(() -> {

                    Poluente novoPoluente = new Poluente();
                    novoPoluente.setNome(poluenteDominanteNome);
                    novoPoluente.setDescricao("Poluente monitorado pela API.");
                    return poluenteRepository.save(novoPoluente);
                });

        Medicao novaMedicao = new Medicao();
        novaMedicao.setLocalizacao(localizacao);
        novaMedicao.setDataHora(OffsetDateTime.now());
        novaMedicao.setValorAqi(aqiAtual);
        novaMedicao.setPoluenteDominante(poluenteDominante);

        medicaoRepository.save(novaMedicao);
        System.out.println("Nova medição salva para " + localizacao.getNomeCidade() + " com AQI " + aqiAtual);
    }

    private String formatarNomeCidadeParaApi(String nomeCidade) {
        String nomeNormalizado = Normalizer.normalize(nomeCidade, Normalizer.Form.NFD);


        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String nomeSemAcentos = pattern.matcher(nomeNormalizado).replaceAll("");

        return nomeSemAcentos
                .toLowerCase()
                .trim()
                .replace(" ", "-")
                .replaceAll("[^a-z0-9-]", "");
    }
}
