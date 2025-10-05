package br.com.cruzeirodosul.airquality.backend.service;

import br.com.cruzeirodosul.airquality.backend.dto.MedicaoDiariaDTO;
import br.com.cruzeirodosul.airquality.backend.model.Localizacao;
import br.com.cruzeirodosul.airquality.backend.model.Medicao;
import br.com.cruzeirodosul.airquality.backend.model.Poluente;
import br.com.cruzeirodosul.airquality.backend.repository.LocalizacaoRepository;
import br.com.cruzeirodosul.airquality.backend.repository.MedicaoRepository;
import br.com.cruzeirodosul.airquality.backend.repository.PoluenteRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DataCollectionService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Autowired
    private PoluenteRepository poluenteRepository;

    @Autowired
    private MedicaoRepository medicaoRepository;


    @Value("${API_URL}")
    private String apiUrl;

    @Value("${EXTERNAL_API_KEY}")
    private String apiKey;

    @Transactional
    public void buscarESalvarDadosDaApi(String cidade) throws Exception {
        System.out.println("Coletando dados de previsão para: " + cidade);
        String fullUrl = String.format("%s/feed/%s/?token=%s", apiUrl, cidade, apiKey);
        String jsonResponse = restTemplate.getForObject(fullUrl, String.class);
        JsonNode root = objectMapper.readTree(jsonResponse);

        if (!"ok".equals(root.path("status").asText())) {
            throw new RuntimeException("API externa retornou erro para a cidade: " + cidade);
        }

        JsonNode data = root.path("data");
        JsonNode dailyForecasts = data.path("forecast").path("daily");

        String nomeCompletoDaEstacao = data.path("city").path("name").asText();
        Localizacao localizacao = localizacaoRepository.findByNomeCidade(nomeCompletoDaEstacao)
                .orElseGet(() -> {
                    Localizacao novaLocalizacao = new Localizacao();
                    novaLocalizacao.setNomeCidade(nomeCompletoDaEstacao);
                    String[] partes = nomeCompletoDaEstacao.split(", ");
                    if (partes.length >= 3) {
                        novaLocalizacao.setEstado(partes[partes.length - 2].trim());
                        novaLocalizacao.setPais(partes[partes.length - 1].trim());
                    } else if (partes.length == 2) {
                        novaLocalizacao.setEstado("N/A");
                        novaLocalizacao.setPais(partes[1].trim());
                    } else {
                        novaLocalizacao.setEstado("N/A");
                        novaLocalizacao.setPais("N/A");
                    }
                    JsonNode geo = data.path("city").path("geo");
                    if (geo.isArray() && geo.size() >= 2) {
                        novaLocalizacao.setLatitude(geo.get(0).decimalValue());
                        novaLocalizacao.setLongitude(geo.get(1).decimalValue());
                    }
                    return localizacaoRepository.save(novaLocalizacao);
                });


        Map<LocalDate, MedicaoDiariaDTO> pioresMedicoesPorDia = new HashMap<>();

        dailyForecasts.fieldNames().forEachRemaining(nomePoluente -> {
            JsonNode forecastArray = dailyForecasts.path(nomePoluente);

            for (JsonNode dailyForecast : forecastArray) {
                LocalDate dia = LocalDate.parse(dailyForecast.path("day").asText());
                int avgAqi = dailyForecast.path("avg").asInt();

                MedicaoDiariaDTO medicaoExistente = pioresMedicoesPorDia.get(dia);
                if (medicaoExistente == null || avgAqi > medicaoExistente.getValorAqi()) {

                    MedicaoDiariaDTO novaPiorMedicao = new MedicaoDiariaDTO();
                    novaPiorMedicao.setData(dia);
                    novaPiorMedicao.setValorAqi(avgAqi);
                    novaPiorMedicao.setNomePoluente(nomePoluente);
                    pioresMedicoesPorDia.put(dia, novaPiorMedicao);
                }
            }
        });

        for (MedicaoDiariaDTO medicaoDoDia : pioresMedicoesPorDia.values()) {
            LocalDate dia = medicaoDoDia.getData();
            OffsetDateTime inicioDoDia = dia.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();
            OffsetDateTime fimDoDia = dia.atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC);

            Optional<Medicao> medicaoExistenteOpt = medicaoRepository.findByLocalizacaoAndDia(localizacao, inicioDoDia, fimDoDia);

            Poluente poluente = poluenteRepository.findByNome(medicaoDoDia.getNomePoluente())
                    .orElseGet(() -> {
                        Poluente novoPoluente = new Poluente();
                        novoPoluente.setNome(medicaoDoDia.getNomePoluente());
                        return poluenteRepository.save(novoPoluente);
                    });

            if (medicaoExistenteOpt.isPresent()) {
                Medicao medicaoExistente = medicaoExistenteOpt.get();
                System.out.println("Atualizando medição para " + cidade + " no dia " + dia);
                medicaoExistente.setValorAqi(medicaoDoDia.getValorAqi());
                medicaoExistente.setPoluenteDominante(poluente);
                medicaoRepository.save(medicaoExistente);
            } else {
                System.out.println("Criando nova medição para " + cidade + " no dia " + dia);
                Medicao novaMedicao = new Medicao();
                novaMedicao.setValorAqi(medicaoDoDia.getValorAqi());
                novaMedicao.setDataHora(dia.atTime(12, 0).atOffset(ZoneOffset.UTC));
                novaMedicao.setLocalizacao(localizacao);
                novaMedicao.setPoluenteDominante(poluente);
                medicaoRepository.save(novaMedicao);
            }
        }
    }
}
