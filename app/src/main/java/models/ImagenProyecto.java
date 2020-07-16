package models;

public class ImagenProyecto
{
    private int idImagenProyecto;
    private int idProyecto;
    private Proyecto proyecto;
    private String url;

    public ImagenProyecto(int idImagenProyecto, int idProyecto, Proyecto proyecto, String url)
    {
        this.idImagenProyecto = idImagenProyecto;
        this.idProyecto = idProyecto;
        this.proyecto = proyecto;
        this.url = url;
    }

    public ImagenProyecto() {
    }

    public int getIdImagenProyecto() {
        return idImagenProyecto;
    }

    public void setIdImagenProyecto(int idImagenProyecto) {
        this.idImagenProyecto = idImagenProyecto;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
