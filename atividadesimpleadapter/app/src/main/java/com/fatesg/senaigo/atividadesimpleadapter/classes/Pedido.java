package com.fatesg.senaigo.atividadesimpleadapter.classes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class Pedido {

    private Long id;
    private String nomeCliente;
    private String dataDoPedido;
    private List<HashMap<String, String>> produtos;
    private BigDecimal valorTotal;

    public Pedido(){}

    public Pedido(Long id, String nomeCliente, String dataDoPedido,
                  List<HashMap<String, String>> produtos, BigDecimal valorTotal) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.dataDoPedido = dataDoPedido;
        this.produtos = produtos;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getDataDoPedido() {
        return dataDoPedido;
    }

    public void setDataDoPedido(String dataDoPedido) {
        this.dataDoPedido = dataDoPedido;
    }

    public List<HashMap<String, String>> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<HashMap<String, String>> produtos) {
        this.produtos = produtos;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
