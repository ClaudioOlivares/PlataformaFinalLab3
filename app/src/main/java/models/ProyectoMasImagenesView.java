package models;

import java.util.ArrayList;
import java.util.List;

public class ProyectoMasImagenesView
{
    private int idProyecto;
    private String titulo,genero,status,plataforma,fechaCreacion,portada,video,textoResumen,textoCompleto,videoTrailer;
    private int idUser;
    private Usuario user;
    private List<ImagenProyecto> imagenes = new ArrayList<>();

    public ProyectoMasImagenesView(int idProyecto, String titulo, String genero, String status, String plataforma, String fechaCreacion, String portada, String video, String textoResumen, String textoCompleto, String videoTrailer, int idUser, Usuario user, List<ImagenProyecto> imagenes) {
        this.idProyecto = idProyecto;
        this.titulo = titulo;
        this.genero = genero;
        this.status = status;
        this.plataforma = plataforma;
        this.fechaCreacion = fechaCreacion;
        this.portada = portada;
        this.video = video;
        this.textoResumen = textoResumen;
        this.textoCompleto = textoCompleto;
        this.videoTrailer = videoTrailer;
        this.idUser = idUser;
        this.user = user;
        this.imagenes = imagenes;
    }

    public ProyectoMasImagenesView()
    {

    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTextoResumen() {
        return textoResumen;
    }

    public void setTextoResumen(String textoResumen) {
        this.textoResumen = textoResumen;
    }

    public String getTextoCompleto() {
        return textoCompleto;
    }

    public void setTextoCompleto(String textoCompleto) {
        this.textoCompleto = textoCompleto;
    }

    public String getVideoTrailer() {
        return videoTrailer;
    }

    public void setVideoTrailer(String videoTrailer) {
        this.videoTrailer = videoTrailer;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public List<ImagenProyecto> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenProyecto> imagenes) {
        this.imagenes = imagenes;
    }
}
