package main;

import java.util.List;

public class UbicacionesDTO {

	private List<List<Ubicacion>> serpientes;
	private List<Ubicacion> comestibles;
	
	public UbicacionesDTO(List<List<Ubicacion>> serpientes, List<Ubicacion> comestibles) {
		this.serpientes = serpientes;
		this.comestibles = comestibles;
	}

	public List<List<Ubicacion>> getSerpientes() {
		return serpientes;
	}
	
	public List<Ubicacion> getComestibles() {
		return comestibles;
	}

}
