package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Proyecto {
    private int idProyecto;
    private String titulo, genero, status, plataforma;
    private String fechaCreacion;
    private int idUser;
    private Usuario user;
    private String portada, video, textoResumen, textoCompleto, videoTrailer;

    public Proyecto(int idProyecto, String titulo, String genero, String status, String plataforma, String fechaCreacion, int idUser, String portada, String video, String videoTrailer, String textoResumen, String textoCompleto, Usuario user) {
        this.idProyecto = idProyecto;
        this.titulo = titulo;
        this.genero = genero;
        this.status = status;
        this.plataforma = plataforma;
        this.fechaCreacion = fechaCreacion;
        this.idUser = idUser;
        this.portada = portada;
        this.video = video;
        this.textoResumen = textoResumen;
        this.textoCompleto = textoCompleto;
        this.videoTrailer = videoTrailer;
        this.user = user;
    }

    public Proyecto() {
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public void setVideo(String video) {
        this.video = video;
    }
}
