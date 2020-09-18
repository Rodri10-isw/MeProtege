package com.nextimpulse.meprotege;

public class Productos {
    private String pUid;
    private String cantidad;
    private String producto;
    private String codigoPro;
    private String img;
    private String piesas;
    private String color;
    private String total;

    public Productos() {
    }

    public Productos(String producto) {
        this.producto = producto;
    }

    public Productos(String pUid, String cantidad, String codigoPro, String img, String piesas, String total,String color) {
        this.pUid = pUid;
        this.cantidad = cantidad;
        this.codigoPro = codigoPro;
        this.img = img;
        this.piesas = piesas;
        this.color=color;
        this.total = total;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getpUid() {
        return pUid;
    }

    public void setpUid(String pUid) {
        this.pUid = pUid;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoPro() {
        return codigoPro;
    }

    public void setCodigoPro(String codigoPro) {
        this.codigoPro = codigoPro;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPiesas() {
        return piesas;
    }

    public void setPiesas(String piesas) {
        this.piesas = piesas;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
