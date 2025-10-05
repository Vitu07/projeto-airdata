package br.com.cruzeirodosul.airquality.backend.job;

import br.com.cruzeirodosul.airquality.backend.service.DataCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataCollectionJob {

    @Autowired
    private DataCollectionService dataCollectionService;

    private final List<String> CIDADES_MONITORADAS = Arrays.asList("sao-paulo", "osasco", "campinas",
            "santos", "guarulhos", "sao-bernardo-do-campo", "sao-jose-dos-campos", "ribeirao-preto",
            "sorocaba", "maua", "jundiai", "carapicuiba", "piracicaba", "bauru", "cubatao", "taubate",
            "tatui", "americana", "limeira", "sao-sebastiao", "presidente-prudente", "sao-jose-do-rio-preto");

    @Scheduled(cron = "0 0 2 * * ?")
    public void coletarEgravarDados() {
        System.out.println("--- INICIANDO JOB DE COLETA DE DADOS ---");
        CIDADES_MONITORADAS.forEach(cidade -> {
            try {
                dataCollectionService.buscarESalvarDadosDaApi(cidade);
            } catch (Exception e) {
                System.err.println("Falha ao coletar dados para a cidade: " + cidade + ". Erro: " + e.getMessage());
            }
        });
        System.out.println("--- JOB DE COLETA DE DADOS FINALIZADO ---");
    }
}
