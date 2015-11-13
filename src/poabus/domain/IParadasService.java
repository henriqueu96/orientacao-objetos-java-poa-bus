package poabus.domain;

import java.util.List;

public interface IParadasService {
    Parada getParadaNearLocation(double x, double y);
    public List<Parada> getAllParadas();    
}