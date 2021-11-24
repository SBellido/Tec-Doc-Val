package calendarTask;
import java.sql.Time;
import java.util.Date;


public class Programacion {

	private Tarea task;
	private Date inicio;
	private Date fin;
	
	public Programacion(Date d1, Date d2, Tarea t) {
		this.setInicio(d1);
		this.setFin(d2);
		this.task = t;
	}
	
	public Tarea getTask() {
		return task;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date d1) {
		this.inicio = d1;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date d2) {
		this.fin = d2;
	}
	
	
}
