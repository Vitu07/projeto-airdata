package br.com.cruzeirodosul.airquality.backend.job;

import br.com.cruzeirodosul.airquality.backend.model.Localizacao;
import br.com.cruzeirodosul.airquality.backend.repository.LocalizacaoRepository;
import br.com.cruzeirodosul.airquality.backend.service.DataCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataCollectionJob {

    @Autowired
    private DataCollectionService dataCollectionService;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Scheduled(cron = " 0 30 *  * * ?")
    public void coletarEgravarDados() {
        System.out.println("--- INICIANDO JOB DE COLETA DE DADOS HORÁRIA ---");

        List<Localizacao> cidadesMonitoradas = localizacaoRepository.findAll();

        if (cidadesMonitoradas.isEmpty()) {
            System.out.println("Nenhuma cidade encontrada no banco de dados para monitorar.");
            return;
        }

        System.out.println("Monitorando " + cidadesMonitoradas.size() + " cidades.");

        cidadesMonitoradas.forEach(localizacao -> {
            try {
                dataCollectionService.coletarESalvarDadosAtuais(localizacao);
            } catch (Exception e) {
                System.err.println("Falha ao coletar dados para a cidade: " + localizacao.getNomeCidade() + ". Erro: " + e.getMessage());
            }
        });
        System.out.println("--- JOB DE COLETA DE DADOS HORÁRIA FINALIZADO ---");
    }
}
