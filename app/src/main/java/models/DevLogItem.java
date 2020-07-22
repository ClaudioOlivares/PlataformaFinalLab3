package models;

public class DevLogItem
{
    private int idDevLogItem;

    private int idDevLog;

    private DevLog devLog;

    private String titulo;

    private String texto;

    private String  multimedia;

    public DevLogItem(int idDevlogItem, int idDevlog, DevLog devLog, String multimedia, String texto, String titulo)
    {
        this.idDevLogItem = idDevlogItem;

        this.idDevLog = idDevlog;

        this.devLog = devLog;

        this.multimedia = multimedia;

        this.texto = texto;

        this.titulo = titulo;
    }

    public DevLogItem()
    {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getIdDevlogItem() {
        return idDevLogItem;
    }

    public void setIdDevlogItem(int idDevlogItem) {
        this.idDevLogItem = idDevlogItem;
    }

    public int getIdDevlog() {
        return idDevLog;
    }

    public void setIdDevlog(int idDevlog) {
        this.idDevLog = idDevlog;
    }

    public DevLog getDevLog() {
        return devLog;
    }

    public void setDevLog(DevLog devLog) {
        this.devLog = devLog;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }
}
