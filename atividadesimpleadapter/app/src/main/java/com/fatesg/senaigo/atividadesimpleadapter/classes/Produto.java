package com.fatesg.senaigo.atividadesimpleadapter.classes;

import java.math.BigDecimal;

public class Produto {

    private Long id;
    private String nomeProduto;
    private BigDecimal valorProduto;
    private Integer quantidadeProduto;

    public Produto() {}

    public Produto(Long id, String nomeProduto, BigDecimal valorProduto, Integer quantidade) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.quantidadeProduto = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public BigDecimal getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(BigDecimal valorProduto) {
        this.valorProduto = valorProduto;
    }

    public Integer getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(Integer quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    @Override
    public String toString() {
        StringBuilder sProduto = new StringBuilder();
        sProduto.append(this.getNomeProduto()).append(" - R$");
        sProduto.append(String.valueOf(this.getValorProduto()).replace('.', ','));
        return sProduto.toString();
    }
}
