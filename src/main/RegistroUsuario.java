package main;

public class RegistroUsuario {
		private String mensaje;
		private boolean registroEfectivo;
		
		public RegistroUsuario(String mensaje, boolean registroEfectivo) {
			this.mensaje = mensaje;
			this.registroEfectivo = registroEfectivo;
		}

		public String getMensaje() {
			return mensaje;
		}

		public boolean esRegistroEfectivo() {
			return registroEfectivo;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}

		public void setRegistroEfectivo(boolean registroEfectivo) {
			this.registroEfectivo = registroEfectivo;
		}		
}
