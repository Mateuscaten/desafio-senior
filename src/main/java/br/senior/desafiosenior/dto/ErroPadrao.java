package br.senior.desafiosenior.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ErroPadrao {

    private final LocalDateTime dataHora = LocalDateTime.now();

    private String caminho;
    private String status;
    private List<String> mensagens;

}
