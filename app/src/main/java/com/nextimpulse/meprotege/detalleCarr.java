package com.nextimpulse.meprotege;

public class detalleCarr {
    private String num;
    private String producto;
    private String piezas;
    private String cantidad;
    private String total;
    private String img;
    private String color;
    private String codigoP;

    public detalleCarr(String producto, String piezas, String cantidad, String total,String img,String color,String num,String codigoP) {
        this.producto = producto;
        this.piezas = piezas;
        this.cantidad = cantidad;
        this.total = total;
        this.img = img;
        this.color=color;
        this.num=num;
        this.codigoP=codigoP;
    }

    public String getCodigoP() {
        return codigoP;
    }

    public void setCodigoP(String codigoP) {
        this.codigoP = codigoP;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    public String getImg() {
        return img;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
