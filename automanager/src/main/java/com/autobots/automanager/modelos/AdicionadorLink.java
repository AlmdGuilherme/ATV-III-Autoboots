package com.autobots.automanager.modelos;

import java.util.List;

public interface AdicionadorLink<T> {
    public void adicionadorLinkGeral(List<T> lista);
    public void adicionadorLink(T objeto);
}
