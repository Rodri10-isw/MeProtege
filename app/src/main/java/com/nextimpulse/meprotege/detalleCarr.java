package com.nextimpulse.meprotege;

public class detalleCarr {
    private String producto;
    private String piezas;
    private String cantidad;
    private String total;
    private String img;

    public detalleCarr(String producto, String piezas, String cantidad, String total,String img) {
        this.producto = producto;
        this.piezas = piezas;
        this.cantidad = cantidad;
        this.total = total;
        this.img = img;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPiezas() {
        return piezas;
    }

    public void setPiezas(String piezas) {
        this.piezas = piezas;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    public detalleCarr(){

    }
}
