package models;

public class DevLog
{
    private int idDevLog;

    private int idProyecto;

    private Proyecto proyecto;

    private String titulo;

    private String resumen;

    private String fechaCreacion;

    public DevLog(int idDevlog, int idProyecto, Proyecto proyecto,String titulo, String resumen, String fechaCreacion)
    {
        this.idDevLog = idDevlog;
        this.idProyecto = idProyecto;
        this.proyecto = proyecto;
        this.titulo = titulo;
        this.resumen = resumen;
        this.fechaCreacion = fechaCreacion;
    }

    public DevLog() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdDevlog() {
        return idDevLog;
    }

    public void setIdDevlog(int idDevlog) {
        this.idDevLog = idDevlog;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
