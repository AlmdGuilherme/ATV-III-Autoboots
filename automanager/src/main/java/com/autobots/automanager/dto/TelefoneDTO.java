package com.autobots.automanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelefoneDTO {
    private Long id;
    private String ddd;
    private String numero;
}
