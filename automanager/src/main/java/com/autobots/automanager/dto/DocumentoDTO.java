package com.autobots.automanager.dto;

import com.autobots.automanager.enumeracoes.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoDTO {
    private Long id;
    private TipoDocumento tipoDocumento;
    private Date dataEmissao;
    private String numero;
}
